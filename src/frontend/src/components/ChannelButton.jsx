import React from "react";
import { useNavigate } from "react-router-dom";
import CurrentStore from "../store/CurrentStore";
import hashtag from "../assets/icons/hashtag.svg";
import voice from "../assets/icons/voice.svg";

const ChannelButton = ({ channel }) => {
  const navigate = useNavigate();
  const {
    CURRENT_VIEW_GUILD,
    CURRENT_VIEW_CHANNEL,
    setCurrentViewChannel,
    setCurrentViewChannelType,
  } = CurrentStore();

  const handleChangeChannel = (channelId, type) => {
    setCurrentViewChannel(channelId);
    setCurrentViewChannelType(type);
    navigate(`/channels/${CURRENT_VIEW_GUILD}/${channelId}`);
  };

  return (
    <div
      onClick={() => handleChangeChannel(channel.channelReadId, channel.type)}
      className={`channel-btn channel ${
        CURRENT_VIEW_CHANNEL === channel.channelReadId ? "channel-selected" : ""
      }`}
    >
      <img alt={"hashtag"} src={hashtag}/>
      <p>{channel.name}</p>
    </div>
  );
};

export default ChannelButton;
