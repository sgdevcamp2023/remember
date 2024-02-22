import { IsBoolean, IsNotEmpty } from 'class-validator';
import * as mediasoup from 'mediasoup';

export class SendTransportConnectDTO {
  @IsNotEmpty()
  dtlsParameters: mediasoup.types.DtlsParameters;

  @IsNotEmpty()
  @IsBoolean()
  consumer: boolean;
}
