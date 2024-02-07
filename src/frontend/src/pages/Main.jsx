import React, { useEffect } from "react";
import "../css/Main.css";
import { Outlet, useNavigate } from "react-router-dom";
import { guildList as mock_guild_list } from "../config/mock_data";
import AuthStore from "../store/AuthStore";
import CommunityStore from "../store/CommunityStore";

const Main = () => {
  const navigate = useNavigate();
  const { USER_ID, ACCESS_TOKEN } = AuthStore();
  const { setGuildList } = CommunityStore();

  useEffect(() => {
    // 추후 if문 조건 변경
    if (USER_ID && ACCESS_TOKEN) {
      // axios.get 길드 리스트
      const response = mock_guild_list.resultData;

      // set GuildList in global state
      setGuildList(response);
      navigate(`/channels/${response[0].guildId}`);
      return;
    }
    navigate("/login");

    return () => {};
  }, []);

  return (
    <div className="main">
      {/* <Guilds /> */}
      {/* {rooms.length > 0 ? (
        rooms.map((room, index) => (
          <Link to={`/main/room/${room.roomId}`} className="no-decoration">
            <div key={index}>
              <Room room={room} />
            </div>
          </Link>
        ))
      ) : (
        <></>
      )} */}
      <Outlet />
    </div>
  );
};

export default Main;
