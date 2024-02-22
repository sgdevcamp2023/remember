import { Controller, Get, Body, Patch, Delete } from '@nestjs/common';
import { GuildService } from './guild.service';
import { UpdateGuildDto } from './dto/update-guild.dto';

@Controller('guild')
export class GuildController {
  constructor(private readonly guildService: GuildService) {}

  @Get()
  findOne() {
    return this.guildService.find();
  }

  @Patch()
  update(@Body() updateGuildDto: UpdateGuildDto) {
    return this.guildService.update(updateGuildDto);
  }

  @Delete()
  remove(@Body() updateGuildDto: UpdateGuildDto) {
    return this.guildService.remove(updateGuildDto);
  }
}
