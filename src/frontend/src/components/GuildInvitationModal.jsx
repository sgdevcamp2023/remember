import "../css/Guild.css"
import {useRef, useState} from "react";
import Modal from 'react-modal';

export default function GuildInvitationModal(props) {

  const reactAddr = "http://localhost:3000/invitation/";
  const inputRef = useRef(null);

  const handleCopyClick = () => {
    if (inputRef.current) {
      inputRef.current.select();
      navigator.clipboard.writeText(inputRef.current.value);
    }
  };

  return (
    <div>
      <Modal
        isOpen={props.isModalOpen}
        onRequestClose={props.closeModal}
        contentLabel="Server Add Modal"
        className="server-add-modal-content"
        overlayClassName="server-add-modal-overlay"
      >
        <div className="server-add-modal-header">
          <h2>초대하기</h2>
          <h4>초대링크 입니다</h4>
        </div>
        <div className="server-add-modal-body">
          <input ref={inputRef} type="text" value={reactAddr + props.invitation} readOnly/>
          <button onClick={handleCopyClick}>복사</button>
        </div>
      </Modal>
    </div>
  );
}
