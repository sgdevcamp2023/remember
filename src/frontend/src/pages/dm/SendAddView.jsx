import { useState } from "react";
import styled from "styled-components";
import { PostAddFriendReqeust } from "../../Request/friendRequest";
import AuthStore from "../../store/AuthStore";

const AddDiv = styled.div`
  display: flex;
  align-items: center;
  height: 40%;
  justify-content: space-between;
  margin-left: 20px;
  margin-right: 20px;
  color: white;
`;

const AddInput = styled.input`
  width: 70%;
  height: 30px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  padding: 10px;
  font-size: 16px;
  border: 1px solid #313338;
`
const AddButton = styled.button`
  width: 100px;
  height: 60px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 20px;
  font-weight: bold;
`

export function SendAddView() {
  const { USER_ID } = AuthStore();
  const [email, setEmail] = useState("");

  const handleEmailChange = (e) => {
    setEmail(e.target.value);
  }

  const handleAddFriend = (e) => {
    e.preventDefault();
    if (email === "") return;

    console.log(email);
    
    PostAddFriendReqeust({ myId: USER_ID, friendEmail: email })
      .then((response) => {
        if (response.status === 200) {
          alert("친구 추가 성공");
        } else {
          alert("친구 추가 실패");
        }
      })
      .catch((error) => {
        alert("친구 추가 실패")
      });
  }

  return (
    <div>
      <h1>친구 추가하기</h1>
      <form onSubmit={handleAddFriend}>
        <AddDiv>
          <AddInput
            type="email"
            value={email}
            onChange={handleEmailChange}
            name="email"
            placeholder="추가하고 싶은 친구의 이메일을 입력하세요"
            required
          ></AddInput>
          <AddButton id="submit" type="submit">추가하기</AddButton>
        </AddDiv>
      </form>
    </div>
  );

}