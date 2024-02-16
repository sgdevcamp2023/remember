import React from "react";
import "../css/MediaInteract.css";
import CommunityStore from "../store/CommunityStore";
import CurrentStore from "../store/CurrentStore";
import SocketStore from "../store/SocketStore";

const MediaTitle = () => {
  const { CHANNEL_LIST } = CommunityStore();
  const { CURRENT_JOIN_CHANNEL } = CurrentStore();

  return (
    <div>
      <span className="media-title">
        {CHANNEL_LIST.map((element) =>
          element.channelReadId === CURRENT_JOIN_CHANNEL ? element.name : null
        )}
      </span>
      {SocketStore.getState().VOICE_SOCKET ? (
        <button className="media-disconnect">
          <span>Disconnect</span>
        </button>
      ) : (
        <></>
      )}
    </div>
  );
};

export default MediaTitle;
