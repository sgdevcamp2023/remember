import axios from "axios";
import AuthStore from "../store/AuthStore";
import CommunityStore from "../store/CommunityStore";

const CommunityServerAddr = "http://localhost:8000/";

let accessToken = AuthStore.getState().ACCESS_TOKEN;

AuthStore.subscribe(
  (newAccessToken) => {
    accessToken = newAccessToken;
  },
  (state) => state.accessToken
);

export const getGuildListRequest = async (userId) => {
  axios
    .get(CommunityServerAddr + `/api/community/check/guild/${userId}`, {
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

export const getCategoryListRequest = async (guildId, userId) => {
  axios
    .get(
      CommunityServerAddr +
      `/api/community/check/category/${guildId}/${userId}`,
      {
        headers: {Authorization: `Bearer ${accessToken}`},
      }
    )
    .then((response) => {
      console.log(response);
      return response.data;
    })
    .catch((error) => {
      console.error("데이터를 받아오는 데 실패했습니다:", error);
    });
};

export const getChannelListRequest = async (guildId, userId) => {
  axios
    .get(
      CommunityServerAddr + `/api/community/check/channel/${guildId}/${userId}`,
      {
        headers: {Authorization: `Bearer ${accessToken}`},
      }
    )
    .then((response) => {
      console.log(response);
      return response.data;
    })
    .catch((error) => {
      console.error("데이터를 받아오는 데 실패했습니다:", error);
    });
};

export const getUserDmRoom = async (userId) => {
};

export const createNewGuild = async (formData) => {
  axios
    .post(CommunityServerAddr + `/api/community/registration/guild`, formData, {
      headers: {
        headers: {Authorization: `Bearer ${accessToken}`},
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

// getUserStateAndVoice 함수를 정의
export const getUserStateAndVoice = async (CURRENT_VIEW_GUILD, USER_ID) => {
  const result = await axios.get(`http://34.22.109.45:8000/api/community/guild/${CURRENT_VIEW_GUILD}/${USER_ID}`, {
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
  })
    // .then(response => {
    //   console.log(response.data.resultData.guildStates);
    //   return response.data;
    // })
    // .catch((error) => {
    //   console.error("데이터를 받아오는 데 실패했습니다:", error);
    // });
  return result;
};