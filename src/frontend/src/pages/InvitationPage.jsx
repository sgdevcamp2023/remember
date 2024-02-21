import {useNavigate, useParams} from "react-router-dom";
import AuthStore from "../store/AuthStore";
import React, {useEffect} from "react";
import {joinInvitation} from "../Request/communityRequest";

const InvitationPage = () => {
  const navigate = useNavigate();
  const {USER_ID, ACCESS_TOKEN, REDIRECT_URL, setRedirectUrl} = AuthStore();
  const params = useParams();

  useEffect(() => {
    if (USER_ID && ACCESS_TOKEN) {
      console.log(params.invitationCode);
      const fetchData = async (code, userId) => {
        const wait = await joinInvitation(code, userId);
      }

      fetchData(params.invitationCode, USER_ID);

      navigate("/");
      return;
    }
    setRedirectUrl("/invitation/" + params.invitationCode);
    console.log(AuthStore.getState().REDIRECT_URL);
    navigate("/login");

    return () => {
    };
  }, []);

  return <></>;
};

export default InvitationPage;
