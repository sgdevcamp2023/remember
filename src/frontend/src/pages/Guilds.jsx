import { Link } from "react-router-dom";
import { useEffect, useState } from "react";
import axios from "axios";
import Guild from "./Guild";
import "../css/Guilds.css";
import Modal from "react-modal";
import upload from "../assets/icons/upload.svg";
import CommunityStore from "../store/CommunityStore";

export default function Guilds() {
  const apiAddr = "https://0chord.store";
  const commonImageUrl =
    "https://storage.googleapis.com/remember-harmony/d68c3d6c-4683-4ddf-892b-8a73e3678145";
  // const userId = localStorage.getItem("user_id");
  const { channels, registerChannels, removeChannels } = CommunityStore();
  const { categories, registerCategories, removeCategories } = CommunityStore();
  const { rooms, registerRooms, removeRooms } = CommunityStore();
  const [Guilds, setGuilds] = useState([]);
  const [isModalOpen, setModalOpen] = useState(false);
  const [ServerName, setServerName] = useState("");
  const [ServerImage, setServerImage] = useState(null);
  const bearerToken = "Bearer " + localStorage.getItem("access_token");
  const appendServer =
    "https://storage.googleapis.com/remember-harmony/fe2f6651-10c4-444c-a336-3740dd4a5890";
  const userId = 1;

  const openModal = () => {
    setModalOpen(true);
  };

  const closeModal = () => {
    setServerImage(null);
    setServerName("");
    setModalOpen(false);
  };

  const handleServerAdd = (event) => {
    event.preventDefault();
    let formData = new FormData();
    let data = {
      managerId: userId,
      name: ServerName,
    };
    formData.append(
      "requestDto",
      new Blob([JSON.stringify(data)], { type: "application/json" })
    );
    formData.append("profile", ServerImage);
    axios
      .post(apiAddr + "/api/community/registration/guild", formData, {
        headers: {
          Authorization: bearerToken,
        },
      })
      .then((response) => {
        closeModal();
        window.location.reload();
      })
      .catch((exception) => {
        console.error(exception);
      });
  };

  const handleImageChange = (e) => {
    const file = e.target.files[0];
    // 파일이 선택되면 상태를 업데이트하여 선택한 파일을 저장
    setServerImage(file);
  };

  useEffect(() => {}, []);

  return (
    <div className="sidebar">
      <Link
        to={`/main`}
        onClick={() => {
          axios
            .get(apiAddr + `/api/community/check/room/${userId}`, {
              headers: {
                Authorization: bearerToken,
              },
            })
            .then((response) => {
              removeRooms();
              registerRooms(response.data.resultData.roomResponseDtos);
            })
            .catch((e) => {
              console.log(e);
            });
        }}
      >
        <div>
          <img alt="기본 이미지" className="profile_img" src={commonImageUrl} />
        </div>
      </Link>
      <hr />
      {Guilds.length > 0 ? (
        Guilds.map((guild, index) => (
          <Link
            to={`/guild/${guild.guildId}`}
            onClick={() => {
              removeChannels();
              removeCategories();
              axios
                .get(
                  apiAddr +
                    `/api/community/check/category/${guild.guildId}/${userId}`,
                  {
                    headers: {
                      Authorization: bearerToken,
                    },
                  }
                )
                .then((response) => {
                  registerCategories(response.data.resultData);
                })
                .catch((e) => {
                  console.log(e);
                });

              axios
                .get(
                  apiAddr +
                    `/api/community/check/guild/channel/${guild.guildId}/${userId}`,
                  {
                    headers: {
                      Authorization: bearerToken,
                    },
                  }
                )
                .then((response) => {
                  registerChannels(response.data.resultData);
                })
                .catch((e) => {
                  console.log(e);
                });
            }}
          >
            <div key={index}>
              <Guild guild={guild} />
            </div>
          </Link>
        ))
      ) : (
        <></>
      )}
      <hr />
      <div onClick={openModal}>
        <img alt={"기본 이미지"} className="profile_img" src={appendServer} />
      </div>
      <Modal
        isOpen={isModalOpen}
        onRequestClose={closeModal}
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
          <button onClick={closeModal}>취소</button>
        </div>
      </Modal>
    </div>
  );
}
