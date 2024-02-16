import { create } from "zustand";

const AuthStore = create((set) => ({
  ACCESS_TOKEN: "null",
  USER_ID: 1,
  FRIEND_LIST: [],

  setAccessToken: (token) => set({ ACCESS_TOKEN: token }),
  removeAccessToken: () => set({ ACCESS_TOKEN: null }),
  setUserId: (userId) => set({ USER_ID: userId }),
  removeUserId: () => set({ USER_ID: null }),
  setFriendList: (friendList) => set({ FRIEND_LIST: friendList }),
  removeFriendList: () => set({ FRIEND_LIST: [] }),
  deleteFriend: (friendList, friendEmail) => set({ FRIEND_LIST: friendList.filter(friend => friend.email !== friendEmail)}),
}));

export default AuthStore;