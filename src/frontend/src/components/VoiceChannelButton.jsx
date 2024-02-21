import React from "react";
import VoiceChatUser from "./VoiceChatUser";
import CurrentStore from "../store/CurrentStore";
import voice from "../assets/icons/voice.svg";

const VoiceChannelButton = ({ channel, members, onClick }) => {
  const { CURRENT_VIEW_GUILD, CURRENT_VIEW_CHANNEL } = CurrentStore();

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
        <img alt={"voice"} src={voice} />
        <p>{channel.name}</p>
      </div>
      <div>
        {members[channel.channelReadId]?.map((member, index) => (
          <div>
            <VoiceChatUser key={index} props={member} />
          </div>
        ))}
      </div>
    </div>
  );
};

export default VoiceChannelButton;
