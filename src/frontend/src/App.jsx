import {Route, Routes} from "react-router-dom";
import Guilds from "./pages/Guilds";
import Channels from "./pages/Channels";
import Forum from "./pages/type/Forum";
import Text from "./pages/type/Text";
import Voice from "./pages/type/Voice";
import Main from "./pages/Main";
import DM from "./pages/dm/DM";

function App() {
  return (
    <div className="App">
      <Guilds/>
      <Routes>
        <Route path={`/main`} element={<Main/>}>
          <Route path={`room/:roomid`} element={<DM/>}/>
        </Route>
        <Route path={`/guild/:guildId/`} element={<Channels/>}>
          <Route path={`channel/forum/:channelId`} element={<Forum/>}/>
          <Route path={`channel/text/:channelId`} element={<Text/>}/>
          <Route path={`channel/voice/:channelId`} element={<Voice/>}/>
        </Route>
      </Routes>
    </div>
  );
}

export default App;
