import { create } from "zustand";

const ChatStore = create((set) => ({
  MESSAGE: null,

  setMessage: (message) => set({ MESSAGE: message}),
  removeMessage: () => set({ MESSAGE: null })
}));

export default ChatStore;