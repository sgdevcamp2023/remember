import React from "react";
import VoiceChatUser from "./VoiceChatUser";
import CurrentStore from "../store/CurrentStore";

const VoiceChannelButton = ({ channel }) => {
  const { CURRENT_VIEW_CHANNEL } = CurrentStore();

  return (
    <div
      className={`channel-btn channel ${
        CURRENT_VIEW_CHANNEL === channel.channelReadId ? "channel-selected" : ""
      }`}
    >
      <p>{channel.name}</p>
    </div>
  );
};

export default VoiceChannelButton;
