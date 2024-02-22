import { IsNotEmpty, IsString } from 'class-validator';

export class UpdateGuildDto {
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
