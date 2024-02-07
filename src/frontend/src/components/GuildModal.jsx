import React, { useState } from "react";
import Modal from "react-modal";
import upload from "../assets/icons/upload.svg";
import AuthStore from "../store/AuthStore";
import { createNewGuild } from "../Request/communityRequest";

Modal.setAppElement("#root");
const GuildModal = (props) => {
  const [ServerImage, setServerImage] = useState(null);
  const [ServerName, setServerName] = useState("");

  const { USER_ID } = AuthStore();

  const handleImageChange = (e) => {
    const file = e.target.files[0];
    // 파일이 선택되면 상태를 업데이트하여 선택한 파일을 저장
    setServerImage(file);
  };

  const handleServerAdd = (event) => {
    event.preventDefault();
    let formData = new FormData();
    let data = {
      managerId: USER_ID,
      name: ServerName,
    };
    formData.append(
      "requestDto",
      new Blob([JSON.stringify(data)], { type: "application/json" })
    );
    formData.append("profile", ServerImage);

    createNewGuild(formData);
    props.closeModal();
    window.location.reload();
  };

  return (
    <Modal
      isOpen={props.isModalOpen}
      onRequestClose={props.closeModal}
      contentLabel="Server Add Modal"
      className="server-add-modal-content"
      overlayClassName="server-add-modal-overlay"
    >
      <div className="server-add-modal-header">
        <h2>서버 커스터마이징하기</h2>
        <h4>
          새로운 서버에 이름과 아이콘을 부여해 개성을 드러내 보세요. 나<br />
          중에 언제든 바꿀 수 있어요.
        </h4>
      </div>
      <div className="server-add-modal-body">
        <div className="file_img_container">
          <label htmlFor="fileInput" className="file_img_label">
            <img
              alt="프로필 사진"
              className="file_img"
              src={ServerImage ? URL.createObjectURL(ServerImage) : upload}
            />
          </label>
          <input
            id="fileInput"
            type="file"
            accept="image/*"
            onChange={handleImageChange}
            style={{ display: "none" }}
          />
        </div>

        <div>
          <label className="server-name-label">서버 이름</label>
          <input
            type="text"
            className="server-name-input"
            value={ServerName}
            onChange={(e) => setServerName(e.target.value)}
          />
        </div>
      </div>
      <br />
      <div className="server-add-modal-footer">
        <button onClick={handleServerAdd}>추가</button>
        <button onClick={props.closeModal}>취소</button>
      </div>
    </Modal>
  );
};

export default GuildModal;
