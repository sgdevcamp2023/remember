import axios from "axios";
import AuthStore from "../store/AuthStore";

const UserServerAddr = "https://0chord.store/api/user/";

export const GetUserInfoRequest = async () => {
    return await axios
        .get(UserServerAddr + `info/${AuthStore.getState().USER_ID}`, {
            headers: {
                Authorization: `${AuthStore.getState().ACCESS_TOKEN}`,
            },
        })
        .then((response) => {
            return response.data;
        })
        .catch((error) => {
            console.error("데이터를 받아오는 데 실패했습니다:", error);
            return error.response;
        });
};

export const PatchChangeProfileRequest = async (data) => {
    return await axios
        .patch(UserServerAddr + "change-profile", data, {
            headers: {
                Authorization: `${AuthStore.getState().ACCESS_TOKEN}`,
            },
        })
        .then((response) => {
            return response;
        })
        .catch((error) => {
            console.error("데이터를 받아오는 데 실패했습니다:", error);
            return error.response;
        });
};

export const PatchUserNameRequest = async (data) => {
    return await axios
        .patch(UserServerAddr + "change-name", data, {
            headers: {
                Authorization: `${AuthStore.getState().ACCESS_TOKEN}`,
            },
        })
        .then((response) => {
            return response;
        })
        .catch((error) => {
            console.error("데이터를 받아오는 데 실패했습니다:", error);
            return error.response;
        });
};

export const PatchPasswordRequest = async (data) => {
    return await axios
        .patch(UserServerAddr + "change-password", data, {
            headers: {
                Authorization: `${AuthStore.getState().ACCESS_TOKEN}`,
            },
        })
        .then((response) => {
            return response;
        })
        .catch((error) => {
            console.error("데이터를 받아오는 데 실패했습니다:", error);
            return error.response;
        });
};

export const logoutRequest = async (data) => {
    return await axios
        .post(UserServerAddr + "logout", data, {
            headers: {
                Authorization: `${AuthStore.getState().ACCESS_TOKEN}`,
            },
        })
        .then((response) => {
            return response;
        })
        .catch((error) => {
            console.error("데이터를 받아오는 데 실패했습니다:", error);
            return error.response;
        });
}