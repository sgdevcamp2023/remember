import React from "react"
import { useState } from "react";
import styled from "styled-components";
import { AllView } from "./AllView";
import { OnlineView } from "./OnlineView";
import { WaitListView } from "./WaitListView";
import { SendAddView } from "./SendAddView";

const Navigation = styled.nav`
    width: 50%;
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

  const TabLists = [
    { name: "모두", content: <AllView /> },
    { name: "온라인", content: <OnlineView /> },
    { name: "친구 대기 목록", content: <WaitListView /> },
    { name: "친구 추가하기", content: <SendAddView /> },
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
              <ul>
                {TabView}
              </ul>
            </Navigation>
            {TabLists[crntTab].content}
        </div>
      </MainContainer>
    </Wrapper>
  );
};

export default FriendPage;
