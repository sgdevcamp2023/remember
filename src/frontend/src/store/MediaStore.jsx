import { create } from "zustand";

let params = {
  encodings: [
    {
      rid: "r0",
      maxBitrate: 100000,
      scalabilityMode: "S1T3",
    },
    {
      rid: "r1",
      maxBitrate: 300000,
      scalabilityMode: "S1T3",
    },
    {
      rid: "r2",
      maxBitrate: 900000,
      scalabilityMode: "S1T3",
    },
  ],
  codecOptions: {
    videoGoogleStartBitrate: 1000,
  },
};

const MediaStore = create((set) => ({
  DEVICE: null,
  RTP_CAPABILITIES: null,
  AUDIO_PRODUCER: null,
  VIDEO_PRODUCER: null,
  DISPLAY_PRODUCER: null,
  RECV_AUDIO_CONSUMER: [],
  RECV_VIDEO_CONSUMER: [],
  SEND_TRANSPORT: null,
  RECV_TRANSPORT: null,
  AUDIO_PARAMS: null,
  VIDEO_PARAMS: { params },
  TRY_GUILD: null,
  TRY_CHANNEL: null,

  setDevice: (device) => set({ DEVICE: device }),
  removeDevice: () => set({ DEVICE: null }),
  setRtpCapabilities: (rtpCapabilities) =>
    set({ RTP_CAPABILITIES: rtpCapabilities }),
  removeRtpCapabilities: () => set({ RTP_CAPABILITIES: null }),
  setAudioProducer: (producer) => set({ AUDIO_PRODUCER: producer }),
  removeAudioProducer: () => set({ AUDIO_PRODUCER: null }),
  setVideoProducer: (producer) => set({ VIDEO_PRODUCER: producer }),
  removeVideoProducer: () => set({ VIDEO_PRODUCER: null }),
  setDisplayProducer: (producer) => set({ DISPLAY_PRODUCER: producer }),
  removeDisplayProducer: () => set({ DISPLAY_PRODUCER: null }),
  setSendTransport: (transport) => set({ SEND_TRANSPORT: transport }),
  removeSendTransport: () => set({ SEND_TRANSPORT: null }),
  setRecvTransport: (transport) => set({ RECV_TRANSPORT: transport }),
  removeRecvTransport: () => set({ RECV_TRANSPORT: null }),
  setTryGuild: (guild) => set({ TRY_GUILD: guild }),
  removeTryGuild: () => set({ TRY_GUILD: null }),
  setTryChannel: (channel) => set({ TRY_CHANNEL: channel }),
  removeTryChannel: () => set({ TRY_CHANNEL: null }),
  setAudioParams: (params) => set({ AUDIO_PARAMS: params }),
  removeAudioParams: () => set({ AUDIO_PARAMS: null }),
  setVideoParams: (params) =>
    set((state) => ({
      VIDEO_PARAMS: {
        ...state.VIDEO_PARAMS,
        track: params.track,
        type: params.type,
      },
    })),
  removeVideoParams: () => set({ VIDEO_PARAMS: null }),

  setRecvAudioConsumer: (obj) =>
    set((state) => ({
      RECV_AUDIO_CONSUMER: [...state.RECV_AUDIO_CONSUMER, obj],
    })),
}));

export default MediaStore;
