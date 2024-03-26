import { useEffect, useState } from "react";
import { GetFriendReceiveListRequest, GetFriendSendListRequest } from "../../Request/friendRequest";
import { AddSendList, AddReceiveList } from "../../components/FriendList";

export function WaitListView() {
  const [SendList, setSendList] = useState([]);
  const [ReceiveList, setReceiveList] = useState([]);

  useEffect(() => {
    GetFriendSendListRequest().then((response) => {
      if (response.status === 200) {
        if (Array.isArray(response.data))
          setSendList(response.data);
        else
          setSendList([]);
      }
    }).catch((error) => {
      console.error("데이터를 받아오는 데 실패했습니다:", error);
    });

    GetFriendReceiveListRequest().then((response) => {
      if (response.status === 200) {
        if (Array.isArray(response.data))
          setReceiveList(response.data);
        else
          setReceiveList([]);
      }
    }).catch((error) => {
      console.error("데이터를 받아오는 데 실패했습니다:", error);
    });
  }, []);

  return (
    <div>
      <h1>대기 중 - {SendList.length + ReceiveList.length}</h1>

      {ReceiveList.map((friend) => (
        <AddReceiveList key={friend.id} friend={friend}></AddReceiveList>
      ))}
      {SendList.map((friend) => (
        <AddSendList key={friend.id} friend={friend}></AddSendList>
      ))}
    </div>
  );
}