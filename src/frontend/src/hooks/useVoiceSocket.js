import { useRef } from "react";
import { io } from "socket.io-client";
import CurrentStore from "../store/CurrentStore";
import AuthStore from "../store/AuthStore";
import SocketStore from "../store/SocketStore";
import useMediasoup from "./useMediasoup";
import { useMediaStream } from "../contexts/MediaStreamContext";
import MediaStore from "../store/MediaStore";

function useVoiceSocket(url) {
  const {
    setCurrentJoinGuild,
    setCurrentJoinChannel,
    setParsedRoomUrl,
    removeCurrentJoinGuild,
    removeCurrentJoinChannel,
    removeParsedRoomUrl,
  } = CurrentStore();
  // const { USER_ID } = AuthStore();
  const { setVoiceSocket, removeVoiceSocket } = SocketStore();
  const { setPeerVideoStream, setAudioStream } = useMediaStream();
  const { getLocalAudioStream, signalNewConsumerTransport, closeAll } =
    useMediasoup();
  const { removeRecvAudioConsumer, removeRecvVideoConsumer } = MediaStore();

  let voice_socket = useRef();
  let join_guild = useRef();
  let join_channel = useRef();
  let try_guild = useRef();
  let try_channel = useRef();

  let isUserInChannel = false;

  const connectSocket = async () => {
    try {
      voice_socket.current = io(url);
      setVoiceSocket(voice_socket.current);
    } catch (error) {
      console.error("소켓 연결에 실패했습니다:", error);
    }
  };

  const disconnectSocket = () => {
    if (voice_socket.current) {
      voice_socket.current.disconnect();
    }
  };

  const addEventListeners = () => {
    if (voice_socket.current) {
      voice_socket.current.on("new-producer-init", (producerId) => {
        signalNewConsumerTransport(producerId);
      });

      voice_socket.current.on("new-video-producer-init", (producerId) => {
        signalNewConsumerTransport(producerId);
      });

      voice_socket.current.on("producer-closed", ({ kind, producerId }) => {
        if (kind === "audio") {
          removeRecvAudioConsumer(producerId);
          setAudioStream((prevStreams) => {
            const updatedStreams = { ...prevStreams };
            delete updatedStreams[producerId];
            return updatedStreams;
          });
        }
        if (kind === "video") {
          removeRecvVideoConsumer(producerId);
          setPeerVideoStream((prevStreams) => {
            const updatedStreams = { ...prevStreams };
            delete updatedStreams[producerId];
            return updatedStreams;
          });
        }
      });

      voice_socket.current.on("message", (msg) => {
        console.log(msg);
      });

      voice_socket.current.on("disconnect", async () => {
        closeAll();
        removeGlobalState();
        if (isUserInChannel) {
          if (!voice_socket.current || !voice_socket.current.connected) {
            connectSocket();
            addEventListeners();
          }

          validateUserInChannel(try_guild.current, try_channel.current);
          isUserInChannel = false;
        }
      });

      voice_socket.current.on("validate-new-user", () => {
        validateUserInGuild(try_guild.current);
      });

      voice_socket.current.on("user-in-channel", () => {
        const agree = window.confirm(
          "이미 음성 채널에 접속 중입니다. 이동하시겠습니까?"
        );
        if (!agree) {
          try_guild.current = null;
          try_channel.current = null;
          return;
        }
        isUserInChannel = true;
        leaveChannel();
      });

      voice_socket.current.on("join-channel", () => {
        setJoinGuildAndChannel(try_guild.current, try_channel.current);

        if (SocketStore.getState().VOICE_SOCKET) getLocalAudioStream();
      });
    }
  };

  const leaveChannel = () => {
    voice_socket.current.emit("leave-channel", {
      socketId: voice_socket.current.id,
    });
  };

  const validateUserInChannel = (guildId, channelId) => {
    try_guild.current = guildId;
    try_channel.current = channelId;
    if (voice_socket.current) {
      voice_socket.current.emit("validate-user-in-channel", {
        guildId,
        channelId,
      });
    }
  };

  const validateUserInGuild = (guildId) => {
    const { USER_ID } = AuthStore.getState();
    if (voice_socket.current) {
      voice_socket.current.emit("validate-user-in-guild", {
        guildId,
        userId: USER_ID,
      });
    }
  };

  const setJoinGuildAndChannel = (guildId, channelId) => {
    join_guild.current = guildId;
    join_channel.current = channelId;
    setCurrentJoinGuild(join_guild.current);
    setCurrentJoinChannel(join_channel.current);
    setParsedRoomUrl(`${join_guild.current}-${join_channel.current}`);
  };

  const removeGlobalState = () => {
    removeVoiceSocket();
    removeCurrentJoinGuild();
    removeCurrentJoinChannel();
  };

  return {
    connectSocket,
    addEventListeners,
    validateUserInChannel,
    voice_socket: voice_socket.current,
  };
}

export default useVoiceSocket;