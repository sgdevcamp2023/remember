import React, { useState } from "react";
import styled from "styled-components";
import "../css/auth.css";
import backImg from "../img/backImg.webp";
import { registerRequest } from "../server/server";
import { checksumRequest } from "../server/server";
import { Link, useNavigate } from "react-router-dom";

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
    height: 60%;
    padding: 32px;
    border-radius: px;
    background-color: #313338;
    color: white;
`;

const Content = styled.form`
    height: 90%;
    margin-top: 30px;
    margin-right: 30px;
    margin-left: 30px;
    display: flex;
    flex-direction: column;
    justify-content: space-around;
`;

export default function Register() {

    const navigate = useNavigate();
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [checksum, setChecksum] = useState("");
    const [userName, setName] = useState("");
    const handleEmailChange = (e) => {
        setEmail(e.target.value);
    };

    const handlePasswordChange = (e) => {
        setPassword(e.target.value);
    };

    const handleNameChange = (e) => {
        setName(e.target.value);
    }

    const handleChecksumChange = (e) => {
        setChecksum(e.target.value);
    }

    const handleRegister = (e) => {
        e.preventDefault();
        console.log(email, password, userName, checksum);
        // 여기서 logInRequest 함수를 호출
        registerRequest(email, password, userName, checksum)
            .then((status) => {
                if (status === 200)
                    navigate("/");
            })

    };

    const handleChecksum = (e) => {
        e.preventDefault();

        if (email === "") {
            alert("이메일을 입력해주세요.");
            return;
        }
        // 여기서 logInRequest 함수를 호출
        checksumRequest(email);
    }

    return (
        <Wrapper>
            <FormContainer>
                <Content onSubmit={handleRegister}>
                    <label name="email">
                        이메일
                        <input
                            type="email"
                            value={email}
                            onChange={handleEmailChange}
                            name="userId"
                            required
                        ></input>
                    </label>
                    <button id="button" type="button" onClick={handleChecksum}>
                        이메일 인증 번호 전송
                    </button>
                    <label name="checksum">
                        이메일 인증 번호
                        <input
                            type="text"
                            value={checksum}
                            onChange={handleChecksumChange}
                            name="checksum"
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
                    <label name="name">
                        유저 이름
                        <input
                            type="text"
                            value={userName}
                            onChange={handleNameChange}
                            name="name"
                            required
                        ></input>
                    </label>

                    <button id="submit" type="submit">
                        회원가입
                    </button>
                    <br />
                    <Link to="/" style={{ color: "white" }}>뒤로</Link>
                </Content>
            </FormContainer>
        </Wrapper>
    );
};