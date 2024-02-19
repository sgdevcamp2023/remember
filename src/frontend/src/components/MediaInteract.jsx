import React from "react";
import "../css/MediaInteract.css";
import CommunityStore from "../store/CommunityStore";
import CurrentStore from "../store/CurrentStore";
import SocketStore from "../store/SocketStore";
import MediaStore from "../store/MediaStore";

const MediaInteract = () => {
  const { CHANNEL_LIST } = CommunityStore();
  const { CURRENT_JOIN_CHANNEL } = CurrentStore();
  const { removeVoiceSocket } = SocketStore();

  const handleDisconnect = () => {
    const { VOICE_SOCKET } = SocketStore.getState();
    const { SEND_TRANSPORT, RECV_TRANSPORT } = MediaStore.getState();
    VOICE_SOCKET.close();
    removeVoiceSocket();
    SEND_TRANSPORT.close();
    RECV_TRANSPORT.close();
  };

  return (
    <div>
      <span className="media-title">
        {CHANNEL_LIST.map((element) =>
          element.channelReadId === CURRENT_JOIN_CHANNEL ? element.name : null
        )}
      </span>
      {SocketStore.getState().VOICE_SOCKET ? (
        <button className="media-disconnect" onClick={handleDisconnect}>
          <span>Disconnect</span>
        </button>
      ) : (
        <></>
      )}
    </div>
  );
};

export default MediaInteract;
