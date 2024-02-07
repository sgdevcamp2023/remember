import React, { useEffect } from "react";
import { Outlet } from "react-router-dom";

import Guild from "../components/Guild";
import Channel from "../components/Channel";
import CurrentStore from "../store/CurrentStore";

const Layout = () => {
  return (
    <div>
      <div>
        {/* Guild section */}
        <Guild />
        {/* Channel section */}
        <Channel />
        {/* Main Section */}
        <section>
          <Outlet />
        </section>
        {/* user status section */}
        {/* <Status/> */}
      </div>
    </div>
  );
};

export default Layout;
