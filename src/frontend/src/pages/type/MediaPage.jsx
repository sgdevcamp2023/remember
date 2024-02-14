import React from "react";
import "../../css/MediaPage.css";
import MyVideoBox from "../../components/MyVideoBox";
import MediaTitle from "../../components/MediaTitle";

const MediaPage = () => {
  return (
    <div className="page-container">
      <MediaTitle />
      <MyVideoBox />
    </div>
  );
};

export default MediaPage;
