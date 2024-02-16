import React, { useEffect, useState } from "react";
import CurrentStore from "../store/CurrentStore";

// 길드 버튼을 클릭하면 현재 해당하는 길드로 zustand값 변경
const GuildBtn = ({ props }) => {
  const { CURRENT_VIEW_GUILD, setCurrentViewGuild, setCurrentViewGuildName } = CurrentStore();
  
  return (
    <div
      onClick={() => {
        setCurrentViewGuild(props.guildId);
        setCurrentViewGuildName(props.name);
      }}
      className={`guild-btn ${
        CURRENT_VIEW_GUILD === props.guildId ? "guild-selected" : ""
      }`}
    >
      <img alt="프로필 사진" className="profile_img" src={props.profile}></img>
    </div>
  );
};

export default GuildBtn;
