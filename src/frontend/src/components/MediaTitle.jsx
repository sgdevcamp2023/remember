import React, { useEffect } from "react";
import "../css/MediaTitle.css";
import CommunityStore from "../store/CommunityStore";
import CurrentStore from "../store/CurrentStore";

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
    </div>
  );
};

export default MediaTitle;
