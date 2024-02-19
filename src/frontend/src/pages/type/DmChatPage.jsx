import React, { useEffect } from "react";
import { useParams } from "react-router-dom";

const DmChatPage = () => {
  const { roomId } = useParams();
  useEffect(() => {
    console.log(roomId);
  }, []);

  return (
    <div className="main-section ">
      <p>Room ID : {roomId}</p>
    </div>
  );
};

export default DmChatPage;
