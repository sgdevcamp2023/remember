import { IsNotEmpty, IsString } from 'class-validator';

export class JoinChannelDto {
  @IsNotEmpty()
  @IsString()
  guildId: string;

  @IsNotEmpty()
  @IsString()
  channelId: string;

  @IsNotEmpty()
  @IsString()
  userId: string;
}
