import axios from "axios";
import AuthStore from "../store/AuthStore";

const CommunityServerAddr = "http://34.22.109.45:8000/";
// const CommunityServerAddr = "http://34.22.109.45:4000/";

let accessToken = AuthStore.getState().ACCESS_TOKEN;

AuthStore.subscribe(
  (newAccessToken) => {
    accessToken = newAccessToken;
  },
  (state) => state.accessToken
);

export const getGuildListRequest = async (userId) => {
  return await axios.get(
    CommunityServerAddr + `api/community/check/guild/${userId}`,
    {
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    }
  );
};

export const getCategoryListRequest = async (guildId, userId) => {
  axios
    .get(
      CommunityServerAddr + `api/community/check/category/${guildId}/${userId}`,
      {
        headers: { Authorization: `Bearer ${accessToken}` },
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
  return await axios.get(
    CommunityServerAddr +
      `api/community/check/guild/channel/${guildId}/${userId}`,
    {
      headers: { Authorization: `Bearer ${accessToken}` },
    }
  );
};

export const getUserDmRoom = async (userId) => {
  return await axios.get(
    CommunityServerAddr + `api/community/check/room/${userId}`,
    {
      headers: {
        headers: { Authorization: `Bearer ${accessToken}` },
      },
    }
  );
};

export const createNewGuild = async (formData) => {
  return axios.post(
    CommunityServerAddr + `api/community/registration/guild`,
    formData,
    {
      headers: {
        headers: { Authorization: `Bearer ${accessToken}` },
      },
    }
  );
};

// getUserStateAndVoice 함수를 정의
export const getUserStateAndVoice = async (CURRENT_VIEW_GUILD, USER_ID) => {
  return await axios.get(
    CommunityServerAddr +
      `api/community/guild/${CURRENT_VIEW_GUILD}/${USER_ID}`,
    {
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    }
  );
};

export const createChannel = async (data) => {
  return await axios.post(
    CommunityServerAddr + "api/community/registration/category/channel",
    data,
    {
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    }
  );
};
