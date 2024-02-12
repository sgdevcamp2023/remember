import axios from "axios";
import AuthStore from "../store/AuthStore";

const FriendServerAddr = "http://127.0.0.1:4000/api/user/friends/";

let accessToken = AuthStore.getState().ACCESS_TOKEN;
let userId = AuthStore.getState().USER_ID;

AuthStore.subscribe(
  (newAccessToken) => {
    accessToken = newAccessToken;
  },
  (state) => state.accessToken
);

export const GetFriendListRequest = () => {
  axios
    .get(FriendServerAddr + `list/${userId}`, {
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    })
    .then((response) => {
      console.log(response);
      return response.data;
    })
    .catch((error) => {
      console.error("데이터를 받아오는 데 실패했습니다:", error);
    });
};

export const GetFriendSendListRequest = () => {
  axios
    .get(FriendServerAddr + `request/send-list/${userId}`, {
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    })
    .then((response) => {
      console.log(response);
      return response.data;
    })
    .catch((error) => {
      console.error("데이터를 받아오는 데 실패했습니다:", error);
    });
};

export const GetFriendReceiveListRequest = () => {
  axios
    .get(FriendServerAddr + `request/receive-list/${userId}`, {
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    })
    .then((response) => {
      console.log(response);
      return response.data;
    })
    .catch((error) => {
      console.error("데이터를 받아오는 데 실패했습니다:", error);
    });
};

export const PostAddFriendReqeust = (data) => {
  axios
    .post(FriendServerAddr + "request/send", data, {
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    })
    .then((response) => {
      console.log(response);
      return response.status;
    })
    .catch((error) => {
      console.error("데이터를 받아오는 데 실패했습니다:", error);
    });
};

export const PostAcceptFriendReqeust = (data) => {
  axios
    .post(FriendServerAddr + "request/Accept", data, {
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    })
    .then((response) => {
      console.log(response);
      return response.status;
    })
    .catch((error) => {
      console.error("데이터를 받아오는 데 실패했습니다:", error);
    });
}

export const PostRefuseFriendReqeust = (data) => {
  axios
    .post(FriendServerAddr + "request/refuse", data, {
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    })
    .then((response) => {
      console.log(response);
      return response.status;
    })
    .catch((error) => {
      console.error("데이터를 받아오는 데 실패했습니다:", error);
    });
};

export const PostRemoveFriendReqeust = (data) => {
  axios
    .post(FriendServerAddr + "request/refuse", data, {
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    })
    .then((response) => {
      console.log(response);
      return response.status;
    })
    .catch((error) => {
      console.error("데이터를 받아오는 데 실패했습니다:", error);
    });
};

export const CancleAddFriendRequest = (data) => {
  axios
    .delete(FriendServerAddr + "request/cancle", data, {
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    })
    .then((response) => {
      console.log(response);
      return response.status;
    })
    .catch((error) => {
      console.error("데이터를 받아오는 데 실패했습니다:", error);
    });
}

export const DeleteFriendRequest = (data) => {
  axios
    .delete(FriendServerAddr + "delete", data, {
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    })
    .then((response) => {
      console.log(response);
      return response.status;
    })
    .catch((error) => {
      console.error("데이터를 받아오는 데 실패했습니다:", error);
    });
}