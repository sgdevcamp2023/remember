import React from "react";
import "../css/MediaContainer.css";
import { useNavigate } from "react-router-dom";
import CurrentStore from "../store/CurrentStore";
import CommunityStore from "../store/CommunityStore";
import MediaStore from "../store/MediaStore";

import useMediasoup from "../hooks/useMediasoup";
import { useMediaStream } from "../contexts/MediaStreamContext";

const MediaContainer = () => {
  const navigate = useNavigate();
  const { setCurrentViewChannelType } = CurrentStore();
  const { videoStream, setVideoStream } = useMediaStream();
  const { getLocalCameraStream, getLocalDisplayStream } = useMediasoup();

  const { CURRENT_JOIN_GUILD, CURRENT_JOIN_CHANNEL } = CurrentStore.getState();

  const handleCemareStream = () => {
    if (videoStream && videoStream.camera) {
      videoStream.camera.track.stop();
      setVideoStream({ ...videoStream, camera: null });
      return;
    }
    getLocalCameraStream();
    setCurrentViewChannelType("VOICE");
    navigate(`/channels/${CURRENT_JOIN_GUILD}/${CURRENT_JOIN_CHANNEL}`);
  };

  const handleDisplayStream = () => {
    if (videoStream && videoStream.display) {
      videoStream.display.track.stop();
      setVideoStream({ ...videoStream, display: null });
      return;
    }
    getLocalDisplayStream();
    setCurrentViewChannelType("VOICE");
    navigate(`/channels/${CURRENT_JOIN_GUILD}/${CURRENT_JOIN_CHANNEL}`);
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
        <button className="disconnect-button">Disconnect</button>
      </div>
      <div className="media-tools">
        <button className="media_button camera" onClick={handleCemareStream}>
          Camera
        </button>
        <button
          className="media_button video_call"
          onClick={handleDisplayStream}
        >
          Streaming
        </button>
        <button className="media_button mic">Mic</button>
        <button className="media_button headset">Headset</button>
      </div>
    </div>
  );
};

export default MediaContainer;
