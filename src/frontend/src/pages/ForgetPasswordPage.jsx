import React, { useState } from "react";
import styled from "styled-components";
import "../css/auth.css";
import backImg from "../img/backImg.webp";
import { forgetPasswordRequest } from "../Request/authRequest";
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
  width: 40%;
  height: 40%;
  padding: 32px;
  border-radius: px;
  background-color: #313338;
  color: white;
`;

const Content = styled.form`
  height: 45%;
  margin-top: 60px;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
`;

export default function ForgetPasswordPage() {
  const navigate = useNavigate();
  const [email, setEmail] = useState("");

  const handleEmailChange = (e) => {
    setEmail(e.target.value);
  };

  const handleForgotPassword = (e) => {
    e.preventDefault();

    // 여기서 logInRequest 함수를 호출
    forgetPasswordRequest(email).then((response) => {
      if (response.status === 200) {
        alert("초기화된 비밀번호가 이메일로 전송되었습니다.");
        navigate("/login");
      } else {
        const data = response.json();
        alert(data.description);
      }
    });
  };

  return (
    <Wrapper>
      <FormContainer>
        <h1 style={{ margin: "0px" }}>당신의 비번을 찾아드립니다</h1>
        <Content onSubmit={handleForgotPassword}>
          <label name="userId">
            이메일
            <input
              type="email"
              value={email}
              onChange={handleEmailChange}
              name="userId"
              required
            ></input>
          </label>
          <button id="submit" type="submit">
            비밀번호 찾기
          </button>
        </Content>
      </FormContainer>
    </Wrapper>
  );
}
