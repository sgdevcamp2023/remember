import { useEffect } from "react";
import AuthStore from "../../store/AuthStore";
import { friendList } from "../../config/mock_data";
import { FriendList } from "../../components/FriendList";

export function AllView() {
  const { FRIEND_LIST, setFriendList } = AuthStore();

  useEffect(() => {
    // const friendList = GetFriendListRequest();
    // if(friendList != null)
    setFriendList(friendList.resultData);
  }, []);

  return (
    <div>
      <h1>모든 친구 - {FRIEND_LIST.length}</h1>
      {FRIEND_LIST.map((friend) => (
        <FriendList key={friend.id} friend={friend}></FriendList>
      ))}
    </div>
  );
}