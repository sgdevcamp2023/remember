import { create } from "zustand";

const CommunityStore = create((set) => ({
  GUILD_LIST: [],
  CHANNEL_LIST: [],
  CATEGORY_LIST: [],
  DM_ROOM_LIST: [],

  setGuildList: (guildList) => set({ GUILD_LIST: guildList }),
  removeGuildList: () => set({ GUILD_LIST: [] }),
  setChannelList: (channelList) => set({ CHANNEL_LIST: channelList }),
  removeChannelList: () => set({ CHANNEL_LIST: [] }),
  setCategoryList: (categoryList) => set({ CATEGORY_LIST: categoryList }),
  removeCategoryList: () => set({ CATEGORY_LIST: [] }),
  setDmRoomList: (dmRoomList) => set({ DM_ROOM_LIST: dmRoomList }),
  removeDmRoomList: () => set({ DM_ROOM_LIST: [] }),
}));

export default CommunityStore;
