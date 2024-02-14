import { useEffect } from "react";
import AuthStore from "../../store/AuthStore";
import { GetFriendListRequest } from "../../Request/friendRequest";
import { FriendList } from "../../components/FriendList";

export function AllView() {
  const { FRIEND_LIST, setFriendList } = AuthStore();

  useEffect(() => {
    GetFriendListRequest().then((response) => {
      if (Array.isArray(response))
        setFriendList(response);
    }).catch((error) => {
      setFriendList([]);
    });
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