import {create} from "zustand";

const CommunityStore = create((set) => ({
  GUILD_LIST: [],
  CHANNEL_LIST: [],
  CATEGORY_LIST: [],
  DM_ROOM_LIST: [],
  USER_STATE_MAP: {},
  VOICE_USER_STATE_MAP: {},
  CHANNEL_MODAL: false,

  setGuildList: (guildList) => set({GUILD_LIST: guildList}),
  removeGuildList: () => set({GUILD_LIST: []}),
  setChannelList: (channelList) => set({CHANNEL_LIST: channelList}),
  removeChannelList: () => set({CHANNEL_LIST: []}),
  setCategoryList: (categoryList) => set({CATEGORY_LIST: categoryList}),
  removeCategoryList: () => set({CATEGORY_LIST: []}),
  setDmRoomList: (dmRoomList) => set({DM_ROOM_LIST: dmRoomList}),
  removeDmRoomList: () => set({DM_ROOM_LIST: []}),
  setUserStateMap: (userStateMap) => set({USER_STATE_MAP: userStateMap}),
  removeUserStateMap: () => set({USER_STATE_MAP: {}}),
  setVoiceUserStateMap: (voiceUserStateMap) => set({VOICE_USER_STATE_MAP: voiceUserStateMap}),
  removeVoiceUserStateMap: () => set({VOICE_USER_STATE_MAP: {}}),
  setChannelModalOpen: (modal) => set({CHANNEL_MODAL: modal}),
  setChannelModalClose: (modal) => set({CHANNEL_MODAL: modal}),
}));

export default CommunityStore;
