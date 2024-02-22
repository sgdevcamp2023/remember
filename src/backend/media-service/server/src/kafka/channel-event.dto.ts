import { IsNotEmpty, IsNumber, IsString } from 'class-validator';

export class ChannelEventDto {
  @IsNotEmpty()
  @IsNumber()
  guildId: number;

  @IsNotEmpty()
  @IsNumber()
  channelId: number;

  @IsNotEmpty()
  @IsNumber()
  userId: number;

  @IsNotEmpty()
  @IsString()
  type: string;
}
