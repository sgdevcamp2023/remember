import axios from "axios";

const UserAuthServerAddr = "https://0chord.store/api/auth/";

export const logInRequest = async (email, password) => {
  return await axios
    .post(UserAuthServerAddr + "login", {
      email: email,
      password: password,
    })
    .then((response) => {
      // Body에 accessToken, refreshToken이 담김
      return response;
    })
    .catch((error) => {
      console.error("데이터를 받아오는 데 실패했습니다:", error);
      return error.response;
    });
};

export const registerRequest = async (email, password, userName, checksum) => {
  return await axios
    .post(UserAuthServerAddr + "register", {
      email: email,
      userName: userName,
      password: password,
      emailChecksum: checksum,
    })
    .then((response) => {
      console.log(response);
      return response;
    })
    .catch((error) => {
      console.error("데이터를 받아오는 데 실패했습니다:", error);
      return error.response;
    });
};

export const checksumRequest = async (email) => {
  return await axios
    .post(UserAuthServerAddr + "send-email", {
      email: email,
    })
    .then((response) => {
      return response;
    })
    .catch((error) => {
      console.error("데이터를 받아오는 데 실패했습니다:", error);
      return error.response;
    });
};

export const forgetPasswordRequest = async (email) => {
  return await axios
    .post(UserAuthServerAddr + "reset-password", {
      email: email,
    })
    .then((response) => {
      console.log(response);
      return response;
    })
    .catch((error) => {
      console.error("데이터를 받아오는 데 실패했습니다:", error);
      return error.response;
    });
};