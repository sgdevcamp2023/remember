import {useState} from "react";
import axios from "axios";
import {Outlet, useNavigate, useParams} from "react-router-dom";
import "../css/Channels.css"
import forum from "../assets/icons/forum.svg"
import hashtag from "../assets/icons/hashtag.svg"
import voice from "../assets/icons/voice.svg"
import Modal from "react-modal";
import ChannelsStore from "../store/ChannelsStore";
import CategoriesStore from "../store/CategoriesStore";

export default function Channels() {
  const apiAddr = "http://10.99.29.133:4000"


  const userId = 1;
  const {guildId} = useParams();
  const navigator = useNavigate();
  const [Category, setCategory] = useState("");
  const [Channel, setChannel] = useState("");
  const {channels, registerChannels, removeChannels} = ChannelsStore();
  const {categories, registerCategories, removeCategories} = CategoriesStore();
  const [collapsedCategories, setCollapsedCategories] = useState([]);
  const bearerToken = "Bearer " + localStorage.getItem("access_token");
  const [isCategoryModalOpen, setIsCategoryModalOpen] = useState(false);
  const [isChannelModalOpen, setIsChannelModalOpen] = useState(false);
  const [channelType, setChannelType] = useState("TEXT");
  const [CategoryNumber, setCategoryNumber] = useState(0);

  const handleChannelClick = (channel) => {
    if (channel.type === 'FORUM') {
      const forumPath = `/guild/${guildId}/channel/forum/${channel.channelReadId}`;
      navigator(forumPath);
    }
    if (channel.type === 'VOICE') {
      const forumPath = `/guild/${guildId}/channel/voice/${channel.channelReadId}`;
      navigator(forumPath);
    }
    if (channel.type === 'TEXT') {
      const forumPath = `/guild/${guildId}/channel/text/${channel.channelReadId}`;
      navigator(forumPath);
    }

    // 여기에 다른 channel.type에 대한 처리를 추가
  };
  const channelNameHandler = (e) => {
    setChannel(e.currentTarget.value);
  }
  const handleChannelTypeChange = (e) => {
    setChannelType(e.target.value);
  };
  const openCategoryModal = () => {
    setIsCategoryModalOpen(true);
  };

  const closeCategoryModal = () => {
    setIsCategoryModalOpen(false);
  };

  const openChannelModal = () => {
    setIsChannelModalOpen(true);
  };

  const closeChannelModal = () => {
    setIsChannelModalOpen(false);
  };

  const categoryHandler = (event) => {
    setCategory(event.currentTarget.value);
  }

  function Span({space = 5}) {
    return (
      <span style={{paddingRight: space}}></span>
    )
  }

  const toggleCategory = (categoryId) => {
    setCollapsedCategories(prevState => {
      if (prevState.includes(categoryId)) {
        return prevState.filter(id => id !== categoryId);
      } else {
        return [...prevState, categoryId];
      }
    });
  };

  const handleContextMenu = (e) => {
    e.preventDefault();
    if (e.target.tagName === "H2" && e.button === 2) {
      openChannelModal();
    } else {
      openCategoryModal();
    }
  };

  const handleCategoryId = (e, categoryId) => {
    e.preventDefault();

    setCategoryNumber(categoryId);
  };


  const registerCategory = (e) => {
    e.preventDefault();
    const data = {
      "name": Category,
      "userId": userId,
      "guildId": guildId
    }
    axios.post(apiAddr + "/api/community/registration/category", data, {
      headers: {
        "Authorization": bearerToken
      }
    })
      .then(response => {
        window.location.reload();
      })
      .catch(error => {
        console.error("데이터를 받아오는 데 실패했습니다:", error);
      });
  };

  const registerChannel = (e) => {
    e.preventDefault();
    const data = {
      "guildId": guildId,
      "name": Channel,
      "userId": userId,
      "categoryId": CategoryNumber,
      "type": channelType
    }
    axios.post(apiAddr + "/api/community/registration/category/channel", data, {
      headers: {
        "Authorization": bearerToken
      }
    })
      .then(response => {
        window.location.reload();
      })
      .catch(error => {
        console.error("데이터를 받아오는 데 실패했습니다:", error);
      });
  };
  return (
    <div className="channel" onContextMenu={(e) => handleContextMenu(e)}>
      {categories.map(category => (
        <div key={category.categoryReadId}>
          <h2 onClick={() => toggleCategory(category.categoryReadId)}
              onContextMenu={(e) => handleCategoryId(e, category.categoryReadId)}
          >
            {collapsedCategories.includes(category.categoryReadId) ? '>' : 'v'} {category.name}
          </h2>
          {!collapsedCategories.includes(category.categoryReadId) && (
            channels
              .filter(channel => channel.categoryId === category.categoryReadId)
              .map(channel => (
                <div key={channel.channelReadId} onClick={() => handleChannelClick(channel)}>
                  {channel.type === "TEXT" && (
                    <p>
                      <img
                        alt={"hashtag"}
                        src={hashtag}
                      />
                      <Span/>
                      {channel.name}
                    </p>
                  )}
                  {channel.type === "FORUM" && (
                    <p>
                      <img
                        alt={"forum"}
                        src={forum}
                      />
                      <Span/>
                      {channel.name}
                    </p>
                  )}
                  {channel.type === "VOICE" && (
                    <p>
                      <img
                        alt={"voice"}
                        src={voice}
                      />
                      <Span/>
                      {channel.name}
                    </p>
                  )}
                </div>
              ))
          )}
        </div>
      ))}

      <Modal
        isOpen={isCategoryModalOpen}
        onRequestClose={closeCategoryModal}
        contentLabel="Server Add Modal"
        className="server-add-modal-content"
        overlayClassName="server-add-modal-overlay"
      >
        <div className="server-add-modal-header">
          <h2>카테고리 만들기</h2>
        </div>
        <div className="server-add-modal-body">
          <label htmlFor="categoryName" className="category-input-label">
            카테고리 이름
          </label>
          <input
            type="text"
            id="categoryName"
            placeholder="카테고리 이름을 입력하세요"
            onChange={categoryHandler}
          />
          <button onClick={registerCategory}>등록하기</button>
        </div>
      </Modal>

      <Modal
        isOpen={isChannelModalOpen}
        onRequestClose={closeChannelModal}
        contentLabel="Server Add Modal"
        className="server-add-modal-content"
        overlayClassName="server-add-modal-overlay"
      >
        <div className="server-add-modal-header">
          <h2>채널 만들기</h2>
        </div>
        <div className="radio-group">
          <div className="radio-option">
            <div className="radio-box">
              <input
                type="radio"
                name="channelType"
                value="VOICE"
                onChange={handleChannelTypeChange}
              />
              <label>VOICE</label>
            </div>
          </div>
          <div className="radio-option">
            <div className="radio-box">
              <input
                type="radio"
                name="channelType"
                value="FORUM"
                onChange={handleChannelTypeChange}
              />
              <label>FORUM</label>
            </div>
          </div>
          <div className="radio-option">
            <div className="radio-box">
              <input
                type="radio"
                name="channelType"
                value="TEXT"
                onChange={handleChannelTypeChange}
              />
              <label>TEXT</label>
            </div>
          </div>
        </div>

        <div className="server-add-modal-body">
          <label htmlFor="categoryName" className="category-input-label">
            채널 이름
          </label>
          <input
            type="text"
            id="categoryName"
            placeholder="채널 이름을 입력하세요"
            onChange={channelNameHandler}
          />
          <button onClick={registerChannel}>등록하기</button>
        </div>

      </Modal>
      <Outlet/>
    </div>
  );
}