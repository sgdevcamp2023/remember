import { IsBoolean, IsNotEmpty, IsString } from 'class-validator';

export class CreateWebRtcTransportDTO {
  @IsNotEmpty()
  @IsBoolean()
  consumer: boolean;

  @IsNotEmpty()
  @IsString()
  roomId: string;

  @IsString()
  socketId: string;
}
