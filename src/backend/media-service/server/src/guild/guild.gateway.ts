import {
  OnGatewayConnection,
  OnGatewayDisconnect,
  OnGatewayInit,
  SubscribeMessage,
  WebSocketGateway,
  WebSocketServer,
} from '@nestjs/websockets';
import { Server, Socket } from 'socket.io';
import { GuildService } from './guild.service';

@WebSocketGateway({
  cors: { origin: '*', credentials: true },
  namespace: 'rendering',
})
export class GuildGateway
  implements OnGatewayInit, OnGatewayConnection, OnGatewayDisconnect
{
  constructor(private guildService: GuildService) {
    this.guildService.eventEmitter.on('memberUpdate', (newMockdata) => {
      this.server.emit('pageRender', newMockdata);
    });
  }

  @WebSocketServer()
  server: Server;

  afterInit(server: Server) {
    console.log(`Server initialized`);
  }

  handleConnection(client: Socket) {
    console.log(`Client connected: ${client.id}`);
  }
  handleDisconnect(client: Socket) {
    console.log(`Client disconnected: ${client.id}`);
  }

  @SubscribeMessage('message')
  handleMessage(client: any, payload: any): string {
    return 'Hello world!';
  }
}
