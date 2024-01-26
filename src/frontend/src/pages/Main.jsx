import "../css/Main.css"
import {Link, Outlet} from "react-router-dom";
import DmRoomsStore from "../store/DmRoomsStore";
import Room from "./Room";

export default function Main() {
  const {rooms, registerRooms, removeRooms} = DmRoomsStore();


  return (
    <div className="main">
      <h1>Main</h1>
      {rooms.length > 0 ? rooms.map((room, index) => (
        <Link to={`/main/room/${room.roomId}`} className="no-decoration">
          <div key={index}>
            <Room room={room}/>
          </div>
        </Link>
      )) : <></>}
      <Outlet/>
    </div>);
}