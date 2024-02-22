import React, { useEffect } from "react";
import "../css/DmChannelButton.css";
import { useNavigate } from "react-router-dom";

const DmChannelButton = ({ room }) => {
  const navigate = useNavigate();

  const handleDmChannelClick = () => {
    navigate(`/channels/@me/${room.roomId}`);
  };

  return (
    <div onClick={() => handleDmChannelClick()} className={`dm-channel`}>
      <img className="dm-img" src={room.profile} alt="" />
      <p className="dm-roomName">{room.name}</p>
    </div>
  );
};

export default DmChannelButton;
