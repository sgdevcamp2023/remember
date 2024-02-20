import { create } from "zustand";

const ChannelStore = create((set) => ({
  CHANNEL: null,

  setChannel: (channel) => set({ CHANNEL: channel}),
  removeChannel: () => set({ CHANNEL: null })
}));

export default ChannelStore;