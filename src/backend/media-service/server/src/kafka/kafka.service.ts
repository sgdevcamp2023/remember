// kafka.service.ts
import { Inject, Injectable, OnModuleInit } from '@nestjs/common';
import { ClientKafka } from '@nestjs/microservices';
import { ChannelEventDto } from './channel-event.dto';

@Injectable()
export class KafkaService implements OnModuleInit {
  constructor(
    @Inject('MEDIA_SERVICE') private readonly clientKafka: ClientKafka,
  ) {}

  async onModuleInit() {
    await this.clientKafka.connect();
  }

  async send(event: ChannelEventDto) {
    console.log('send event', event, typeof event);
    return this.clientKafka.emit('channelEvent', JSON.stringify(event));
  }
}
