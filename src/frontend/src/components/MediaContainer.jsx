import React from "react";
import "../css/MediaContainer.css";
import { useNavigate } from "react-router-dom";
import CurrentStore from "../store/CurrentStore";
import CommunityStore from "../store/CommunityStore";
import MediaStore from "../store/MediaStore";

import useMediasoup from "../hooks/useMediasoup";
import { useMediaStream } from "../contexts/MediaStreamContext";
import SocketStore from "../store/SocketStore";

const MediaContainer = () => {
  const navigate = useNavigate();
  const { setCurrentViewChannelType } = CurrentStore();
  const { videoStream, setVideoStream } = useMediaStream();
  const { getLocalCameraStream, getLocalDisplayStream, closeProducer } =
    useMediasoup();
  const { removeVideoProducer, removeDisplayProducer } = MediaStore();
  const { removeVoiceSocket } = SocketStore();
  const { CURRENT_JOIN_GUILD, CURRENT_JOIN_CHANNEL } = CurrentStore.getState();

  const handleCemareStream = () => {
    if (videoStream && videoStream.camera) {
      const { VIDEO_PRODUCER } = MediaStore.getState();
      videoStream.camera.track.stop();
      closeProducer(VIDEO_PRODUCER);
      removeVideoProducer();
      setVideoStream({ ...videoStream, camera: null });
      return;
    }
    getLocalCameraStream();
    setCurrentViewChannelType("VOICE");
    navigate(`/channels/${CURRENT_JOIN_GUILD}/${CURRENT_JOIN_CHANNEL}`);
  };

  const handleDisplayStream = () => {
    if (videoStream && videoStream.display) {
      const { DISPLAY_PRODUCER } = MediaStore.getState();
      videoStream.display.track.stop();
      closeProducer(DISPLAY_PRODUCER);
      removeDisplayProducer();
      setVideoStream({ ...videoStream, display: null });
      return;
    }
    getLocalDisplayStream();
    setCurrentViewChannelType("VOICE");
    navigate(`/channels/${CURRENT_JOIN_GUILD}/${CURRENT_JOIN_CHANNEL}`);
  };

  const handleDisconnect = () => {
    const { VOICE_SOCKET } = SocketStore.getState();
    const { SEND_TRANSPORT, RECV_TRANSPORT } = MediaStore.getState();
    VOICE_SOCKET.close();
    removeVoiceSocket();
    SEND_TRANSPORT.close();
    RECV_TRANSPORT.close();
  };

  return (
    <div className="media-container">
      <div className="media-status">
        <div className="voice-connection">
          <p
            className={`connection-status ${
              CURRENT_JOIN_CHANNEL ? "connected" : ""
            }`}
          >
            {CURRENT_JOIN_CHANNEL ? "Connected" : "Disconnected"}
          </p>
          <p className="channel-name">
            {CommunityStore.getState().CHANNEL_LIST.map((element) =>
              element.channelReadId === CURRENT_JOIN_CHANNEL
                ? element.name
                : null
            )}
          </p>
        </div>
        <button className="disconnect-button" onClick={handleDisconnect}>
          Disconnect
        </button>
      </div>
      <div className="media-tools">
        <button
          className={`media_button camera ${
            videoStream.camera ? "media-selected" : ""
          }`}
          onClick={handleCemareStream}
        >
          Camera
        </button>
        <button
          className={`media_button video_call ${
            videoStream.display ? "media-selected" : ""
          }`}
          onClick={handleDisplayStream}
        >
          Streaming
        </button>
      </div>
    </div>
  );
};

export default MediaContainer;
