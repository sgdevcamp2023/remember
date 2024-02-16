import React, { useEffect, useState } from "react";
import "../css/Guilds.css";
import { guildList as mock_guild_list } from "../config/mock_data";
import AuthStore from "../store/AuthStore";
import CommunityStore from "../store/CommunityStore";
import GuildBtn from "./GuildBtn";
import DmRoomBtn from "./DmRoomBtn";
import GuildModal from "./GuildModal";
import GuildSeperator from "./GuildSeperator";

const Guild = () => {
  const { USER_ID } = AuthStore();
  const { GUILD_LIST, setGuildList } = CommunityStore();

  const [isModalOpen, setIsModalOpen] = useState(false);

  const appendServer =
    "https://storage.googleapis.com/remember-harmony/fe2f6651-10c4-444c-a336-3740dd4a5890";

  useEffect(() => {
    // axios를 통한 길드 리스트 요청
    const response = mock_guild_list.resultData.filter((element) => {
      return element.userId === USER_ID;
    });
    console.log(response);
    // response를 세팅
    setGuildList(response);
    return () => {};
  }, []);

  const openModal = () => {
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };

  return (
    <div className="guild-bar">
      <DmRoomBtn />
      <GuildSeperator />
      <div className="guild-container">
        {GUILD_LIST ? (
          GUILD_LIST.map((guild) => <GuildBtn props={guild} />)
        ) : (
          <></>
        )}
      </div>
      <GuildSeperator />
      <div onClick={openModal} className="guild-btn">
        <img alt={"기본 이미지"} className="profile_img" src={appendServer} />
      </div>
      <GuildModal isModalOpen={isModalOpen} closeModal={closeModal} />
    </div>
  );
};

export default Guild;
