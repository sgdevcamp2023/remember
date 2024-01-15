import { IsNotEmpty, IsString } from 'class-validator';

export class LeaveChannelDto {
  @IsNotEmpty()
  @IsString()
  guildId: string;

  @IsNotEmpty()
  @IsString()
  channelId: string;

  @IsNotEmpty()
  @IsString()
  userId: string;

  @IsString()
  socketId: string;
}
