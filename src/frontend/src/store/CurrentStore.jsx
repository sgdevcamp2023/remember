import { create } from "zustand";

const CurrentStore = create((set) => ({
  CURRENT_JOIN_GUILD: null,
  CURRENT_JOIN_CHANNEL: null,
  CURRENT_VIEW_GUILD: null,
  CURRENT_VIEW_CHANNEL: null,
  CURRENT_VIEW_GUILD_NAME: null,
  CURRENT_VIEW_CHANNEL_TYPE: null,
  PARSED_ROOM_URL: null,

  setCurrentJoinGuild: (guild) => set({ CURRENT_JOIN_GUILD: guild }),
  removeCurrentJoinGuild: () => set({ CURRENT_JOIN_GUILD: null }),
  setCurrentJoinChannel: (channel) => set({ CURRENT_JOIN_CHANNEL: channel }),
  removeCurrentJoinChannel: () => set({ CURRENT_JOIN_CHANNEL: null }),
  setCurrentViewGuild: (guild) => set({ CURRENT_VIEW_GUILD: guild }),
  removeCurrentViewGuild: () => set({ CURRENT_VIEW_GUILD: null }),
  setCurrentViewChannel: (channel) => set({ CURRENT_VIEW_CHANNEL: channel }),
  removeCurrentViewChannel: () => set({ CURRENT_VIEW_CHANNEL: null }),
  setCurrentViewGuildName: (guildName) =>
    set({ CURRENT_VIEW_GUILD_NAME: guildName }),
  removeCurrentViewGuildName: () => set({ CURRENT_VIEW_GUILD_NAME: null }),
  setCurrentViewChannelType: (type) => set({ CURRENT_VIEW_CHANNEL_TYPE: type }),
  removeCurrentViewChannelType: () => set({ CURRENT_VIEW_CHANNEL_TYPE: null }),
  setParsedRoomUrl: (url) => set({ PARSED_ROOM_URL: url }),
  removeParsedRoomUrl: () => set({ PARSED_ROOM_URL: null }),
}));

export default CurrentStore;
