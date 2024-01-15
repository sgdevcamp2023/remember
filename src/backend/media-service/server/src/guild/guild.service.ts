import { Injectable } from '@nestjs/common';
import { EventEmitter } from 'events';
import { UpdateGuildDto } from './dto/update-guild.dto';
import { mockData } from './entities/mock';

const newMockdata = { ...mockData };
@Injectable()
export class GuildService {
  eventEmitter = new EventEmitter();
  find() {
    // console.log(JSON.stringify(newMockdata, null, 2));
    return newMockdata;
  }

  update(updateGuildDto: UpdateGuildDto) {
    const { guildId, channelId, userId } = updateGuildDto;
    newMockdata[guildId].channels
      .find((channel) => channel.id === channelId)
      .members.push(userId);
    this.eventEmitter.emit('memberUpdate', newMockdata);
    return newMockdata;
  }

  remove(updateGuildDto: UpdateGuildDto) {
    const { guildId, channelId, userId } = updateGuildDto;
    if (newMockdata[guildId]?.channels) {
      newMockdata[guildId].channels.find(
        (channel) => channel.id === channelId,
      ).members = newMockdata[guildId]?.channels
        .find((channel) => channel.id === channelId)
        .members.filter((member) => member !== userId);
    }
    // console.log(JSON.stringify(newMockdata, null, 2));
    this.eventEmitter.emit('memberUpdate', newMockdata);
    return newMockdata;
  }
}
