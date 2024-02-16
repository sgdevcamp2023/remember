import React, { useState, useEffect, useRef } from 'react';
import useSocketStore from '../../store/SocketStore';
import useAuthStore from '../../store/AuthStore'
import '../../css/ChatPage.css'; 
import axios from 'axios'; 
import { useLocation } from 'react-router-dom';
import ChatStore from '../../store/ChatStore';
import CurrentStore from "../../store/CurrentStore";

const ChatPage = () => {
  const USER_ID = useAuthStore(state => state.USER_ID);
  const [inputMessage, setInputMessage] = useState(''); 
  const [messages, setMessages] = useState([]); // 메시지 목록 상태 관리
  const [editingMessage, setEditingMessage] = useState({ messageId: null, message: '' }); 
  const mainSocket = useSocketStore(state => state.MAIN_SOCKET); 

  const socketIdRef = useRef('');
  const location = useLocation();

  const chatMessage = ChatStore(state => state.MESSAGE);
  const { CURRENT_VIEW_GUILD, CURRENT_VIEW_CHANNEL} = CurrentStore();

  const messageWindowRef = useRef(null); // 메시지 창의 DOM 요소에 접근하기 위한 useRef 훅 사용
  const [page, setPage] = useState(0); // 페이지 번호 상태
  const [loading, setLoading] = useState(false); // 로딩 상태
  const [hasMoreData, setHasMoreData] = useState(true); // 더 이상 데이터를 가져올 수 있는지 여부 상태

  useEffect(() => {
    // 페이지 로딩 시 메시지 불러오기
    console.log("CURRENT_VIEW_CHANNEL", CURRENT_VIEW_CHANNEL);
    fetchScrollMessages();
  }, [page]); 

  // 메시지 불러오기
  const fetchScrollMessages = async () => {
    if (hasMoreData && CURRENT_VIEW_CHANNEL) {
      try {
        setLoading(true); 
        const response = await axios.get(`http://localhost:7000/api/chat-service/community/messages/channel?channelId=${CURRENT_VIEW_CHANNEL}&page=${page}&size=10`);
        const newMessages = response.data.content.reverse();
        console.log("메시지 길이", newMessages.length);
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

    console.log("메시지 총 길이", messages.length);
    // 웹 소켓으로부터 메시지를 받았을 때 처리하는 함수
    if (chatMessage) {
      if (chatMessage.channelId === CURRENT_VIEW_CHANNEL) {
        if (chatMessage.type === "modify") {
          setMessages(prevMessages => {
            return [...prevMessages].map(prevMessage => {
              if (prevMessage.messageId === chatMessage.messageId) {
                return { ...prevMessage, message: chatMessage.message };
              } else {
                return prevMessage;
              }
            });
          });
        } else if (chatMessage.type === "delete") {
          setMessages(prevMessages => {
            return prevMessages.filter(prevMessage => prevMessage.messageId !== chatMessage.messageId);
          });
        } else {
          setMessages(prevMessages => {
            return [...prevMessages, chatMessage]
          });
        }
      }
    }

  }, [chatMessage]); 

  // 메시지 전송
  const sendMessage = () => {
    if (!mainSocket) return;
    if (!inputMessage.trim()) return; 
    
    mainSocket.publish({
      destination: "/api/chat/guild/message",
      body: JSON.stringify({
        guildId: CURRENT_VIEW_GUILD,
        channelId: CURRENT_VIEW_CHANNEL,
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
    const date = new Date(timestamp); // 문자열을 Date 객체로 파싱

    // 날짜를 YYYY.MM.DD. 오후 HH:MM 형식으로 포맷팅
    const formattedDate = `${date.getFullYear()}.${(date.getMonth() + 1).toString().padStart(2, '0')}.${date.getDate().toString().padStart(2, '0')}. `;
    const formattedTime = `${(date.getHours() % 12 || 12)}:${date.getMinutes().toString().padStart(2, '0')}`;
    const meridiem = date.getHours() >= 12 ? '오후' : '오전';
    
    return `${formattedDate}${meridiem} ${formattedTime}`;
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
