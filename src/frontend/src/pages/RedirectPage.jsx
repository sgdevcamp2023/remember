import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import AuthStore from "../store/AuthStore";

const RedirectPage = () => {
  const navigate = useNavigate();
  const { USER_ID, ACCESS_TOKEN } = AuthStore();

  useEffect(() => {
    if (USER_ID && ACCESS_TOKEN) {
    // *************** 메인 소켓 연결 **************
      navigate("/channels/@me");
      return;
    }

    navigate("/login");

    return () => {};
  }, []);

  return <></>;
};

export default RedirectPage;
