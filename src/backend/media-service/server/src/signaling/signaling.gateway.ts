import {
  ConnectedSocket,
  MessageBody,
  OnGatewayConnection,
  OnGatewayDisconnect,
  OnGatewayInit,
  SubscribeMessage,
  WebSocketGateway,
  WebSocketServer,
} from '@nestjs/websockets';
import { Server, Socket } from 'socket.io';
import { JoinChannelDto } from './dto/join-channel.dto';
import { LeaveChannelDto } from './dto/leave-channel.dts';
import { ChannelGuildEntity } from './entity/channel.guild.entity';
import { MediasoupService } from '../mediasoup/mediasoup.service';
import * as mediasoup from 'mediasoup';
import { CreateWebRtcTransportDTO } from './dto/create-webRtcTransport.dto';
import { SendTransportConnectDTO } from './dto/send-transport-connect.dto';
import { SendTransportProduceDTO } from './dto/send-transport-produce.dto';
import { GetProducersDTO } from './dto/get-producers.dto';
import { HandleConsumeDTO } from './dto/handle-consume.dto';
import { RecvTransportConnectDTO } from './dto/recv-transport-connect.dto';
import { KafkaService } from 'src/kafka/kafka.service';

@WebSocketGateway({
  cors: {
    origin: [process.env.CORS_ORIGIN_LIST.split(',')],
    credentials: true,
  },
})
export class SignalingGateway
  implements OnGatewayInit, OnGatewayConnection, OnGatewayDisconnect
{
  private voiceChannelStatusMap: Map<string, Map<string, Map<string, string>>> =
    new Map();

  private socketToChannelGuildMap: Map<string, ChannelGuildEntity> = new Map();

  @WebSocketServer()
  server: Server;

  constructor(
    private mediasoupService: MediasoupService,
    private kafkaService: KafkaService,
  ) {}

  afterInit(server: Server) {
    console.log(`Server initialized`);
  }

  handleConnection(client: Socket) {
    console.log(`Client connected: ${client.id}`);
  }

  async handleDisconnect(client: Socket) {
    const { channelId, guildId, userId } =
      this.socketToChannelGuildMap.get(client.id) || {};

    const parsedRoomId = guildId + '-' + channelId;
    this.server.to(parsedRoomId).emit('message', `${userId}님이 퇴장했습니다.`);

    // voiceChannelStatusMap에서 client를 제거한다.
    const deleteUserInMap = this.voiceChannelStatusMap
      .get(guildId)
      ?.get(channelId)
      ?.delete(userId);

    // when you delete user in map, if channel is empty, delete channel in map
    if (this.voiceChannelStatusMap?.get(guildId)?.get(channelId)?.size === 0) {
      this.voiceChannelStatusMap.get(guildId).delete(channelId);
    }

    // 만약 해당 길드의 음성채널이 비어있다면 제거한다.
    if (this.voiceChannelStatusMap?.get(guildId)?.size === 0) {
      this.voiceChannelStatusMap.delete(guildId);
    }

    // socketToChannelGuildMap에서 제거한다.
    this.socketToChannelGuildMap.delete(client.id);

    ////////////////////////////// mediasoup 관련 작업
    const myProducer = this.mediasoupService.getSocketProducerList();
    if (myProducer.has(client.id)) {
      myProducer.get(client.id).forEach((producer, mediaTag) => {
        console.log('disconnect producer', producer, mediaTag);

        if (mediaTag === 'audio') {
          console.log('>> audio producer closed');
          this.mediasoupService.removeProducer('audio', parsedRoomId, producer);
          this.mediasoupService.removeProducerIdByMediaTag(client.id, mediaTag);
        }
        if (mediaTag === 'camera' || mediaTag === 'display') {
          console.log('>> video producer closed');
          this.mediasoupService.removeProducer('video', parsedRoomId, producer);
          this.mediasoupService.removeProducerIdByMediaTag(client.id, mediaTag);
        }
        // 해당유저가 나갔음을 알린다.
        client.broadcast.to(parsedRoomId).emit('producer-closed', {
          mediaTag,
          kind: mediaTag === 'audio' ? 'audio' : 'video',
          producerId: producer,
        });
      });
    }
    this.mediasoupService.removeTransport(client.id);

    ////////////////////////////// 상태 관리 서버 전달 작업
    try {
      // const response = await this.httpService
      //   .post('http://localhost:6001/guild/delete', data)
      //   .toPromise();

      const kafka_event = {
        guildId: Number(guildId),
        channelId: Number(channelId),
        userId: Number(userId),
        type: 'LEAVE',
      };

      await this.kafkaService.send(kafka_event);
    } catch (error) {
      console.error(error);
    }
  }

  @SubscribeMessage('validate-user-in-channel')
  validateUserInChannel(
    @MessageBody() data: JoinChannelDto,
    @ConnectedSocket() client: Socket,
  ) {
    const { guildId } = data;

    const validateSocketStatus = this.socketToChannelGuildMap.get(client.id);
    if (validateSocketStatus) {
      client.emit('user-in-channel');
      return;
    }

    client.emit('validate-new-user', {
      guildId,
    });
  }

  @SubscribeMessage('validate-user-in-guild')
  validateUserInGuild(
    @MessageBody() data: JoinChannelDto,
    @ConnectedSocket() client: Socket,
  ) {
    const { guildId, userId } = data;
    const guildMap = this.voiceChannelStatusMap.get(guildId);
    if (guildMap) {
      for (let channelMap of guildMap.values()) {
        if (channelMap.has(userId)) {
          this.disconnectUserSocket(channelMap.get(userId));
        }
      }
    }
    client.emit('join-channel');
  }

  @SubscribeMessage('start-voice-call')
  async handleJoinChannel(
    @MessageBody() data: JoinChannelDto,
    @ConnectedSocket() client: Socket,
  ) {
    const { guildId, channelId, userId } = data;
    const parsedRoomId = guildId + '-' + channelId;
    const guildMap = this.voiceChannelStatusMap.get(guildId) || new Map();
    this.voiceChannelStatusMap.set(guildId, guildMap);

    const channelMap = guildMap.get(channelId) || new Map();
    guildMap.set(channelId, channelMap);
    channelMap.set(userId, client.id);

    this.socketToChannelGuildMap.set(
      client.id,
      new ChannelGuildEntity(channelId, guildId, userId),
    );

    client.join(parsedRoomId);
    this.server.to(parsedRoomId).emit('message', `${userId}님이 입장했습니다.`);

    ////////////////////////////////////////// 상태관리 서버 전달 작업
    // const response = await this.httpService
    //   .post('http://localhost:6001/guild/update', data)
    //   .toPromise();

    const kafka_event = {
      guildId: Number(guildId),
      channelId: Number(channelId),
      userId: Number(userId),
      type: 'JOIN',
    };

    await this.kafkaService.send(kafka_event);
  }

  @SubscribeMessage('leave-channel')
  handleLeaveChannel(
    @MessageBody() data: LeaveChannelDto,
    @ConnectedSocket() client: Socket,
  ) {
    this.disconnectUserSocket(data.socketId);
  }

  disconnectUserSocket(socketId: string) {
    const socketToDisconnect = this.server.sockets.sockets.get(socketId);
    if (socketToDisconnect) {
      socketToDisconnect.disconnect();
    }
  }
  /**
   *##################################################
   *#########   mediasoup 관련 메소드  ################
   *##################################################
   */

  @SubscribeMessage('createRouter')
  async hanldeRTPcapabilities(
    @MessageBody() roomId: string,
    @ConnectedSocket() client: Socket,
  ): Promise<mediasoup.types.RtpCapabilities> {
    const router: mediasoup.types.Router =
      await this.mediasoupService.getRouter(roomId);

    return router.rtpCapabilities;
  }

  @SubscribeMessage('createWebRtcTransport')
  async createTransport(
    @MessageBody() data: CreateWebRtcTransportDTO,
    @ConnectedSocket() client: Socket,
  ) {
    const newData = {
      ...data,
      socketId: client.id,
    };

    const transport =
      await this.mediasoupService.createWebRtcTransport(newData);

    const transportParams = {
      id: transport.id,
      iceParameters: transport.iceParameters,
      iceCandidates: transport.iceCandidates,
      dtlsParameters: transport.dtlsParameters,
    };

    return transportParams;
  }

  @SubscribeMessage('transport-connect')
  async handleConnectTransport(
    @MessageBody() data: SendTransportConnectDTO,
    @ConnectedSocket() client: Socket,
  ) {
    const { dtlsParameters, consumer } = data;
    try {
      const transport = await this.mediasoupService.getTransport(
        client.id,
        consumer,
      );
      await transport.connect({ dtlsParameters });
    } catch (error) {
      console.error(error);
    }
  }

  @SubscribeMessage('transport-produce')
  async handleProduceTransport(
    @MessageBody() data: SendTransportProduceDTO,
    @ConnectedSocket() client: Socket,
  ) {
    const { kind, rtpParameters, roomId, mediaTag } = data;
    const transport = await this.mediasoupService.getTransport(
      client.id,
      data.consumer,
    );

    const producer = await transport.produce({
      kind,
      rtpParameters,
    });

    this.mediasoupService.setProducer(kind, roomId, producer);
    this.mediasoupService.setProdcuerIdByMediaTag(
      client.id,
      mediaTag,
      producer.id,
    );

    let producerExist = false;
    if (this.mediasoupService.getProducers('audio', roomId).length > 1) {
      producerExist = true;
      if (kind === 'audio')
        this.server.to(roomId).emit('new-producer-init', producer.id);
    }

    producer.on('transportclose', () => {
      console.log('producer transport close');
      producer.close();
      // this.mediasoupService.removeProducer(kind, roomId, producer.id);
      this.mediasoupService.removeTransport(client.id);
    });

    return { producerId: producer.id, producerExist };
  }

  @SubscribeMessage('transport-recv-connect')
  async handleRedvConnect(
    @MessageBody() data: RecvTransportConnectDTO,
    @ConnectedSocket() client: Socket,
  ) {
    const { dtlsParameters } = data;

    const transport = await this.mediasoupService.getTransport(
      client.id,
      data.consumer,
    );

    try {
      await transport.connect({ dtlsParameters });
    } catch (error) {
      console.error('Error connecting transport:', error);
    }
  }

  @SubscribeMessage('consume')
  async handleConsume(
    @MessageBody() data: HandleConsumeDTO,
    @ConnectedSocket() client: Socket,
  ) {
    try {
      const { rtpCapabilities, roomId, remoteProducerId } = data;
      const router = await this.mediasoupService.getRouter(roomId);
      const transport = await this.mediasoupService.getTransport(
        client.id,
        true,
      );

      if (
        router.canConsume({
          producerId: remoteProducerId,
          rtpCapabilities,
        })
      ) {
        const consumer = await transport.consume({
          producerId: remoteProducerId,
          rtpCapabilities,
          paused: true,
        });
        this.mediasoupService.setConsumer(consumer.kind, roomId, consumer);

        consumer.on('transportclose', () => {
          console.log('consumer transport close');
          consumer.close();
          this.mediasoupService.removeConsumer(
            consumer.kind,
            roomId,
            consumer.id,
          );
        });

        consumer.on('producerclose', () => {
          console.log('producer close');
          consumer.close();
          this.mediasoupService.removeConsumer(
            consumer.kind,
            roomId,
            consumer.id,
          );
        });
        const params = {
          id: consumer.id,
          producerId: remoteProducerId,
          kind: consumer.kind,
          rtpParameters: consumer.rtpParameters,
        };

        return { params };
      }
    } catch (error) {
      console.error(error);
    }
  }

  @SubscribeMessage('consumer-resume')
  async handleConsumerResume(
    @MessageBody() data: any,
    @ConnectedSocket() client: Socket,
  ) {
    const { kind, roomId, serverConsumerId } = data;

    const consumer = await this.mediasoupService.getConsumer(
      kind,
      roomId,
      serverConsumerId,
    );

    await consumer.resume();
  }

  @SubscribeMessage('new-video-producer')
  async handleNewVideoProducer(
    @MessageBody() data: any,
    @ConnectedSocket() client: Socket,
  ) {
    const { roomId, producerId } = data;

    client.broadcast.to(roomId).emit('new-video-producer-init', producerId);
  }

  @SubscribeMessage('consumer-toggle')
  async handleConsumerToggle(
    @MessageBody() data: any,
    @ConnectedSocket() client: Socket,
  ) {
    const { kind, roomId, serverConsumerId } = data;
    const consumer = await this.mediasoupService.getConsumer(
      kind,
      roomId,
      serverConsumerId,
    );

    let paused = false;
    if (consumer.paused) {
      await consumer.resume();
      paused = false;
    } else {
      await consumer.pause();
      paused = true;
    }
    return { paused };
  }

  @SubscribeMessage('getProducers')
  async handleGetProducers(
    @MessageBody() data: GetProducersDTO,
    @ConnectedSocket() client: Socket,
  ) {
    const { kind, roomId, producerId = null } = data;
    const producers = this.mediasoupService.getProducers(kind, roomId);

    let producerIds = producers?.map((producer) => producer.id);
    if (producerId) {
      producerIds = producerIds.filter((id) => id !== producerId);
    }

    return producerIds;
  }

  @SubscribeMessage('producer-close')
  async handleProducerClose(
    @MessageBody() data: any,
    @ConnectedSocket() client: Socket,
  ) {
    const { roomId, kind, mediaTag } = data;
    const myProducerId = this.mediasoupService.getProducerIdByMediaTag(
      client.id,
      mediaTag,
    );
    const producers = this.mediasoupService.getProducers(kind, roomId);
    const producer = producers.find((producer) => producer.id === myProducerId);
    producer.close();
    this.mediasoupService.removeProducer(kind, roomId, myProducerId);
    this.mediasoupService.removeProducerIdByMediaTag(client.id, mediaTag);

    client.broadcast
      .to(roomId)
      .emit('producer-closed', { mediaTag, kind, producerId: myProducerId });
  }
}
