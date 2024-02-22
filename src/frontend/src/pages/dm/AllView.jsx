import { useEffect } from "react";
import AuthStore from "../../store/AuthStore";
import { GetFriendListRequest } from "../../Request/friendRequest";
import { FriendList } from "../../components/FriendList";

export function AllView() {
  const { FRIEND_LIST, setFriendList } = AuthStore();

  useEffect(() => {
    // GetFriendListRequest()
    //   .then((response) => {
    //     if (response.status === 200) {
    //       if (Array.isArray(response.data)) setFriendList(response.data);
    //     }
    //   })
    //   .catch((error) => {
    //     console.error("데이터를 받아오는 데 실패했습니다:", error);
    //     setFriendList([]);
    //   });
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
