import { useEffect, useState } from "react";
import { GetFriendReceiveListRequest, GetFriendSendListRequest } from "../../Request/friendRequest";
import { AddSendList, AddReceiveList } from "../../components/FriendList";
import { friendList } from "../../config/mock_data";

export function WaitListView() {
  const [ SendList, setSendList ] = useState([]);
  const [ ReceiveList, setReceiveList ] = useState([]);

  useEffect(() => {
    // var sendList = GetFriendSendListRequest();
    var sendList = friendList.resultData;
    if (sendList != null)
      setSendList(sendList);

    // var receiveList = GetFriendReceiveListRequest();
    var receiveList = friendList.resultData; 
    if (receiveList != null)
      setReceiveList(receiveList);

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