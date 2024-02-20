import { Injectable, OnModuleInit } from '@nestjs/common';
import * as mediasoup from 'mediasoup';
import * as os from 'os';
import { CreateWebRtcTransportDTO } from 'src/signaling/dto/create-webRtcTransport.dto';

@Injectable()
export class MediasoupService implements OnModuleInit {
  private nextWorkerIndex = 0;
  private workers: mediasoup.types.Worker[] = [];

  private mediaCodecs: mediasoup.types.RtpCodecCapability[];

  private webRtcTransport_options: mediasoup.types.WebRtcTransportOptions;

  private routers = new Map<string, mediasoup.types.Router>();

  // socketId -> producer transport
  private producerTransports = new Map<
    string,
    mediasoup.types.WebRtcTransport
  >();

  // socketId -> consumer transport
  private consumerTransports = new Map<
    string,
    mediasoup.types.WebRtcTransport
  >();

  // socketId -> mediatag -> producerId
  private socketProducerList: Map<string, Map<string, string>> = new Map();

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

    this.webRtcTransport_options = {
      listenIps: [
        {
          ip: process.env.WEBRTC_LISTEN_IP || '127.0.0.1',
          announcedIp: process.env.WEBRTC_ANNOUNCED_IP || '127.0.0.1',
        },
      ],
      enableUdp: true,
      enableTcp: true,
      preferUdp: true,
    };
  }

  async onModuleInit() {
    const numWorkers = os.cpus().length;

    for (let i = 0; i < numWorkers; ++i) {
      await this.createWorker();
    }
  }

  async createWorker() {
    const worker = await mediasoup.createWorker({
      rtcMinPort: 6002,
      rtcMaxPort: 6202,
    });

    worker.on('died', () => {
      console.error('mediasoup worker has died');
      setTimeout(() => process.exit(1), 2000);
    });

    this.workers.push(worker);
    return worker;
  }

  getWorker() {
    const worker = this.workers[this.nextWorkerIndex];
    this.nextWorkerIndex = (this.nextWorkerIndex + 1) % this.workers.length;
    return worker;
  }

  async createRouter(roomId: string): Promise<mediasoup.types.Router> {
    const worker = this.getWorker();
    const router = await worker.createRouter({
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
    data: CreateWebRtcTransportDTO,
  ): Promise<mediasoup.types.WebRtcTransport> {
    const { roomId } = data;

    try {
      const router = await this.getRouter(roomId);
      const transport = await router.createWebRtcTransport(
        this.webRtcTransport_options,
      );

      this.setTransport(data.consumer, data.socketId, transport);

      transport.on('dtlsstatechange', (dtlsState) => {
        switch (dtlsState) {
          case 'connected':
            console.log('>> dtlsstatechange connected');
            break;
          case 'failed':
            console.log('>> dtlsstatechange failed');
            break;
          case 'closed':
            console.log('>> dtlsstatechange closed');
            transport.close();
            break;
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
    if (consumer) {
      this.consumerTransports.set(socketId, transport);
    } else {
      this.producerTransports.set(socketId, transport);
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

  setProdcuerIdByMediaTag(
    socketId: string,
    mediaTag: string,
    producerId: string,
  ) {
    if (!this.socketProducerList.has(socketId)) {
      this.socketProducerList.set(socketId, new Map());
    }
    this.socketProducerList?.get(socketId).set(mediaTag, producerId);
  }

  getProducerIdByMediaTag(socketId: string, mediaTag: string) {
    return this.socketProducerList?.get(socketId)?.get(mediaTag);
  }

  removeProducerIdByMediaTag(socketId: string, mediaTag: string) {
    this.socketProducerList?.get(socketId).delete(mediaTag);
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
    }

    const consumer = this.videoConsumers
      .get(roomId)
      .find((consumer) => consumer.id === consumerId);
    return consumer;
  }

  getConsumers(kind: string, roomId: string) {
    if (kind === 'audio') {
      return this.audioProducers.get(roomId);
    }
    return this.videoProducers.get(roomId);
  }

  removeTransport(socketId: string) {
    const producerTransport = this.producerTransports.get(socketId);
    const consumerTransport = this.consumerTransports.get(socketId);

    this.producerTransports.delete(socketId);
    this.consumerTransports.delete(socketId);

    producerTransport?.close();
    consumerTransport?.close();
  }

  removeProducer(kind: string, roomId: string, producerId: string) {
    const producers =
      kind === 'audio'
        ? this.audioProducers.get(roomId)
        : this.videoProducers.get(roomId);

    const index = producers?.findIndex(
      (producer) => producer.id === producerId,
    );
    producers.splice(index, 1);
  }

  removeConsumer(kind: string, roomId: string, producerId: string) {
    const consumers =
      kind === 'audio'
        ? this.audioConsumers.get(roomId)
        : this.videoConsumers.get(roomId);

    const index = consumers.findIndex((consumer) => consumer.id === producerId);
    consumers.splice(index, 1);
  }

  getSocketProducerList() {
    return this.socketProducerList;
  }
}
