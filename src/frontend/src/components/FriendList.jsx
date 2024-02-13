import styled from "styled-components";
import temp from "../img/temp.png";

const List = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 70px;
  color: white;
`

const ProfileBox = styled.div`
  display: flex;
  width: 300px;
  flex-direction: row;
  align-items: center;
  gap: 10px;
`

const ButtonBox = styled.div`
  display: flex;
  width: 100px;
  flex-direction: row;
  gap: 10px;
`

const Button = styled.button`
  width: 50px;
  height: 30px;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
`

export const FriendList = ({ friend }) => {
  const handleChatClick = (friendEmail) => {
    // 채팅 버튼을 클릭할 때의 동작을 정의합니다. 여기에 채팅을 보내는 로직을 추가하세요.
    console.log(`Chat with friend ${friendEmail}`);
  };

  const handleDeleteFriendClick = (friendEmail) => {
    console.log(`Delete with friend ${friendEmail}`);
  }

  return (
    <List>
      <ProfileBox>
      <img src={temp} alt={friend.name} style={{ width: '50px', height: '50px', borderRadius: '50%' }} />
      <span>{friend.name}</span>
      </ProfileBox>
      
      <ButtonBox>
        <Button onClick={() => handleChatClick(friend.email)}>채팅</Button>
        <Button onClick={() => handleDeleteFriendClick(friend.email)}>삭제</Button>
      </ButtonBox>
    </List>
  );
}

export const AddReceiveList = ({ friend }) => {
  const handleAcceptClick = (friendEmail) => {
    console.log(`Accept with friend ${friendEmail}`);
  }

  const handleRefurseClick = (friendEmail) => {
    // 채팅 버튼을 클릭할 때의 동작을 정의합니다. 여기에 채팅을 보내는 로직을 추가하세요.
    console.log(`Refuse with friend ${friendEmail}`);
  };

  return (
    <List>
      <ProfileBox>
        <img src={temp} alt={friend.name} style={{ width: '50px', height: '50px', borderRadius: '50%' }} />
        <span>{friend.name}</span>
      </ProfileBox>

      <ButtonBox>
        <Button onClick={() => handleAcceptClick(friend.email)}>허락</Button>
        <Button onClick={() => handleRefurseClick(friend.email)}>거절</Button>
      </ButtonBox>
    </List>
  );
}

export const AddSendList = ({ friend }) => {
  const handleCancleClick = (friendEmail) => {
    console.log(`Delete with friend ${friendEmail}`);
  }

  return (
    <List>
      <ProfileBox>
        <img src={temp} alt={friend.name} style={{ width: '50px', height: '50px', borderRadius: '50%' }} />
        <span>{friend.name}</span>
      </ProfileBox>

      <ButtonBox>
        <Button onClick={() => handleCancleClick(friend.email)}>취소</Button>
      </ButtonBox>
    </List>
  );
}