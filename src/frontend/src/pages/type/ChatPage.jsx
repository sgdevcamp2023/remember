import React, { useState, useEffect, useRef } from 'react';
import useSocketStore from '../../store/SocketStore';
import useAuthStore from '../../store/AuthStore'
import '../../css/ChatPage.css'; 
import axios from 'axios'; 
import { useLocation } from 'react-router-dom';

const ChatPage = () => {
  const USER_ID = useAuthStore(state => state.USER_ID);
  const location = useLocation();
  const [inputMessage, setInputMessage] = useState(''); 
  const [messages, setMessages] = useState([]); // 메시지 목록 상태 관리
  const [editingMessage, setEditingMessage] = useState({ messageId: null, message: '' }); 
  const mainSocket = useSocketStore(state => state.MAIN_SOCKET); 
  const socketIdRef = useRef('');

  const messageWindowRef = useRef(null); // 메시지 창의 DOM 요소에 접근하기 위한 useRef 훅 사용
  const [channelId, setChannelId] = useState(1); // 채널 ID 상태
  const [page, setPage] = useState(0); // 페이지 번호 상태
  const [loading, setLoading] = useState(false); // 로딩 상태
  const [hasMoreData, setHasMoreData] = useState(true); // 더 이상 데이터를 가져올 수 있는지 여부 상태

  useEffect(() => {
    // 페이지 로딩 시 메시지 불러오기
    fetchScrollMessages();
  }, [channelId, page]); 

  // 메시지 불러오기
  const fetchScrollMessages = async () => {
    if (hasMoreData) {
      try {
        setLoading(true); 
        const response = await axios.get(`http://localhost:7000/api/chat-service/community/messages/channel?channelId=${channelId}&page=${page}&size=10`);
        const newMessages = response.data.content.reverse();
        // 새로운 데이터가 없을 경우
        if (newMessages.length === 0) {
          setHasMoreData(false);
        } else {
          setMessages(prevMessages => [...newMessages, ...prevMessages]);
        }
      } catch (error) {
        console.error('Error fetching messages:', error);
      } finally {
        setLoading(false); 
      }
    }
  };

  // 스크롤 이벤트 핸들러
  const handleScroll = () => {
    if (messageWindowRef.current.scrollTop === 0 && !loading) {
      setPage(prevPage => prevPage + 1); // 다음 페이지로 이동
    }
  };

  useEffect(() => {   
    if (messageWindowRef.current) {
      messageWindowRef.current.scrollTop = messageWindowRef.current.scrollHeight;
    }

    // 웹 소켓으로부터 메시지를 받았을 때 처리하는 함수
    const handleReceiveMessage = (data) => {
      const parsedMessage = JSON.parse(data.body)
      console.log("서버에서 가져온 데이터", parsedMessage);
      if (parsedMessage.channelId === 1) {
        if (parsedMessage.type === "modify") {
          setMessages(prevMessages => {
            return [...prevMessages].map(prevMessage => {
              if (prevMessage.messageId === parsedMessage.messageId) {
                return { ...prevMessage, message: parsedMessage.message };
              } else {
                return prevMessage;
              }
            });
          });
        } else if (parsedMessage.type === "delete") {
          setMessages(prevMessages => {
            return prevMessages.filter(prevMessage => prevMessage.messageId !== parsedMessage.messageId);
          });
        } else {
          setMessages(prevMessages => {
            return [...prevMessages, parsedMessage]
          });
        }
      }
    };

    console.log("일하는 중");

    //처음 구독
    socketIdRef.current = mainSocket.subscribe('/topic/guild/1', handleReceiveMessage);
    console.log(socketIdRef.current)
    return () => {
      mainSocket.unsubscribe(socketIdRef.current.id);
      socketIdRef.current = ''
    }
  }, [location.pathname]); 

  // 메시지 전송
  const sendMessage = () => {
    if (!mainSocket) return;
    if (!inputMessage.trim()) return; 
    
    mainSocket.publish({
      destination: "/api/chat/guild/message",
      body: JSON.stringify({
        guildId: 1,
        channelId: 1,
        userId: USER_ID,
        parentId: 0,
        profileImage: "qwedfw",
        type: "send",
        senderName: "바나나",
        message: inputMessage
      }),
    });
    setInputMessage(''); // 메시지 입력 필드 초기화
  };

  const modifyMessage = (messageId, message) => {
    setEditingMessage({ messageId, message });
  };

  const cancelModify = () => {
    setEditingMessage({ messageId: null, message: '' });
  };

  // 메시지 수정
  const saveModifiedMessage = (messageId, message, guildId) => {
    if (!mainSocket) return; 
    if (!message.trim()) return; 

    const msg = {
      guildId: guildId,
      messageId: messageId,
      message: message,
      type: "modify"
    };
    const destination = "/api/chat/guild/modify";
    mainSocket.publish({destination: destination, body: JSON.stringify(msg)});
  };

  // 메시지 삭제
  const deleteMessage = (messageId, guildId) => {
    if (!mainSocket) return; 
    const msg = {
      guildId: guildId,
      messageId: messageId,
      type: "delete"
    };
    const destination = "/api/chat/guild/delete";
    mainSocket.publish({destination: destination, body: JSON.stringify(msg)});
  };

  const handleKeyPress = (e) => {
    if (e.key === 'Enter') {
      sendMessage();
    }
  };

  const formatTime = (timestamp) => {
    const date = new Date(timestamp); // timestamp를 Date 객체로 변환
    const hours = date.getHours();
    const minutes = date.getMinutes();
  
    // 시간과 분을 두 자리로 포맷팅
    const formattedHours = String(hours).padStart(2, '0');
    const formattedMinutes = String(minutes).padStart(2, '0');
  
    // 시간을 오전/오후 형식으로 변환
    const meridiem = hours >= 12 ? '오후' : '오전';
    const formattedTime = `${meridiem} ${formattedHours}:${formattedMinutes}`;
  
    return formattedTime;
  };

  return (
    <div className="chat-container">
      <div className="scroller-content" ref={messageWindowRef} onScroll={handleScroll}>
        {messages.length === 0 && <div>Loading...</div>}
        {messages.map((msg) => (

/* <div key={msg.messageId} className={msg.senderName === 'me' ? 'my-message' : 'other-message'}>
<div className="message-info">
  <img src={msg.profileImage} alt="Profile" className="profile-image" />
  <div className="user-info">
    <span className="username">{msg.senderName}</span>
    <span className="time">{formatTime(msg.createdAt)}</span>
  </div>
</div>
<div className="message-content">
  <p>{msg.message}</p>
</div> */

          <div key={msg.messageId} className={`message-container ${msg.senderName === 'me' ? 'my-message' : ''}`}>
            <img src={msg.profileImage} alt="Profile" className="profile-image" />
            <div className="message-info">
              <div className="user-info">
                <span className="username">{msg.senderName}</span>
                <span className="time">{formatTime(msg.createdAt)}</span>
              </div>
              <div className="message-content">
                {msg.message}
              </div>
            </div>

            {USER_ID === msg.userId && (
              <div>
                {editingMessage.messageId === msg.messageId ? (
                  <div>
                    <input 
                      type="text" 
                      value={editingMessage.message} 
                      onChange={(e) => setEditingMessage({ ...editingMessage, message: e.target.value })} 
                      placeholder="수정할 내용 입력..." 
                    />
                    <button onClick={() => saveModifiedMessage(msg.messageId, editingMessage.message)}>저장</button>
                    <button onClick={cancelModify}>취소</button>
                  </div>
                ) : (
                  <div>
                    <button onClick={() => modifyMessage(msg.messageId, msg.message)}>수정</button>
                    <button onClick={() => deleteMessage(msg.messageId)}>삭제</button>
                  </div>
                )}
              </div>
            )}
          </div>
        ))}
      </div>
      <div className="input-container">
        <input 
          type="text" 
          value={inputMessage} 
          onChange={e => setInputMessage(e.target.value)} 
          onKeyPress={handleKeyPress}
          placeholder="메시지 입력..." />
      </div>
    </div>
  );
};

export default ChatPage;
