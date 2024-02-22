import React, { useEffect } from "react";
import { useState } from "react";
import styled from "styled-components";
import { AllView } from "./AllView";
import { OnlineView } from "./OnlineView";
import { WaitListView } from "./WaitListView";
import { SendAddView } from "./SendAddView";
import { MyInfoView } from "./MyInfoView";
import { getUserDmRoom } from "../../Request/communityRequest";
import CommunityStore from "../../store/CommunityStore";
import AuthStore from "../../store/AuthStore";
import CurrentStore from "../../store/CurrentStore";

const Navigation = styled.nav`
  width: 95%;
  color: #fff;
  li {
    list-style: none;
  }
  ul {
    display: flex;
    justify-content: space-between;
  }
`;
const Wrapper = styled.div`
  display: flex;
  background-color: #2c2f33;
`;

const MainContainer = styled.div`
  width: 100%;
`;

const FriendPage = () => {
  const [crntTab, setCrntTab] = useState(0);
  const { DM_ROOM_LIST, setDmRoomList, removeDmRoomList } = CommunityStore();
  const { setCurrentViewGuildName } = CurrentStore();
  const { USER_ID } = AuthStore();

  useEffect(() => {
    if (window.location.pathname.includes("@me")) {
      setCurrentViewGuildName("Direct Message");
      const fetchDmRoom = async () => {
        const response = await getUserDmRoom(USER_ID);
        setDmRoomList(response.data?.resultData.roomResponseDtos || []);
        console.log("response", response);
        console.log("DM_ROOM_LIST", DM_ROOM_LIST);
      };
      fetchDmRoom();
    }
  }, []);

  const TabLists = [
    { name: "모두", content: <AllView /> },
    { name: "온라인", content: <OnlineView /> },
    { name: "친구 대기 목록", content: <WaitListView /> },
    { name: "친구 추가하기", content: <SendAddView /> },
    { name: "내 정보", content: <MyInfoView /> },
  ];

  const TabView = TabLists.map((el, idx) => {
    return (
      <li
        key={idx}
        onClick={() => {
          setCrntTab(idx);
        }}
      >
        {el.name}
      </li>
    );
  });
  return (
    <Wrapper>
      <MainContainer>
        <div className="main-section">
          <Navigation>
            <ul>{TabView}</ul>
          </Navigation>
          {TabLists[crntTab].content}
        </div>
      </MainContainer>
    </Wrapper>
  );
};

export default FriendPage;
