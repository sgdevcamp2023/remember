import AuthStore from "../store/AuthStore";
import { CancleAddFriendRequest, DeleteFriendRequest, PostAddFriendReqeust, PostRefuseFriendReqeust } from "../Request/friendRequest";
import { List, ProfileBox, ButtonBox, Button } from '../styled/Main.jsx';

const defaultImage = "https://storage.googleapis.com/remember-harmony/d68c3d6c-4683-4ddf-892b-8a73e3678145";

// 친구 목록을 보여주는 컴포넌트
export const FriendList = ({ friend }) => {
  const { USER_ID } = AuthStore();


  const sendChatClick = (e) => {
    e.preventDefault();

    // TODO
    console.log(`Send chat with friend ${friend.email}`);
  };

  const deleteFriendClick = (e) => {
    e.preventDefault();

    DeleteFriendRequest({ myId: USER_ID, friendEmail: friend.email })
      .then((response) => {
        alert("친구 삭제 성공");
      })
      .catch((error) => {
        console.error("친구 삭제 실패", error);
      });

    console.log(`Delete with friend ${friend.email}`);
  }

  return (
    <List>
      <ProfileBox>
        <img src={(friend.profileUrl === "") ? friend.profileUrl : defaultImage}
          alt={friend.name} style={{ width: '50px', height: '50px', borderRadius: '50%' }} />
        <span>{friend.name}</span>
      </ProfileBox>

      <ButtonBox>
        <Button onClick={sendChatClick}>채팅</Button>
        <Button onClick={deleteFriendClick}>삭제</Button>
      </ButtonBox>
    </List>
  );
}

// 친구 추가 요청을 보내거나 받은 목록을 보여주는 컴포넌트
export const AddReceiveList = ({ friend }) => {
  // 친구 추가 허락
  const { USER_ID } = AuthStore();

  const addAcceptClick = (e) => {
    e.preventDefault();

    PostAddFriendReqeust({ myId: USER_ID, friendEmail: friend.email })
      .then((response) => {
        alert("친구 추가 성공");
      })
      .catch((error) => {
        console.error("친구 추가 실패", error);
      });
  }

  // 친구 추가 거부
  const addRefuseClick = (e) => {
    e.preventDefault();

    PostRefuseFriendReqeust({ myId: USER_ID, friendEmail: friend.email })
      .then((response) => {
        alert("친구 추가 거부 성공");
      })
      .catch((error) => {
        console.error("친구 추가 거부 실패", error);
      });
  };

  return (
    <List>
      <ProfileBox>
        <img src={(friend.profileUrl === "") ? friend.profileUrl : defaultImage}
          alt={friend.name} style={{ width: '50px', height: '50px', borderRadius: '50%' }} />
        <span>{friend.name}</span>
      </ProfileBox>

      <ButtonBox>
        <Button onClick={addAcceptClick}>허락</Button>
        <Button onClick={addRefuseClick}>거절</Button>
      </ButtonBox>
    </List>
  );
}

// 친구 추가 요청 보내는 컴포넌트
export const AddSendList = ({ friend }) => {
  const { USER_ID } = AuthStore();
  const sendAddCancleClick = (e) => {
    e.preventDefault();

    CancleAddFriendRequest({ myId: USER_ID, friendEmail: friend.email })
      .then((response) => {
        alert("친구 추가 취소 성공");
      })
      .catch((error) => {
        console.error("친구 추가 취소 실패", error);
      });
  }

  return (
    <List>
      <ProfileBox>
        <img src={(friend.profileUrl === "") ? friend.profileUrl : defaultImage}
          alt={friend.name} style={{ width: '50px', height: '50px', borderRadius: '50%' }} />
        <span>{friend.name}</span>
      </ProfileBox>

      <ButtonBox>
        <Button onClick={sendAddCancleClick}>취소</Button>
      </ButtonBox>
    </List>
  );
}