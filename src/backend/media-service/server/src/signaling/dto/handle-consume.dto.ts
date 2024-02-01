import { IsNotEmpty, IsString } from 'class-validator';
import * as mediasoup from 'mediasoup';

export class HandleConsumeDTO {
  @IsNotEmpty()
  rtpCapabilities: mediasoup.types.RtpCapabilities;

  @IsNotEmpty()
  @IsString()
  roomId: string;

  @IsNotEmpty()
  remoteProducerId: mediasoup.types.Producer['id'];
}
