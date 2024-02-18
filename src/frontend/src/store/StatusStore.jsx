import { create } from "zustand";

const StatusStore = create((set) => ({
  STATUS: null,

  setStatus: (status) => set({ STATUS: status}),
  removeStatus: () => set({ STATUS: null })
}));

export default StatusStore;