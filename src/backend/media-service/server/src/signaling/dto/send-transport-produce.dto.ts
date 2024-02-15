import { IsBoolean, IsNotEmpty, IsString } from 'class-validator';
import * as mediasoup from 'mediasoup';

export class SendTransportProduceDTO {
  @IsNotEmpty()
  kind: mediasoup.types.MediaKind;

  @IsNotEmpty()
  rtpParameters: mediasoup.types.RtpParameters;

  @IsNotEmpty()
  @IsString()
  roomId: string;

  @IsNotEmpty()
  @IsBoolean()
  consumer: boolean;

  @IsString()
  mediaTag: string;
}
