import "../css/Guild.css"
import {useRef, useState} from "react";
import Modal from 'react-modal';
import axios from "axios";

export default function Guild({guild}) {

  const apiAddr = "http://localhost:8000";
  const reactAddr = "http://localhost:3000";
  const userId = 1;
  const bearerToken = "Bearer " + localStorage.getItem("access_token");
  const inputRef = useRef(null);
  const [isHovered, setIsHovered] = useState(false);
  const [isContextMenuVisible, setIsContextMenuVisible] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [guildData, setGuildData] = useState("");

  const handleMouseEnter = () => {
    setIsHovered(true);
  };

  const handleMouseLeave = () => {
    setIsHovered(false);
  };

  const handleContextMenu = (e) => {
    e.preventDefault();
    setIsContextMenuVisible(true);
    const data = {
      "userId": userId,
      "guildId": guild.guildId
    }
    axios.post(apiAddr + "/api/community/invitation/guild", data,{
      headers: {
        "Authorization": bearerToken
      }
    })
      .then(response => {
        setGuildData(reactAddr + "/register/" + response.data.resultData);
      })
      .catch(error => {
        console.error("데이터를 받아오는 데 실패했습니다:", error);
      });
  };

  const openModal = () => {
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };

  const handleCopyClick = () => {
    if (inputRef.current) {
      inputRef.current.select();
      navigator.clipboard.writeText(inputRef.current.value);
    }
  };

  return (
    <div className={"sidebar-item"}
         onMouseEnter={handleMouseEnter}
         onMouseLeave={handleMouseLeave}
         onContextMenu={handleContextMenu}
    >
      <img
        alt="프로필 사진"
        className="profile_img"
        src={guild.profile}
        onContextMenu={openModal}
      />
      {isHovered && (
        <div className="tooltip">
          <p style={{color: '#fff'}}>{guild.name}</p>
        </div>
      )}
      {isContextMenuVisible && (
        <Modal
          isOpen={isModalOpen}
          onRequestClose={closeModal}
          contentLabel="Server Add Modal"
          className="server-add-modal-content"
          overlayClassName="server-add-modal-overlay"
        >
          <div className="server-add-modal-header">
            <h2>초대하기</h2>
            <h4>초대링크 입니다</h4>
          </div>
          <div className="server-add-modal-body">
            <input ref={inputRef} type="text" value={guildData} readOnly />
            <button onClick={handleCopyClick}>복사</button>
          </div>
        </Modal>
      )}
    </div>
  );
}
