import React, { useEffect, useState,useRef } from "react";
import "../css/Guilds.css";
import { guildList as mock_guild_list } from "../config/mock_data";
import AuthStore from "../store/AuthStore";
import CommunityStore from "../store/CommunityStore";
import CurrentStore from "../store/CurrentStore";
import useSocketStore from '../store/SocketStore';
import GuildBtn from "./GuildBtn";
import DmRoomBtn from "./DmRoomBtn";
import GuildModal from "./GuildModal";
import GuildSeperator from "./GuildSeperator";
import ChatStore from "../store/ChatStore";
import StatusStore from "../store/StatusStore";
import {getGuildListRequest} from "../Request/communityRequest";

const Guild = () => {
  const { USER_ID } = AuthStore();
  const { GUILD_LIST, setGuildList } = CommunityStore();
  const { CURRENT_VIEW_GUILD} = CurrentStore();
  const [isModalOpen, setIsModalOpen] = useState(false);
  const { setMessage } = ChatStore(); 
  const mainSocket = useSocketStore(state => state.MAIN_SOCKET); 
  const socketIdRef = useRef('');
  const { setStatus } = StatusStore(); 


  const appendServer =
    "https://storage.googleapis.com/remember-harmony/fe2f6651-10c4-444c-a336-3740dd4a5890";

  useEffect(() => {   
    if (CURRENT_VIEW_GUILD) {
      console.log("현재 보고 있는 길드 id", CURRENT_VIEW_GUILD);
  
      // 웹 소켓으로부터 메시지를 받았을 때 처리하는 함수
      const handleReceiveMessage = (data) => {    
        const parsedMessage = JSON.parse(data.body);
        console.log("서버로부터 받은 데이터", parsedMessage);
        if (parsedMessage.type === "CONNECT" || parsedMessage.type === "DISCONNECT") {
          setStatus(parsedMessage);
        } else if (parsedMessage.type === "send" || parsedMessage.type === "modify" || parsedMessage.type === "delete") {
          setMessage(parsedMessage);
        }
      };
  
      if (mainSocket !== null) {
        //처음 구독
        socketIdRef.current = mainSocket.subscribe(`/topic/guild/${CURRENT_VIEW_GUILD}`, handleReceiveMessage);
        console.log(socketIdRef.current)
      }
    }

    
    return () => {
      if (mainSocket !== null) {
        mainSocket.unsubscribe(socketIdRef.current.id);
        socketIdRef.current = '';
      }
    }
  }, [CURRENT_VIEW_GUILD]); 
  

  useEffect(() => {

    const fetchData = async () => {
      const data = await getGuildListRequest(USER_ID);
      setGuildList(Object.values(data.data.resultData));
    }

    fetchData();
    return () => {};
  }, []);

  const openModal = () => {
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };

  return (
    <div className="guild-bar">
      <DmRoomBtn />
      <GuildSeperator />
      <div className="guild-container">
        {GUILD_LIST ? (
          GUILD_LIST.map((guild) => <GuildBtn props={guild} />)
        ) : (
          <></>
        )}
      </div>
      <GuildSeperator />
      <div onClick={openModal} className="guild-btn">
        <img alt={"기본 이미지"} className="profile_img" src={appendServer} />
      </div>
      <GuildModal isModalOpen={isModalOpen} closeModal={closeModal} />
    </div>
  );
};

export default Guild;
