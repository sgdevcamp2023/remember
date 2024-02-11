import { create } from "zustand";

const MediaStore = create((set) => ({
  DEVICE: null,
  RTP_CAPABILITIES: null,
  AUDIO_PRODUCER: null,
  VIDEO_PRODUCER: null,
  RECV_AUDIO_CONSUMER: [],
  RECV_VIDEO_CONSUMER: [],
  SEND_TRANSPORT: null,
  RECV_TRANSPORT: null,
  AUDIO_PARAMS: null,
  VIDEO_PARAMS: null,
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
  setVideoParams: (params) => set({ VIDEO_PARAMS: params }),
  removeVideoParams: () => set({ VIDEO_PARAMS: null }),
  setRecvAudioConsumer: (obj) =>
    set((state) => ({
      RECV_AUDIO_CONSUMER: [...state.RECV_AUDIO_CONSUMER, obj],
    })),
}));

export default MediaStore;
