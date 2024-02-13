import React, { useEffect, useState } from "react";
import "../css/MainPage.css";

import { Outlet, useNavigate } from "react-router-dom";
import CurrentStore from "../store/CurrentStore";
import ChatPage from "./type/ChatPage";
import MediaPage from "./type/MediaPage";
import ForumPage from "./type/ForumPage";

const MainPage = () => {
  const [selectedComponent, setSelectedComponent] = useState(null);
  const { CURRENT_VIEW_CHANNEL_TYPE, CURRENT_VIEW_CHANNEL } = CurrentStore();

  useEffect(() => {
    switch (CURRENT_VIEW_CHANNEL_TYPE) {
      case "TEXT":
        setSelectedComponent(<ChatPage />);
        break;
      case "VOICE":
        setSelectedComponent(<MediaPage />);
        break;
      case "FORUM":
        setSelectedComponent(<ForumPage />);
        break;
      default:
        setSelectedComponent(<ChatPage />);
        break;
    }

    return () => {};
  }, [CURRENT_VIEW_CHANNEL_TYPE]);

  return <div className="main-section">{selectedComponent}</div>;
};

export default MainPage;
