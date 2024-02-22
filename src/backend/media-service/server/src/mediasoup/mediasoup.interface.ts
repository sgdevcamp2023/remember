import * as mediasoup from 'mediasoup';

export interface ITransportWrapper {
  producer: mediasoup.types.WebRtcTransport;
  consumer: mediasoup.types.WebRtcTransport;
}
