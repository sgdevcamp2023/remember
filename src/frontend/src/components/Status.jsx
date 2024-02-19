import React, { useEffect, useState } from "react";
import "../css/Status.css";
import CommunityStore from "../store/CommunityStore";
import StatusStore from "../store/StatusStore";

const Status = () => {
  const { USER_STATE_MAP, setUserStateMap } = CommunityStore();
  const [userStates, setUserStates] = useState([]);

  const status = StatusStore((state) => state.STATUS);

  useEffect(() => {
    console.log("status에서 찍은 데이터", status);
    if (status) {
      const findUser = USER_STATE_MAP[status.userId];
      if (status.state === "online") {
        findUser.state = "online";
        USER_STATE_MAP[status.userId] = findUser;
        setUserStateMap(USER_STATE_MAP);
      } else {
        findUser.state = "offline";
        USER_STATE_MAP[status.userId] = findUser;
        setUserStateMap(USER_STATE_MAP);
      }
    }
  }, [status]);

  useEffect(() => {
    if (USER_STATE_MAP !== undefined) {
      console.log(USER_STATE_MAP);
      setUserStates(Object.values(USER_STATE_MAP));
    }
  }, [USER_STATE_MAP]);
  const onlineUsers = userStates.filter(
    (userState) => userState.state === "online"
  );
  const offlineUsers = userStates.filter(
    (userState) => userState.state === "offline"
  );
  return (
    <div className="status">
      <ul>
        <p className="status-state">온라인</p>
        {onlineUsers.map((userState, index) => (
          <div className="status-container" key={index}>
            <img className="status-img" src={userState.profile} alt="" />
            <span className="status-name">{userState.userName}</span>
          </div>
        ))}
        <p className="status-state">오프라인</p>
        {offlineUsers.map((userState, index) => (
          <div className="status-container" key={index}>
            <img className="status-img" src={userState.profile} alt="" />
            <span className="status-name">{userState.userName}</span>
          </div>
        ))}
      </ul>
    </div>
  );
};

export default Status;
