import React, { useEffect, useState } from "react";
import "../css/Channels.css";
import { memberList as mock_member_list } from "../config/mock_data";
import MediaContainer from "./MediaContainer";
import CurrentStore from "../store/CurrentStore";
import CommunityStore from "../store/CommunityStore";
import { useNavigate } from "react-router-dom";
import ChannelButton from "./ChannelButton";
import VoiceChannelButton from "./VoiceChannelButton";
import useVoiceSocket from "../hooks/useVoiceSocket";
import AuthStore from "../store/AuthStore";
import { useMediaStream } from "../contexts/MediaStreamContext";
import SocketStore from "../store/SocketStore";
import {
  getChannelListRequest,
  getUserStateAndVoice,
} from "../Request/communityRequest";
import ChannelModal from "./ChannelModal";
import DmChannelButton from "./DmChannelButton";

const Channel = () => {
  const navigate = useNavigate();

  const { audioStream, setVideoStream, setPeerVideoStream } = useMediaStream();
  const [isModalOpen, setIsModalOpen] = useState(false);
  const {
    connectSocket,
    addEventListeners,
    validateUserInChannel,
    voice_socket,
  } = useVoiceSocket(process.env.REACT_APP_MEDIA_URL);
  const {
    CURRENT_VIEW_GUILD,
    CURRENT_VIEW_GUILD_NAME,
    setCurrentViewChannel,
    setCurrentViewChannelType,
  } = CurrentStore();
  const { setUserId, USER_ID } = AuthStore();
  const {
    CHANNEL_LIST,
    DM_ROOM_LIST,
    setChannelList,
    setUserStateMap,
    setVoiceUserStateMap,
  } = CommunityStore();

  const { VOICE_SOCKET } = SocketStore.getState();
  const { CURRENT_JOIN_CHANNEL } = CurrentStore.getState();

  let accessToken = AuthStore.getState().ACCESS_TOKEN;
  const appendServer =
    "https://storage.googleapis.com/remember-harmony/fe2f6651-10c4-444c-a336-3740dd4a5890";

  const [members, setMembers] = useState([]);
  const [testId, setTestId] = useState("");

  useEffect(() => {
    // ****************** 길드 채널 최초 접속 시 ******************
    if (CURRENT_VIEW_GUILD) {
      // axios를 통한 채널 리스트, memberList 요청

      const fetchChannel = async () => {
        const data = await getChannelListRequest(CURRENT_VIEW_GUILD, USER_ID);
        const channels = Object.values(data.data.resultData);
        setChannelList(channels);
        const channel = channels?.find((element) => element.type === "TEXT");
        if (channel) {
          navigate(`/channels/${CURRENT_VIEW_GUILD}/${channel?.channelReadId}`);
        }
      };

      fetchChannel();

      const memberResponse = mock_member_list.resultData;

      const fetchData = async () => {
        const data = await getUserStateAndVoice(CURRENT_VIEW_GUILD, USER_ID);
        setUserStateMap(data.data.resultData.guildStates);
        setVoiceUserStateMap(data.data.resultData.voiceChannelStates);
      };

      fetchData();

      // setUserStateMap(fetchMembers());

      // setMembers(memberResponse);
      // 첫 번째 채팅 채널로 이동
    }
    return () => {};
  }, [CURRENT_VIEW_GUILD]);

  const handleVoiceChannel = (guildId, channelId) => {
    if (channelId === CURRENT_JOIN_CHANNEL) {
      setCurrentViewChannel(channelId);
      setCurrentViewChannelType("VOICE");
      navigate(`/channels/${CURRENT_VIEW_GUILD}/${channelId}`);
      return;
    }

    if (!voice_socket || !voice_socket.connected) {
      connectSocket();
      addEventListeners();
    }
    validateUserInChannel(guildId, channelId);
  };

  const openModal = () => {
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };

  return (
    <div className="channel-container">
      <div>
        {Object.entries(audioStream).map(([id, { kind, stream }]) =>
          kind === "audio" ? (
            <audio
              key={id}
              ref={(ref) => ref && (ref.srcObject = stream)}
              autoPlay
            />
          ) : (
            <></>
          )
        )}
      </div>
      <div className="channel-title-container">
        <p className="channel-title">{CURRENT_VIEW_GUILD_NAME}</p>
      </div>
      {window.location.pathname.includes("@me") ? (
        DM_ROOM_LIST?.map((room) => (
          <DmChannelButton key={room.roomId} room={room} />
        ))
      ) : (
        <>
          <div className="channel-list-container">
            {CHANNEL_LIST ? (
              CHANNEL_LIST?.map((channel) =>
                channel.type !== "VOICE" ? (
                  <ChannelButton
                    key={channel.channelReadId}
                    channel={channel}
                  />
                ) : (
                  <VoiceChannelButton
                    key={channel.channelReadId}
                    channel={channel}
                    members={members}
                    onClick={handleVoiceChannel}
                  />
                )
              )
            ) : (
              <></>
            )}
            <div onClick={openModal} className="guild-btn">
              <img
                alt={"기본 이미지"}
                className="profile_img"
                src={appendServer}
              />
            </div>
          </div>
          <ChannelModal isModalOpen={isModalOpen} closeModal={closeModal} />
        </>
      )}
      {/*  */}

      {/*  */}
      <div>{VOICE_SOCKET ? <MediaContainer /> : <></>}</div>
    </div>
  );
};

export default Channel;
