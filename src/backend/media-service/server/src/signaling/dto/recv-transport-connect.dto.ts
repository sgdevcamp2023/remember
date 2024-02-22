import { IsBoolean, IsNotEmpty } from 'class-validator';
import * as mediasoup from 'mediasoup';

export class RecvTransportConnectDTO {
  @IsNotEmpty()
  dtlsParameters: mediasoup.types.DtlsParameters;

  @IsNotEmpty()
  @IsBoolean()
  consumer: boolean;
}
