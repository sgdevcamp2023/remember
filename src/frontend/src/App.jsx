import { Route, Routes } from "react-router-dom";
import Guilds from "./pages/Guilds";
import Channels from "./pages/Channels";
import Forum from "./pages/type/Forum";
import Text from "./pages/type/Text";
import Voice from "./pages/type/Voice";
import Main from "./pages/Main";
import DM from "./pages/dm/DM";
import LogIn from "./pages/login";
import Register from "./pages/register";
import ForgetPassword from "./pages/forgetPassword";
import styled from "styled-components";
import backImg from "./img/backImg.webp";

const AppElement = styled.div`
    background-image: url(${backImg});
    background-size: cover; 
`;

function App() {
  const isLogin = localStorage.getItem("access_token") !== null;

  return (
    <AppElement>
      {isLogin ? <Guilds /> : <></>}
      <Guilds/>
      <Routes>
        <Route path={`/`} element={<LogIn />} />
        <Route path={'register'} element={<Register />} />
        <Route path={'forget-password'} element={<ForgetPassword />} />
        {/* <Route path={`/main`} element={<Main />}>
          <Route path={`room/:roomid`} element={<DM />} />
        </Route>
        <Route path={`guild/:guildId/`} element={<Channels />}>
          <Route path={`channel/forum/:channelId`} element={<Forum />} />
          <Route path={`channel/text/:channelId`} element={<Text />} />
          <Route path={`channel/voice/:channelId`} element={<Voice />} />
        </Route> */}
      </Routes>
    </AppElement>
  );
}

export default App;
