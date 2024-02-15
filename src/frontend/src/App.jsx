import { Route, Routes } from "react-router-dom";
import RegisterPage from "./pages/RegisterPage";
import MainPage from "./pages/MainPage";
import Layout from "./pages/Layout";
import ForgetPasswordPage from "./pages/ForgetPasswordPage";
import RedirectPage from "./pages/RedirectPage";
import FriendPage from "./pages/dm/FriendPage";
import LoginPage from "./pages/LoginPage";

function App() {
  return (
    // <AppElement>
    <Routes>
      <Route path={`/`}>
        <Route index element={<RedirectPage />} />
        <Route path={"login"} element={<LoginPage />} />
        <Route path={"register"} element={<RegisterPage />} />
        <Route path={"forget-password"} element={<ForgetPasswordPage />} />

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
