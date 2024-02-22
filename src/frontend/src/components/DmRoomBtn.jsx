import React, { useState } from "react";
import { Link, useLocation } from "react-router-dom";
import CurrentStore from "../store/CurrentStore";

const DmRoomBtn = () => {
  const {
    removeCurrentViewGuild,
    removeCurrentViewChannel,
    removeCurrentViewChannelType,
  } = CurrentStore();

  const commonImageUrl =
    "https://storage.googleapis.com/remember-harmony/d68c3d6c-4683-4ddf-892b-8a73e3678145";
  return (
    <Link to={"/channels/@me"} className="dm-link">
      <div
        className={`guild-btn dm-room-btn `}
        onClick={() => {
          removeCurrentViewGuild();
          removeCurrentViewChannel();
          removeCurrentViewChannelType();
        }}
      >
        <img alt="기본 이미지" className="profile_img" src={commonImageUrl} />
      </div>
    </Link>
  );
};

export default DmRoomBtn;
