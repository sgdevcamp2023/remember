import React, { useState } from "react";
import { Link } from "react-router-dom";
import styled from "styled-components";
import "../css/auth.css";
import backImg from "../img/backImg.webp";
import { logInRequest } from "../Request/authRequest";
import AuthStore from "../store/AuthStore";
import { useNavigate } from "react-router-dom";

const Wrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100vh;
  background-image: url(${backImg});
  background-size: cover;
`;

const FormContainer = styled.div`
  width: 30%;
  height: 40%;
  padding: 32px;
  border-radius: 8px;
  background-color: #313338;
  color: white;
`;

const Content = styled.form`
  height: 70%;
  margin-top: 20px;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
`;

export default function LoginPage() {
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const { setAccessToken, setUserId } = AuthStore();
  const handleEmailChange = (e) => {
    setEmail(e.target.value);
  };

  const handlePasswordChange = (e) => {
    setPassword(e.target.value);
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    // 여기서 logInRequest 함수를 호출
    logInRequest(email, password).then((accessToken, refreshToken, userId) => {
      setAccessToken(accessToken);
      setUserId(userId);
      localStorage.setItem("RefreshToken", refreshToken);
      navigate("/");
      // 추후 페이지 전환
    });
  };

  return (
    <Wrapper>
      <FormContainer>
        <h1 style={{ margin: "0px" }}>돌아오신 것을 환영해요!</h1>
        <span>다시 만나니까 너무 반가워요!</span>

        <Content onSubmit={handleSubmit}>
          <label name="userId">
            이메일 또는 전화번호
            <input
              type="email"
              value={email}
              onChange={handleEmailChange}
              name="userId"
              required
            ></input>
          </label>
          <label name="userPw">
            비밀번호
            <input
              type="password"
              value={password}
              onChange={handlePasswordChange}
              name="userPw"
              required
            ></input>
          </label>

          <div style={{ display: "flex", justifyContent: "space-between" }}>
            <Link to="/register" style={{ color: "white" }}>
              회원가입이 필요하신가요?
            </Link>
            <Link to="/forget-Password" style={{ color: "white" }}>
              비밀번호를 잊으셨나요?
            </Link>
          </div>

          <button id="submit" type="submit">
            로그인
          </button>
        </Content>
      </FormContainer>
    </Wrapper>
  );
}
