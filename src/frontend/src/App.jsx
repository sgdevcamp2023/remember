import { Route, Routes } from "react-router-dom";
import LogIn from "./pages/login";
import Register from "./pages/register";
import MainPage from "./pages/MainPage";
import Layout from "./pages/Layout";
import ForgetPassword from "./pages/forgetPassword";
import styled from "styled-components";
import backImg from "./img/backImg.webp";
import RedirectPage from "./pages/RedirectPage";
import FriendPage from "./pages/dm/FriendPage";

const AppElement = styled.div`
  background-image: url(${backImg});
  background-size: cover;
`;

function App() {
  const isLogin = localStorage.getItem("access_token") !== null;

  return (
    // <AppElement>
    <Routes>
      <Route path={`/`}>
        <Route index element={<RedirectPage />} />
        <Route path={"login"} element={<LogIn />} />
        <Route path={"register"} element={<Register />} />
        <Route path={"forget-password"} element={<ForgetPassword />} />

        {/* Guild and Channel */}
        <Route path="/channels" element={<Layout />}>
          <Route path={`:guildId/:channelId`} element={<MainPage />} />
          <Route path={`@me`} element={<FriendPage />} />
        </Route>
        {/* Catch all - replace with 404 component if you want */}
        {/* <Route path="*" element={<Navigate to="/" replace />} /> */}
      </Route>
    </Routes>
    // </AppElement>
  );
}

export default App;
