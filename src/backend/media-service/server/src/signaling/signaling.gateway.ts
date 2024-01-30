import { GuildService } from './../guild/guild.service';
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
import { HttpService } from '@nestjs/axios';
import { MediasoupService } from '../mediasoup/mediasoup.service';
import * as mediasoup from 'mediasoup';

@WebSocketGateway({
  cors: {
    origin: ['https://localhost:3000', 'https://192.168.0.26:3000'],
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
    private guildService: GuildService,
    private httpService: HttpService,
    private mediasoupService: MediasoupService,
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

    // 테스트 해보는거
    // 유저가 나가면 해당 유저의 transoprt를 제거하낟.
    this.mediasoupService.removeTransport(client.id);

    ////////////////////////////// 상태 관리 서버 전달 작업
    const data = { channelId, guildId, userId };
    try {
      const response = await this.httpService
        .post('http://localhost:6001/guild/delete', data)
        .toPromise();
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
    const response = await this.httpService
      .post('http://localhost:6001/guild/update', data)
      .toPromise();
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
    @MessageBody() data: any,
    @ConnectedSocket() client: Socket,
  ) {
    // data: consumer, roomId;
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
    @MessageBody() data: any,
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
    @MessageBody() data: any,
    @ConnectedSocket() client: Socket,
  ) {
    const { kind, rtpParameters, roomId } = data;
    const transport = await this.mediasoupService.getTransport(
      client.id,
      data.consumer,
    );

    const producer = await transport.produce({
      kind,
      rtpParameters,
    });

    this.mediasoupService.setProducer(kind, roomId, producer);

    let producerExist = false;
    if (this.mediasoupService.getProducers(kind, roomId).length > 1) {
      producerExist = true;
    }

    // 클라이언트의 sendTransport가 종료될때
    producer.on('transportclose', () => {
      console.log('producer transport close');
      producer.close();
      // this.mediasoupService.removeTransport(client.id);
      // this.mediasoupService.removeProducer(roomId, producer.id);
      // // 클라이언트에게 producer가 종료됨을 알린다.
      // this.server
      //   .to(roomId)
      //   .emit('producer-closed', { kind, remoteProducerId: producer.id });
    });

    console.log('>>>> producer id', producer.id);
    this.server.to(roomId).emit('new-producer-init', producer.id);

    return { id: producer.id, producerExist };
  }

  @SubscribeMessage('transport-recv-connect')
  async handleRedvConnect(
    @MessageBody() data: any,
    @ConnectedSocket() client: Socket,
  ) {
    const { dtlsParameters } = data;
    const transport = await this.mediasoupService.getTransport(
      client.id,
      data.consumer,
    );
    await transport.connect({ dtlsParameters });
  }

  @SubscribeMessage('consume')
  async handleConsume(
    @MessageBody() data: any,
    @ConnectedSocket() client: Socket,
  ) {
    try {
      const { rtpCapabilities, roomId, remoteProducerId } = data;
      // if (remoteProducerId === undefined) return;
      const router = await this.mediasoupService.getRouter(roomId);

      // 내 수신용 transport와 consume한다.
      const transport = await this.mediasoupService.getTransport(
        client.id,
        true,
      );
      console.log('>> producer ID check', remoteProducerId);
      if (
        router.canConsume({
          producerId: remoteProducerId,
          rtpCapabilities,
        })
      ) {
        console.log('>> canConsume성공');
        const consumer = await transport.consume({
          producerId: remoteProducerId,
          rtpCapabilities,
          paused: true,
        });
        console.log(`${consumer.kind}, >> consumer객체 생성`);

        this.mediasoupService.setConsumer(consumer.kind, roomId, consumer);

        // client의 recvTransport가 종료될 때
        consumer.on('transportclose', () => {
          console.log('consumer transport close');
          // consumer.close();
          // this.mediasoupService.removeConsumer(roomId, consumer.id);
        });

        // 연결된 producer가 종료될 때
        consumer.on('producerclose', () => {
          console.log('producer close');
          // consumer.close();
          // this.mediasoupService.removeConsumer(roomId, consumer.id);
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

    console.log('>> consumer resume');
    await consumer.resume();
  }

  @SubscribeMessage('getProducers')
  async handleGetProducers(
    @MessageBody() data: any,
    @ConnectedSocket() client: Socket,
  ) {
    const { kind, roomId, producerId } = data;
    const producers = this.mediasoupService.getProducers(kind, roomId);
    console.log('>>>>>>> producers', producers);
    console.log(producers[0].id);
    // 내 아이디를 제외한 다른 producer들의 id를 배열로 반환한다.
    const producerIds = producers
      .map((producer) => producer.id)
      .filter((id) => id !== producerId);

    return producerIds;
  }
}
