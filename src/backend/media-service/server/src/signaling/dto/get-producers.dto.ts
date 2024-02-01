import { IsNotEmpty, IsString } from 'class-validator';
import * as mediasoup from 'mediasoup';

export class GetProducersDTO {
  @IsNotEmpty()
  @IsString()
  roomId: string;

  @IsNotEmpty()
  kind: mediasoup.types.MediaKind;

  @IsNotEmpty()
  producerId: mediasoup.types.Producer['id'];
}
