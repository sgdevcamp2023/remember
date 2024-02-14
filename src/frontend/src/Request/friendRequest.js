import axios from "axios";
import AuthStore from "../store/AuthStore";

const FriendServerAddr = "http://127.0.0.1:4000/api/user/friend/";

export const GetFriendListRequest = async () => {
  return await axios
    .get(FriendServerAddr + `list/${AuthStore.getState().USER_ID}`, {
      headers: {
        Authorization: `${AuthStore.getState().ACCESS_TOKEN}`,
      },
    })
    .then((response) => {
      console.log(response.data);
      return response.data;
    })
    .catch((error) => {
      console.error("데이터를 받아오는 데 실패했습니다:", error);
    });
};

export const GetFriendSendListRequest = async () => {
  return await axios
    .get(FriendServerAddr + `request/send-list/${AuthStore.getState().USER_ID}`, {
      headers: {
        Authorization: `${AuthStore.getState().ACCESS_TOKEN}`,
      },
    })
    .then((response) => {
      return response.data;
    })
    .catch((error) => {
      console.error("데이터를 받아오는 데 실패했습니다:", error);
    });
};

export const GetFriendReceiveListRequest = async () => {
  return await axios
    .get(FriendServerAddr + `request/receive-list/${AuthStore.getState().USER_ID}`, {
      headers: {
        Authorization: `${AuthStore.getState().ACCESS_TOKEN}`,
      },
    })
    .then((response) => {
      return response.data;
    })
    .catch((error) => {
      console.error("데이터를 받아오는 데 실패했습니다:", error);
    });
};

export const PostAddFriendReqeust = async (data) => {
  return await axios
    .post(FriendServerAddr + "request/send", data, {
      headers: {
        Authorization: `${AuthStore.getState().ACCESS_TOKEN}`,
      },
    })
    .then((response) => {
      return response.status;
    })
    .catch((error) => {
      console.error("데이터를 받아오는 데 실패했습니다:", error);
    });
};

export const PostAcceptFriendReqeust = async (data) => {
  return await axios
    .post(FriendServerAddr + "request/Accept", data, {
      headers: {
        Authorization: `${AuthStore.getState().ACCESS_TOKEN}`,
      },
    })
    .then((response) => {
      return response.status;
    })
    .catch((error) => {
      console.error("데이터를 받아오는 데 실패했습니다:", error);
    });
}

export const PostRefuseFriendReqeust = async (data) => {
  return await axios
    .post(FriendServerAddr + "request/refuse", data, {
      headers: {
        Authorization: `${AuthStore.getState().ACCESS_TOKEN}`,
      },
    })
    .then((response) => {
      return response.status;
    })
    .catch((error) => {
      console.error("데이터를 받아오는 데 실패했습니다:", error);
    });
};

export const CancleAddFriendRequest = async (data) => {
  return await axios
    .delete(FriendServerAddr + "request/cancle", {
      data: data,
      headers: {
        Authorization: `${AuthStore.getState().ACCESS_TOKEN}`,
      },
    })
    .then((response) => {
      return response.status;
    })
    .catch((error) => {
      console.error("데이터를 받아오는 데 실패했습니다:", error);
    });
}

export const DeleteFriendRequest = async (data) => {
  return await axios
    .delete(FriendServerAddr + "delete", {
      data: data,
      headers: {
        Authorization: `${AuthStore.getState().ACCESS_TOKEN}`,
      },
    })
    .then((response) => {
      return response.status;
    })
    .catch((error) => {
      console.error("데이터를 받아오는 데 실패했습니다:", error);
    });
}