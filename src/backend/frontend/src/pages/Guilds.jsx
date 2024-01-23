import {Link} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";
import Guild from "./Guild";

export default function Guilds() {

  const apiAddr = "http://localhost:8000";
  const userId = localStorage.getItem("user_id");
  const [Guilds, setGuilds] = useState([]);
  const bearerToken = "Bearer " + localStorage.getItem("access_token");
  useEffect(() => {
    axios.get(apiAddr + `/api/community/check/guild/1`, {
      headers: {
        "Authorization": bearerToken
      }
    })
      .then(response => {
        console.log(response.data);
        setGuilds(response.data.resultData);
      })
      .catch(error => {
        console.error(error);
        localStorage.clear();
      })
  }, []);

  console.log(Guilds);

  return (
    <div className="sidebar">
      {Guilds.length > 0 ? Guilds.map((guild, index) => (
        <div key={index}>
          <Guild guild={guild}/>
        </div>
      )) : <></>}
    </div>
  );

}
