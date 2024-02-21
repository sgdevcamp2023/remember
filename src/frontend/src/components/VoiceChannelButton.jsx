import React, { useEffect, useState } from "react";
import VoiceChatUser from "./VoiceChatUser";
import CurrentStore from "../store/CurrentStore";
import CommunityStore from "../store/CommunityStore";

const VoiceChannelButton = ({ channel, onClick }) => {
  const { CURRENT_VIEW_GUILD, CURRENT_VIEW_CHANNEL } = CurrentStore();
  const { VOICE_USER_STATE_MAP } = CommunityStore();

  const handleVoiceChannel = (guildId, channelId) => {
    onClick(guildId, channelId);
  };

  return (
    <div>
      <div
        className={`channel-btn channel ${
          CURRENT_VIEW_CHANNEL === channel.channelReadId
            ? "channel-selected"
            : ""
        }`}
        onClick={() => {
          handleVoiceChannel(CURRENT_VIEW_GUILD, channel.channelReadId);
        }}
      >
        <p>{channel.name}</p>
      </div>
      <div>
        <VoiceChatUser />
      </div>
    </div>
  );
};

export default VoiceChannelButton;
