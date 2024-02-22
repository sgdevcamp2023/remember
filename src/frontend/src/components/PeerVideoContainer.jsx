import React, { useEffect } from "react";
import "../css/PeerVideoBox.css";
import useMediasoup from "../hooks/useMediasoup";
import { useMediaStream } from "../contexts/MediaStreamContext";
import useVoiceSocket from "../hooks/useVoiceSocket";
import CurrentStore from "../store/CurrentStore";
import SocketStore from "../store/SocketStore";

const PeerVideoContainer = () => {
  const { toggleVideo } = useMediasoup();
  const {
    connectSocket,
    addEventListeners,
    validateUserInChannel,
    voice_socket,
  } = useVoiceSocket(process.env.REACT_APP_MEDIA_URL);
  const { peerVideoStream } = useMediaStream();

  const handleVideoPause = (consumerId) => {
    const { VOICE_SOCKET } = SocketStore.getState();
    const { CURRENT_VIEW_GUILD, CURRENT_VIEW_CHANNEL } =
      CurrentStore.getState();

    if (!VOICE_SOCKET) {
      connectSocket();
      addEventListeners();
      validateUserInChannel(CURRENT_VIEW_GUILD, CURRENT_VIEW_CHANNEL);
    }
    if (VOICE_SOCKET) {
      toggleVideo(consumerId);
    }
  };

  return (
    <div className="peer-video-box">
      {Object.entries(peerVideoStream).map(
        ([id, { kind, stream, consumerId }]) =>
          kind === "video" ? (
            <div className="video-container">
              <video
                key={id}
                ref={(ref) => ref && (ref.srcObject = stream)}
                autoPlay
                className="video"
              />
              <button onClick={() => handleVideoPause(consumerId)}>
                재생/정지
              </button>
            </div>
          ) : (
            <></>
          )
      )}
    </div>
  );
};

export default PeerVideoContainer;
