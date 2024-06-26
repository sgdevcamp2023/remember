import { create } from "zustand";

const AuthStore = create((set) => ({
  ACCESS_TOKEN: null,
  USER_ID: null,
  FRIEND_LIST: [],
  USER_NAME: null,
  USER_PROFILE: null,
  REDIRECT_URL: null,

  setAccessToken: (token) => set({ ACCESS_TOKEN: token }),
  removeAccessToken: () => set({ ACCESS_TOKEN: null }),
  setUserId: (userId) => set({ USER_ID: userId }),
  removeUserId: () => set({ USER_ID: null }),
  setFriendList: (friendList) => set({ FRIEND_LIST: friendList }),
  removeFriendList: () => set({ FRIEND_LIST: [] }),
  deleteFriend: (friendList, friendEmail) =>
    set({
      FRIEND_LIST: friendList.filter((friend) => friend.email !== friendEmail),
    }),
  setUserName: (userName) => set({ USER_NAME: userName }),
  removeUserName: () => set({ USER_NAME: "" }),
  setUserProfile: (userProfile) => set({ USER_PROFILE: userProfile }),
  removeUserProfile: () => set({ USER_PROFILE: "" }),
  setRedirectUrl: (redirectUrl) => set({ REDIRECT_URL: redirectUrl }),
  removeRedirectUrl: () => set({ REDIRECT_URL: "" }),
}));

export default AuthStore;
