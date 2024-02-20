import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import AuthStore from "../store/AuthStore";
import * as StompJs from "@stomp/stompjs";
import SockJS from 'sockjs-client'; // SockJS 가져오기
import SocketStore from '../store/SocketStore'; // websocketStore 가져오기
import { GetUserInfoRequest } from "../Request/userRequest";

const RedirectPage = () => {
  const navigate = useNavigate();
  const { USER_ID, ACCESS_TOKEN, setUserName, setUserProfile } = AuthStore();
  const { setMainSocket } = SocketStore(); // websocketStore에서 setWebsocketClient 가져오기

  useEffect(() => {
    if (USER_ID && ACCESS_TOKEN) {
      // 서버한테 주소 물어보기 추가해야됨.
      // "http://34.22.109.45:4000/api/chat/select/chat-server"
      // const socket = new SockJS("http://34.22.109.45:7000/ws-stomp"); // SockJS 사용
      const socket = new SockJS("https://0chord.store/ws-stomp"); // SockJS 사용
      const clientSocket = new StompJs.Client({
        webSocketFactory: () => socket,
        connectHeaders: {
          userId: USER_ID,
        },
        reconnectDelay: 5000, // 자동 재 연결
        heartbeatIncoming: 4000,
        heartbeatOutgoing: 4000,
      });

      clientSocket.activate(); // 클라이언트 활성화
      // 웹소켓 클라이언트를 전역 상태로 설정
      setMainSocket(clientSocket);

      // GetUserInfoRequest().then((response) => {
      //   if (response.status === 200) {
      //     setUserName(response.data.name);
      //     setUserProfile(response.data.profile);
      //   }
      // }).catch((error) => {
      //   console.error("데이터를 받아오는 데 실패했습니다:", error);
      // });

      navigate("/channels/@me");
      return;
    }

    navigate("/login");

    return () => { };
  }, []);

  return <></>;
};

export default RedirectPage;
