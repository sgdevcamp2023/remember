import React from "react";
import "../../css/MediaPage.css";
import MyVideoBox from "../../components/MyVideoBox";
import MediaInteract from "../../components/MediaInteract";
import PeerVideoContainer from "../../components/PeerVideoContainer";

const MediaPage = () => {
  return (
    <div className="page-container">
      <MediaInteract />
      <MyVideoBox />
      <PeerVideoContainer />
    </div>
  );
};

export default MediaPage;
