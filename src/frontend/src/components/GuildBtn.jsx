import React, {useState} from "react";
import CurrentStore from "../store/CurrentStore";
import GuildInvitationModal from "./GuildInvitationModal";
import {createInvitationCode} from "../Request/communityRequest";
import AuthStore from "../store/AuthStore";
import CommunityStore from "../store/CommunityStore";

// 길드 버튼을 클릭하면 현재 해당하는 길드로 zustand값 변경
const GuildBtn = ({props}) => {
  const {CURRENT_VIEW_GUILD, setCurrentViewGuild, setCurrentViewGuildName} = CurrentStore();
  const [isModalOpen, setIsModalOpen] = useState(false);
  const {USER_ID} = AuthStore();
  const [invitation, setInvitation] = useState("");
  const openModal = () => {
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };

  const fetchData = async (data) => {
    const result = await createInvitationCode(data);
    setInvitation(result.data.resultData)
  };

  return (
    <div
      onClick={() => {
        setCurrentViewGuild(props.guildId);
        setCurrentViewGuildName(props.name);
      }}
      onContextMenu={(e) => {
        e.preventDefault();
        const data = {
          "userId": USER_ID,
          "guildId": props.guildId
        }
        fetchData(data);
        openModal();
      }}
      className={`guild-btn ${
        CURRENT_VIEW_GUILD === props.guildId ? "guild-selected" : ""
      }`}
    >
      <img alt="프로필 사진" className="profile_img" src={props.profile}></img>
      {isModalOpen && <GuildInvitationModal isModalOpen={isModalOpen} closeModal={closeModal} invitation={invitation}/>}
    </div>
  );
};

export default GuildBtn;
