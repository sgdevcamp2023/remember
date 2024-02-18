import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import AuthStore from "../store/AuthStore";
import * as StompJs from "@stomp/stompjs";
import SockJS from "sockjs-client"; // SockJS 가져오기
import SocketStore from "../store/SocketStore"; // websocketStore 가져오기

const RedirectPage = () => {
  const navigate = useNavigate();
  const { USER_ID, ACCESS_TOKEN } = AuthStore();
  const { setMainSocket } = SocketStore(); // websocketStore에서 setWebsocketClient 가져오기

  useEffect(() => {
    if (USER_ID && ACCESS_TOKEN) {
      const socket = new SockJS("http://34.22.109.45:7000/ws-stomp"); // SockJS 사용
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

      navigate("/channels/@me");
      return;
    }

    navigate("/login");

    return () => {};
  }, []);

  return <></>;
};

export default RedirectPage;
