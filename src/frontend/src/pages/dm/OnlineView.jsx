import { useEffect } from "react";
import AuthStore from "../../store/AuthStore";
import { FriendList } from "../../components/FriendList";
import { GetFriendListRequest } from "../../Request/friendRequest";


export function OnlineView() {
  const { FRIEND_LIST, setFriendList } = AuthStore();

  useEffect(() => {
    GetFriendListRequest().then((response) => {
      if (Array.isArray(response))
        setFriendList(response);
    }).catch((error) => {
      console.error("데이터를 받아오는 데 실패했습니다:", error);
      setFriendList([]);
    });
  }, [setFriendList]);

  let ONLINE_FRIEND_LIST = FRIEND_LIST.filter(friend => friend.isOnline);

  return (
    <div>
      <h1>온라인 친구 - {ONLINE_FRIEND_LIST.length}</h1>
      {ONLINE_FRIEND_LIST.map((friend) => (
        <FriendList key={friend.id} friend={friend}></FriendList>
      ))}
    </div>
  );
}