import axios from "axios";
import AuthStore from "../store/AuthStore";

const UserServerAddr = "http://127.0.0.1:4000/api/user/";

let accessToken = AuthStore.getState().ACCESS_TOKEN;
let userId = AuthStore.getState().USER_ID;

AuthStore.subscribe(
    (newAccessToken) => {
        accessToken = newAccessToken;
    },
    (state) => state.accessToken
);

export const GetUserInfoRequest = async () => {
    axios
        .get(UserServerAddr + `info/${userId}`, {
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

export const PatchChangeProfileRequest = async (data) => {
    axios
        .patch(UserServerAddr + "change-profile", {
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

export const PatchUserNameRequest = async (data) => {
    axios
        .patch(UserServerAddr + "change-name", {
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

export const PatchPasswordRequest = async (data) => {
    axios
        .patch(UserServerAddr + "change-password", {
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