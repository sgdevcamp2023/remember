import Modal from "react-modal";
import {useEffect, useState} from "react";
import CurrentStore from "../store/CurrentStore";
import {createChannel} from "../Request/communityRequest";
import ChannelStore from "../store/ChannelStore";
import CommunityStore from "../store/CommunityStore";
import AuthStore from "../store/AuthStore";

const ChannelModal = (props) => {

  const {
    CURRENT_VIEW_GUILD,
  } = CurrentStore();
  const {CHANNEL} = ChannelStore();
  const [Channel, setChannel] = useState("");
  const [ChannelType, setChannelType] = useState("");
  const {CHANNEL_LIST, setChannelList} = CommunityStore();
  const USER_ID = AuthStore(state => state.USER_ID);

  const channelNameHandler = (e) => {
    setChannel(e.currentTarget.value);
  };
  const handleChannelTypeChange = (e) => {
    setChannelType(e.target.value);
  };

  const registerChannel = (e) => {
    e.preventDefault();
    const data = {
      guildId: CURRENT_VIEW_GUILD,
      name: Channel,
      userId: USER_ID,
      categoryId: 0,
      type: ChannelType,
    };

    const makeChannel = async (data) => {
      const wait = await createChannel(data);
    }

    makeChannel(data);
    props.closeModal();
  };

  useEffect(() => {
    if (CHANNEL) {
      let data = {
        categoryId: CHANNEL.categoryId,
        name: CHANNEL.channelName,
        channelReadId: CHANNEL.channelReadId,
        type: CHANNEL.channelType,
        guildId: CHANNEL.guildId
      };
      CHANNEL_LIST.push(data);
      setChannelList(CHANNEL_LIST);
    }
  }, [CHANNEL]);


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
          <button onClick={registerChannel}>등록하기
          </button>
          <button onClick={props.closeModal}>취소</button>
        </div>
      </Modal>
    </div>
  );
}

export default ChannelModal;