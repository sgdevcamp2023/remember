import { Module } from '@nestjs/common';
import { HttpModule } from '@nestjs/axios';
import { ConfigModule } from '@nestjs/config';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { SignalingGateway } from './signaling/signaling.gateway';
import { GuildModule } from './guild/guild.module';
import { MediasoupService } from './mediasoup/mediasoup.service';

@Module({
  imports: [
    ConfigModule.forRoot({
      isGlobal: true,
    }),
    GuildModule,
    HttpModule,
  ],
  controllers: [AppController],
  providers: [AppService, SignalingGateway, MediasoupService],
})
export class AppModule {}