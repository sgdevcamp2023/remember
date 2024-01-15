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

@WebSocketGateway({
  cors: { origin: '*', credentials: true },
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
  ) {}

  afterInit(server: Server) {
    console.log(`Server initialized`);
  }

  handleConnection(client: Socket) {
    console.log(`Client connected: ${client.id}`);
  }

  async handleDisconnect(client: Socket) {
    // clientId를 기반으로 이 사람의 길드, 채널, userId를 찾는다.
    const { channelId, guildId, userId } =
      this.socketToChannelGuildMap.get(client.id) || {};

    // voiceChannelStatusMap에서 client를 제거한다.
    const deleteUserInMap = this.voiceChannelStatusMap
      .get(guildId)
      ?.get(channelId)
      ?.delete(userId);

    // 만약 해당 길드의 음성채널의 유저가 비어있다면 제거한다.
    if (
      deleteUserInMap &&
      this.voiceChannelStatusMap.get(guildId)?.get(channelId).size === 0
    )
      this.voiceChannelStatusMap.delete(guildId);

    // 만약 해당 길드의 음성채널이 비어있다면 제거한다.
    if (this.voiceChannelStatusMap.get(guildId)?.size === 0)
      this.voiceChannelStatusMap.delete(guildId);

    // socketToChannelGuildMap에서 제거한다.
    this.socketToChannelGuildMap.delete(client.id);

    const data = {
      guildId,
      channelId,
      userId,
    };

    ////////////////////////////// 상태 관리 서버 전달 작업
    try {
      const response = await this.httpService
        .post('http://localhost:6001/guild/delete', {
          guildId,
          channelId,
          userId,
        })
        .toPromise();
    } catch (error) {
      console.error(error);
    }
  }

  @SubscribeMessage('validateUserInChannel')
  validateUserInChannel(
    @MessageBody() data: JoinChannelDto,
    @ConnectedSocket() client: Socket,
  ) {
    const { guildId, channelId } = data;
    const validateSocketStatus = this.socketToChannelGuildMap.get(client.id);
    if (validateSocketStatus) {
      client.emit('userInChannel', {
        guildId,
        channelId,
      });
      return;
    }

    client.emit('newUserValidate', {
      guildId,
      channelId,
    });
  }

  @SubscribeMessage('validateUserInGuild')
  validateUserInGuild(
    @MessageBody() data: JoinChannelDto,
    @ConnectedSocket() client: Socket,
  ) {
    const { guildId, channelId, userId } = data;
    const guildMap = this.voiceChannelStatusMap.get(guildId);
    if (guildMap) {
      for (let channelMap of guildMap.values()) {
        if (channelMap.has(userId)) {
          this.disconnectUserSocket(channelMap.get(userId));
        }
      }
    }

    client.emit('userInGuildChannelResponse', {
      guildId,
      channelId,
    });
  }

  @SubscribeMessage('joinChannel')
  async handleJoinChannel(
    @MessageBody() data: JoinChannelDto,
    @ConnectedSocket() client: Socket,
  ) {
    const { guildId, channelId, userId } = data;
    const parsedRoomId = guildId + '-' + channelId;

    // 길드 참가, 없으면 생성 작업
    const guildMap = this.voiceChannelStatusMap.get(guildId) || new Map();
    this.voiceChannelStatusMap.set(guildId, guildMap);

    // 채널 참가, 없으면 생성 작업
    const channelMap = guildMap.get(channelId) || new Map();
    guildMap.set(channelId, channelMap);

    // 유저 참가 작업
    channelMap.set(userId, client.id);

    this.socketToChannelGuildMap.set(
      client.id,
      new ChannelGuildEntity(channelId, guildId, userId),
    );

    // 소켓 연결 작업
    client.join(parsedRoomId);
    this.server.to(parsedRoomId).emit('message', `${userId}님이 입장했습니다.`);

    ////////////////////////////////////////// 상태관리 서버 전달 작업
    const response = await this.httpService
      .post('http://localhost:6001/guild/update', data)
      .toPromise();
  }

  @SubscribeMessage('leaveChannel')
  handleLeaveChannel(
    @MessageBody() data: LeaveChannelDto,
    @ConnectedSocket() client: Socket,
  ) {
    const { channelId, guildId, userId } =
      this.socketToChannelGuildMap.get(data.socketId) || {};

    const parsedRoomId = guildId + '-' + channelId;

    this.server.to(parsedRoomId).emit('message', `${userId}님이 퇴장했습니다.`);
    this.disconnectUserSocket(data.socketId);
  }

  disconnectUserSocket(socketId: string) {
    const socketToDisconnect = this.server.sockets.sockets.get(socketId);
    if (socketToDisconnect) {
      socketToDisconnect.disconnect();
    }
  }
}
