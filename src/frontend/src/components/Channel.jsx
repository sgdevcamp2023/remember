import React, { useEffect, useState } from "react";
import "../css/Channels.css";
import {
  channelList as mock_channel_list,
  memberList as mock_member_list,
} from "../config/mock_data";
import CurrentStore from "../store/CurrentStore";
import CommunityStore from "../store/CommunityStore";
import { useNavigate } from "react-router-dom";
import VoiceChatUser from "./VoiceChatUser";

const Channel = () => {
  const navigate = useNavigate();
  const {
    CURRENT_VIEW_GUILD,
    CURRENT_VIEW_CHANNEL,
    CURRENT_VIEW_GUILD_NAME,
    setCurrentViewChannel,
    setCurrentViewChannelType,
  } = CurrentStore();
  const { CHANNEL_LIST, setChannelList } = CommunityStore();
  const [members, setMembers] = useState([]);

  useEffect(() => {
    // ****************** 길드 채널 최초 접속 시 ******************
    if (CURRENT_VIEW_GUILD) {
      // axios를 통한 채널 리스트, memberList 요청
      const channelResponse = mock_channel_list.resultData.filter((element) => {
        return element.guildId === CURRENT_VIEW_GUILD;
      });
      const memberResponse = mock_member_list.resultData;

      // response를 세팅
      setChannelList(channelResponse);
      setMembers(memberResponse);

      // tpye이 voice가 아닌 첫번째 채널로 이동
      const channel = channelResponse?.find(
        (element) => element.type !== "VOICE"
      );
      if (channel) {
        navigate(`/channels/${CURRENT_VIEW_GUILD}/${channel?.channelReadId}`);
      }
    }
    return () => {};
  }, [CURRENT_VIEW_GUILD]);

  const handleChangeChannel = (channelId, type) => {
    setCurrentViewChannel(channelId);
    setCurrentViewChannelType(type);
    navigate(`/channels/${CURRENT_VIEW_GUILD}/${channelId}`);
  };

  const handleVoiceChannel = () => {
    // 만약 voice 채널 조인중이라면 현재 View채널을 보이스 채널id로 바꾼다.
    console.log("voice");
  };

  return (
    <div className="channel-container">
      <div className="channel-title-container channel">
        <p className="channel-title">{CURRENT_VIEW_GUILD_NAME}</p>
      </div>

      <div className="channel-list-container">
        {CHANNEL_LIST ? (
          CHANNEL_LIST?.map((channel) => (
            <div key={channel.channelReadId}>
              <div
                onClick={
                  channel.type !== "VOICE"
                    ? () =>
                        handleChangeChannel(channel.channelReadId, channel.type)
                    : handleVoiceChannel
                }
                className={`channel-btn channel ${
                  CURRENT_VIEW_CHANNEL === channel.channelReadId
                    ? "channel-selected"
                    : ""
                }`}
              >
                <p>{channel.name}</p>
              </div>
              {members?.[channel.channelReadId] &&
                members[channel.channelReadId].map((member, index) => {
                  <VoiceChatUser key={index} props={member} />;
                })}
            </div>
          ))
        ) : (
          <></>
        )}
      </div>
    </div>
  );
};

export default Channel;
