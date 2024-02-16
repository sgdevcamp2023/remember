import React, {useEffect, useState} from 'react'
import "../css/Status.css"
import CommunityStore from "../store/CommunityStore";

const Status = () => {

  const {USER_STATE_MAP} = CommunityStore();
  const [userStates, setUserStates] = useState([]);
  useEffect(() => {
    if (USER_STATE_MAP !== undefined) {
      setUserStates(Object.values(USER_STATE_MAP));
    }
  }, [USER_STATE_MAP]);
  const onlineUsers = userStates.filter(userState => userState.state === 'online');
  const offlineUsers = userStates.filter(userState => userState.state === 'offline');
  console.log(userStates);
  return (
    <div className="status">
      <ul>
        <h1>온라인</h1>
        {onlineUsers.map((userState, index) => (
          <div key={index}>
            <img src={userState.profile} alt=""/>
            <span>{userState.userName}</span>

          </div>
        ))}
        <h1>오프라인</h1>
        {offlineUsers.map((userState, index) => (
          <div key={index}>
            <img src={userState.profile} alt=""/>
            <span>{userState.userName}</span>

          </div>
        ))}
      </ul>
    </div>
  );
}

export default Status