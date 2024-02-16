import { Module } from '@nestjs/common';
import { HttpModule } from '@nestjs/axios';
import { ConfigModule } from '@nestjs/config';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { SignalingGateway } from './signaling/signaling.gateway';
import { GuildModule } from './guild/guild.module';
import { MediasoupService } from './mediasoup/mediasoup.service';
import { ClientsModule, Transport } from '@nestjs/microservices';
import { KafkaService } from './kafka/kafka.service';

@Module({
  imports: [
    ConfigModule.forRoot({
      isGlobal: true,
    }),
    ClientsModule.register([
      {
        name: 'MEDIA_SERVICE',
        transport: Transport.KAFKA,
        options: {
          client: {
            brokers: ['34.22.109.45:9092'],
          },
        },
      },
    ]),

    GuildModule,
    HttpModule,
  ],
  controllers: [AppController],
  providers: [AppService, SignalingGateway, MediasoupService, KafkaService],
})
export class AppModule {}
