import { create } from "zustand";

const SocketStore = create((set) => ({
  MAIN_SOCKET: null,
  VOICE_SOCKET: null,

  setMainSocket: (socket) => set({ MAIN_SOCKET: socket }),
  removeMainSocket: () => set({ MAIN_SOCKET: null }),
  setVoiceSocket: (socket) => set({ VOICE_SOCKET: socket }),
  removeVoiceSocket: () => set({ VOICE_SOCKET: null }),
}));

export default SocketStore;
