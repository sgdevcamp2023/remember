import React, { useState, useEffect, useRef } from 'react';
import useSocketStore from '../../store/SocketStore';
import useAuthStore from '../../store/AuthStore'
import '../../css/ChatPage.css'; 
import axios from 'axios'; 
import ChatStore from '../../store/ChatStore';
import CurrentStore from "../../store/CurrentStore";

const ChatPage = () => {
  const MAX_MESSAGE_LENGTH = 500; // 최대 메시지 길이
  const MAX_FILE_COUNT = 5; // 최대 파일 개수 제한
  const MAX_FILE_SIZE = 10 * 1024 * 1024; // 최대 파일 크기 제한 (10MB)
  
  const [showMessage, setShowMessage] = useState(false); // 입력 메시지 제한 상태

  const { USER_ID, USER_NAME, USER_PROFILE } = useAuthStore();
  // useAuthStore(state => state.USER_ID);
  const [inputMessage, setInputMessage] = useState(''); // 입력 메시지 상태
  const [messages, setMessages] = useState([]); // 메시지 목록 상태 관리
  const [editingMessage, setEditingMessage] = useState({ messageId: null, message: '' }); 
  const mainSocket = useSocketStore(state => state.MAIN_SOCKET); 

  let chatMessage = ChatStore(state => state.MESSAGE);
  const { removeMessage } = ChatStore(); 
  const { CURRENT_VIEW_GUILD, CURRENT_VIEW_CHANNEL} = CurrentStore();

  const messageWindowRef = useRef(null); // 메시지 창의 DOM 요소에 접근하기 위한 useRef 훅 사용
  const [page, setPage] = useState(0); // 페이지 번호 상태
  const [loading, setLoading] = useState(false); // 로딩 상태
  const [hasMoreData, setHasMoreData] = useState(true); // 더 이상 데이터를 가져올 수 있는지 여부 상태

  const [hoveredMessageId, setHoveredMessageId] = useState(null);

  const [fileInputVisible, setFileInputVisible] = useState(false);
  const [selectedFiles, setSelectedFiles] = useState([]);

  let [newMessages, setNewMessages] = useState([]);
                       
  useEffect(() => {
    // 채널이 바뀔때 초기화 해주는 작업
    console.log("CURRENT_VIEW_CHANNEL", CURRENT_VIEW_CHANNEL);
    setPage(0);
    setMessages([]);
    setHasMoreData(true);
  }, [CURRENT_VIEW_CHANNEL]); 

  useEffect(() => {
    fetchScrollMessages();
  }, [CURRENT_VIEW_CHANNEL]);

  useEffect(() => {
    // 페이지 로딩 시 메시지 불러오기
    console.log("CURRENT_VIEW_CHANNEL", CURRENT_VIEW_CHANNEL);
    if (page > 0) {
      fetchScrollMessages();
    }
  }, [page]); 

  // 메시지 불러오기
  const fetchScrollMessages = async () => {
    if (hasMoreData && CURRENT_VIEW_CHANNEL) {
      try {
        setLoading(true); 
        const response = await axios.get(`https://0chord.store/api/chat-service/community/messages/channel?channelId=${CURRENT_VIEW_CHANNEL}&page=${page}&size=20`);
        newMessages = response.data.content.reverse();
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
        setNewMessages([]);
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
  }, []); 

  useEffect(() => {   
    // 웹 소켓으로부터 메시지를 받았을 때 처리하는 함수
    console.log("서버로부터 받은 데이터 체크", chatMessage);
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

    removeMessage(chatMessage); 
  }, [chatMessage]); 

  // 메시지 전송
  const sendMessage = () => {
    if (!mainSocket) return;
    if (!inputMessage.trim()) return; 

    if (inputMessage.length > MAX_MESSAGE_LENGTH) {
      setShowMessage(true); // 메시지 표시
      return;
    } else {
      setShowMessage(false); // 메시지 감춤
    }
    
    mainSocket.publish({
      destination: "/api/chat/guild/message",
      body: JSON.stringify({
        guildId: CURRENT_VIEW_GUILD,
        channelId: CURRENT_VIEW_CHANNEL,
        userId: USER_ID,
        parentId: 0,
        profileImage: USER_PROFILE,
        type: "send",
        senderName: USER_NAME,
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

    if (message.length > MAX_MESSAGE_LENGTH) {
      setShowMessage(true); // 메시지 표시
      return;
    } else {
      setShowMessage(false); // 메시지 감춤
    }

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

    const confirmed = window.confirm("정말로 삭제하시겠습니까?");
    if (!confirmed) return;

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

  /* 파일 업로드 처리 */
  const toggleFileInput = () => {
    setFileInputVisible(!fileInputVisible);
  };

  const handleFileInputChange = (e) => {
    const files = Array.from(e.target.files);
    if (files.length + selectedFiles.length > MAX_FILE_COUNT) {
      alert(`최대 ${MAX_FILE_COUNT}개의 파일까지만 업로드할 수 있습니다.`);
      return;
    }

    const totalSize = files.reduce((acc, file) => acc + file.size, 0);
    if (totalSize > MAX_FILE_SIZE) {
      alert(`파일 크기는 최대 ${MAX_FILE_SIZE / (1024 * 1024)}MB까지만 가능합니다.`);
      return;
    }

    setSelectedFiles([...selectedFiles, ...files]);
  };

  const handleFileUpload = async () => {
    const formData = new FormData();
    const msg = {
      guildId: CURRENT_VIEW_GUILD,
      channelId: CURRENT_VIEW_CHANNEL,
      userId: USER_ID,
      parentId: 0,
      profileImage: "qwedfw",
      type: "send",
      senderName: "바나나",
      message: inputMessage
    };

    const jsonMsg = JSON.stringify(msg);
    const communityMsg = new Blob([jsonMsg], { type: "application/json" });
    formData.append("communityMessageRequest", communityMsg);

    // 모든 선택된 파일을 formData에 추가
    for (let i = 0; i < selectedFiles.length; i++) {
      formData.append('files', selectedFiles[i]);
    }
  
    try {
      const response = await axios.post('https://0chord.store/api/chat-service/community/message/file', formData, {
        headers: {
          'content-type' : 'multipart/form-data',
        }
      });
      console.log('파일 업로드 성공:', response.data);
    } catch (error) {
      console.error('파일 업로드 실패:', error);
    }

    setSelectedFiles([]); // 선택된 파일 초기화
  };

  const getFileExtension = (filename) => {
    return filename.split('.').pop().toLowerCase();
  };

  const formatTime = (timestamp) => {
    const date = new Date(timestamp); 
    date.setHours(date.getHours() + 9);
      
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
          <div 
            key={msg.messageId} 
            className={`message-container ${msg.senderName === 'me' ? 'my-message' : ''}`}
            onMouseEnter={() => setHoveredMessageId(msg.messageId)}
            onMouseLeave={() => setHoveredMessageId(null)}
          >
            <img src={msg.profileImage} alt="Profile" className="profile-image" />
            <div className="message-info">
              <div className="user-info">
                <span className="username">{msg.senderName}</span>
                <span className="time">{formatTime(msg.createdAt)}</span>
              </div>
              <div className="message-content">
                {msg.message}
                {msg.files && msg.files.map((file, index) => (
                  <div key={index} className="attached-image-container">
                    {getFileExtension(file) === 'jpg' || getFileExtension(file) === 'jpeg' || getFileExtension(file) === 'png' || getFileExtension(file) === 'gif' ? (
                      <img src={file} alt="Attached Image" className="attached-image" />
                    ) : (
                      <a href={file} download>{file}</a>
                    )}
                  </div>
                ))}
              </div>
            </div>

            {USER_ID === msg.userId && hoveredMessageId === msg.messageId && (
              <div className="edit-buttons">
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
        {fileInputVisible && (
          <div>
            <input type="file" onChange={handleFileInputChange} multiple />
            <button onClick={handleFileUpload}>파일 업로드</button>
          </div>
        )}
        <input 
          type="text" 
          value={inputMessage} 
          onChange={e => setInputMessage(e.target.value)} 
          onKeyPress={handleKeyPress}
          placeholder="메시지 입력..." 
        />
        <div className="plus-button">
          <button onClick={toggleFileInput}>+</button>
        </div>
      </div>
      {showMessage && (
        <div className="message">500자를 넘을 수 없습니다.</div>
      )}
    </div>
  );
};

export default ChatPage;
