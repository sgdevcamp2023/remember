import { Module } from '@nestjs/common';
import { GuildService } from './guild.service';
import { GuildController } from './guild.controller';
import { GuildGateway } from './guild.gateway';

@Module({
  controllers: [GuildController],
  providers: [GuildService, GuildGateway],
  exports: [GuildService],
})
export class GuildModule {}
