import axios from "axios";

const UserAuthServerAddr = "http://127.0.0.1:4000/api/auth/";
const UserServerAddr = "http://127.0.0.1:4000/api/user";

export const logInRequest = async (email, password) => {

    axios.post(UserAuthServerAddr + "login", {
        email: email,
        password: password,
    })
        .then(response => {
            // Body에 accessToken, refreshToken이 담김  
            if (response.status !== 200) {
                alert(data.description);
            }
            else {
                // TODO
                const { AccessToken, RefreshToken } = response.data;
                console.log("로그인이 완료되었습니다");

                localStorage.setItem("access-token", AccessToken);
                localStorage.setItem("refresh-token", RefreshToken);
            }
            return response.status;
        })
        .catch(error => {
            console.error("데이터를 받아오는 데 실패했습니다:", error);
        });
};

export const registerRequest = async (email, password, userName, checksum) => {
    axios.post(UserAuthServerAddr + "register", {
        email: email,
        userName: userName,
        password: password,
        emailChecksum: checksum
    })
        .then(response => {
            console.log(response);

            if (response.status !== 200) {
                const data = response.json();
                alert(data.description);
            }
            else
                alert("회원가입이 완료되었습니다");
            return response.status;
        })
        .catch(error => {
            console.error("데이터를 받아오는 데 실패했습니다:", error);
        });
}

export const checksumRequest = async (email) => {
    axios.post(UserAuthServerAddr + "send-email", {
        email: email
    })
        .then(response => {
            if (response.status !== 200) {
                const data = response.json();
                alert(data.description);
            }
            else
                alert("인증번호가 이메일로 전송되었습니다");
        })
        .catch(error => {
            console.error("데이터를 받아오는 데 실패했습니다:", error);
        });
}

export const forgetPasswordRequest = async (email) => {
    axios.post(UserAuthServerAddr + "reset-password", {
            email: email
        })
        .then(response => {
            if (response.status !== 200) {
                const data = response.json();
                alert(data.description);
            }
            else
                alert("초기화된 비밀번호가 이메일로 전송되었습니다.");
            return response.status;
        })
        .catch(error => {
            console.error("데이터를 받아오는 데 실패했습니다:", error);
        });
}
// 서버 통신 로직
