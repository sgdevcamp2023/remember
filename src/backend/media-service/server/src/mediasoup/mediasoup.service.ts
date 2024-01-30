import { Injectable, OnModuleInit } from '@nestjs/common';
import * as mediasoup from 'mediasoup';

@Injectable()
export class MediasoupService implements OnModuleInit {
  private worker: mediasoup.types.Worker;
  private mediaCodecs: mediasoup.types.RtpCodecCapability[];
  private routers = new Map<string, mediasoup.types.Router>();
  // socketId -> [producer transport / consumer transport]
  private producerTransports = new Map<
    string,
    mediasoup.types.WebRtcTransport
  >();
  private consumerTransports = new Map<
    string,
    mediasoup.types.WebRtcTransport
  >();
  // room Id(parsed_url) -> [producer / consumer, ...]
  private audioProducers = new Map<string, mediasoup.types.Producer[]>();
  private videoProducers = new Map<string, mediasoup.types.Producer[]>();
  private audioConsumers = new Map<string, mediasoup.types.Consumer[]>();
  private videoConsumers = new Map<string, mediasoup.types.Consumer[]>();

  constructor() {
    this.mediaCodecs = [
      {
        kind: 'audio',
        mimeType: 'audio/opus',
        clockRate: 48000,
        channels: 2,
      },
      {
        kind: 'video',
        mimeType: 'video/VP8',
        clockRate: 90000,
        parameters: {
          'x-google-start-bitrate': 1000,
        },
      },
    ];
  }

  async onModuleInit() {
    await this.createWorker();
  }

  async createWorker() {
    this.worker = await mediasoup.createWorker({
      rtcMinPort: 2000,
      rtcMaxPort: 2020,
    });
    this.worker.on('died', () => {
      console.error('mediasoup worker has died');
      setTimeout(() => process.exit(1), 2000);
    });

    return this.worker;
  }

  async createRouter(roomId: string): Promise<mediasoup.types.Router> {
    const router = await this.worker.createRouter({
      mediaCodecs: this.mediaCodecs,
    });

    this.routers.set(roomId, router);
    console.log(`>> router created for room ${roomId}`);

    return router;
  }

  async getRouter(roomId: string): Promise<mediasoup.types.Router> {
    let router = this.routers.get(roomId);

    if (!router) {
      router = await this.createRouter(roomId);
    }

    return router;
  }

  async createWebRtcTransport(
    data: any,
  ): Promise<mediasoup.types.WebRtcTransport> {
    const { roomId, consumer, socketId } = data;
    try {
      const router = await this.getRouter(roomId);
      const webRtcTransport_options = {
        listenIps: [
          {
            ip: '127.0.0.1',
            // announcedIp: '127.0.0.1'
          },
        ],
        enableUdp: true,
        enableTcp: true,
        preferUdp: true,
      };
      const transport = await router.createWebRtcTransport(
        webRtcTransport_options,
      );

      this.setTransport(consumer, socketId, transport);

      transport.on('dtlsstatechange', (dtlsState) => {
        if (dtlsState === 'closed') {
          transport.close();
        }
      });
      return transport;
    } catch (error) {
      console.error(error);
    }
  }

  async setTransport(
    consumer: boolean,
    socketId: string,
    transport: mediasoup.types.WebRtcTransport,
  ) {
    if (!consumer) {
      this.producerTransports.set(socketId, transport);
    } else {
      this.consumerTransports.set(socketId, transport);
    }
  }

  async getTransport(
    socketId: string,
    consumer: boolean,
  ): Promise<mediasoup.types.WebRtcTransport> {
    if (consumer) {
      return this.consumerTransports.get(socketId);
    }

    return this.producerTransports.get(socketId);
  }

  setProducer(
    kind: string,
    roomId: string,
    producer: mediasoup.types.Producer,
  ) {
    if (kind === 'audio') {
      if (!this.audioProducers.has(roomId)) {
        this.audioProducers.set(roomId, []);
      }
      this.audioProducers.get(roomId).push(producer);
    } else {
      if (!this.videoProducers.has(roomId)) {
        this.videoProducers.set(roomId, []);
      }
      this.videoProducers.get(roomId).push(producer);
    }
  }

  getProducers(kind: string, roomId: string) {
    if (kind === 'audio') {
      return this.audioProducers.get(roomId);
    } else {
      return this.videoProducers.get(roomId);
    }
  }

  async setConsumer(
    kind: string,
    roomId: string,
    consumer: mediasoup.types.Consumer,
  ) {
    if (kind === 'audio') {
      if (!this.audioConsumers.has(roomId)) {
        this.audioConsumers.set(roomId, []);
      }
      this.audioConsumers.get(roomId).push(consumer);
    } else {
      if (!this.videoConsumers.has(roomId)) {
        this.videoConsumers.set(roomId, []);
      }
      this.videoConsumers.get(roomId).push(consumer);
    }
  }

  async getConsumer(kind: string, roomId: string, consumerId: string) {
    if (kind === 'audio') {
      return this.audioConsumers
        .get(roomId)
        .find((consumer) => consumer.id === consumerId);
    } else {
      return this.videoConsumers
        .get(roomId)
        .find((consumer) => consumer.id === consumerId);
    }
  }

  removeTransport(socketId: string) {
    const producer = this.producerTransports.get(socketId);
    const consumer = this.consumerTransports.get(socketId);

    this.producerTransports.delete(socketId);
    this.consumerTransports.delete(socketId);

    producer?.close();
    consumer?.close();

    console.log('>> transport removed', socketId);
  }

  removeProducer(roomId: string, producerId: string) {
    const audioProducers = this.audioProducers.get(roomId);
    const videoProducers = this.videoProducers.get(roomId);

    if (audioProducers) {
      const index = audioProducers.findIndex(
        (producer) => producer.id === producerId,
      );
      audioProducers.splice(index, 1);
      console.log('>> producer removed', audioProducers);
    }

    if (videoProducers) {
      const index = videoProducers.findIndex(
        (producer) => producer.id === producerId,
      );
      videoProducers.splice(index, 1);
      console.log('>> producer removed', audioProducers);
    }
  }

  removeConsumer(roomId: string, producerId: string) {
    const audioConsumers = this.audioConsumers.get(roomId);
    const videoConsumers = this.videoConsumers.get(roomId);

    if (audioConsumers) {
      const index = audioConsumers.findIndex(
        (consumer) => consumer.id === producerId,
      );
      audioConsumers.splice(index, 1);
      console.log('>> consumer removed', audioConsumers);
    }

    if (videoConsumers) {
      const index = videoConsumers.findIndex(
        (consumer) => consumer.id === producerId,
      );
      videoConsumers.splice(index, 1);
      console.log('>> consumer removed', audioConsumers);
    }
  }

  getWorker() {
    return this.worker;
  }
}
