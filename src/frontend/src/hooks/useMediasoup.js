import * as MediasoupClient from "mediasoup-client";
import { useNavigate } from "react-router-dom";
import MediaStore from "../store/MediaStore";
import SocketStore from "../store/SocketStore";
import CurrentStore from "../store/CurrentStore";
import AuthStore from "../store/AuthStore";
import { useMediaStream } from "../contexts/MediaStreamContext";

function useMediasoup() {
  const {
    setAudioParams,
    setVideoParams,
    setRtpCapabilities,
    setDevice,
    setSendTransport,
    setAudioProducer,
    setVideoProducer,
    setRecvTransport,
    setRecvAudioConsumer,
    setDisplayProducer,
    setRecvVideoConsumer,
    removeDisplayProducer,
  } = MediaStore();
  const {
    setAudioStream,
    setVideoStream,
    setPeerVideoStream,
    videoStream,
    audioStream,
  } = useMediaStream();

  const { setCurrentViewChannel } = CurrentStore();

  const navigate = useNavigate();

  const getLocalAudioStream = () => {
    navigator.mediaDevices
      .getUserMedia({
        audio: true,
      })
      .then(audioStreamSuccess)
      .catch((err) => {
        console.error(err);
      });
  };

  const getLocalDisplayStream = () => {
    navigator.mediaDevices
      .getDisplayMedia({
        video: {
          cursor: "always",
        },
        audio: false,
      })
      .then((stream) => videoStreamSuccess(stream, "display"))
      .catch((err) => {
        console.error(err);
      });
  };

  const getLocalCameraStream = () => {
    navigator.mediaDevices
      .getUserMedia({
        video: {
          width: { min: 640, max: 1920 },
          height: { min: 400, max: 1080 },
        },
      })
      .then((stream) => videoStreamSuccess(stream, "camera"))
      .catch((err) => {
        console.error(err);
      });
  };

  const videoStreamSuccess = (stream, type) => {
    let videoParams = { track: stream.getVideoTracks()[0], type: type };

    setVideoParams(videoParams);
    const videoStream = {
      [type]: {
        track: stream.getVideoTracks()[0],
      },
    };
    setVideoStream((prevStream) => ({
      ...prevStream,
      ...videoStream,
    }));
    connectSendTransport("video");
  };

  const audioStreamSuccess = (stream) => {
    let audioParams = { track: stream.getAudioTracks()[0] };
    setAudioParams(audioParams);
    getRtpCapabilities();
  };

  const getRtpCapabilities = () => {
    const VOICE_SOCKET = SocketStore.getState().VOICE_SOCKET;
    const PARSED_ROOM_URL = CurrentStore.getState().PARSED_ROOM_URL;

    VOICE_SOCKET.emit(
      "createRouter",
      PARSED_ROOM_URL,
      (responseRtpCapabilities) => {
        let rtpCapabilities = responseRtpCapabilities;
        setRtpCapabilities(rtpCapabilities);
        createDevice();
      }
    );
  };

  const createDevice = async () => {
    try {
      let device = new MediasoupClient.Device();
      setDevice(device);
      await device.load({
        routerRtpCapabilities: MediaStore.getState().RTP_CAPABILITIES,
      });
      createSendTransport();
      createRecvTransport();
    } catch (error) {
      console.error(error);
      if (error.name === "UnsupportedError")
        console.warn("browser not supported");
    }
  };

  const createSendTransport = () => {
    const { VOICE_SOCKET } = SocketStore.getState();
    const { USER_ID } = AuthStore.getState();
    const { CURRENT_JOIN_GUILD, CURRENT_JOIN_CHANNEL, PARSED_ROOM_URL } =
      CurrentStore.getState();
    const { DEVICE } = MediaStore.getState();

    VOICE_SOCKET.emit(
      "createWebRtcTransport",
      { consumer: false, roomId: PARSED_ROOM_URL },
      (transportParams) => {
        if (transportParams.error) {
          console.error(transportParams.error);
          return;
        }

        const sendTransport = DEVICE.createSendTransport(transportParams);
        sendTransport.on(
          "connect",
          async ({ dtlsParameters }, callback, errback) => {
            try {
              await VOICE_SOCKET.emit("transport-connect", {
                dtlsParameters,
                consumer: false,
              });
              callback();
            } catch (error) {
              errback(error);
            }
          }
        );

        sendTransport.on("produce", async (parameters, callback, errback) => {
          const { CURRENT_VIEW_CHANNEL_TYPE } = CurrentStore.getState();
          try {
            await VOICE_SOCKET.emit(
              "transport-produce",
              {
                kind: parameters.kind,
                rtpParameters: parameters.rtpParameters,
                roomId: PARSED_ROOM_URL,
                consumer: false,
                mediaTag: parameters.appData.mediaTag,
              },
              ({ producerId, producerExist }) => {
                callback({ producerId });

                if (parameters.kind === "audio") {
                  if (producerExist) {
                    getProducers("audio", producerId);
                  }
                  setVideoStream({});
                  setPeerVideoStream({});
                  getProducersWithoutProducerId("video");
                  if (CURRENT_VIEW_CHANNEL_TYPE === "VOICE") {
                    setCurrentViewChannel(CURRENT_JOIN_CHANNEL);
                    navigate(
                      `/channels/${CURRENT_JOIN_GUILD}/${CURRENT_JOIN_CHANNEL}`
                    );
                  }
                  VOICE_SOCKET.emit("start-voice-call", {
                    guildId: CURRENT_JOIN_GUILD,
                    channelId: CURRENT_JOIN_CHANNEL,
                    userId: USER_ID,
                  });
                }
                if (parameters.kind === "video") {
                  if (producerExist) {
                    VOICE_SOCKET.emit("new-video-producer", {
                      roomId: PARSED_ROOM_URL,
                      producerId,
                    });
                  }
                }
              }
            );
          } catch (error) {
            errback(error);
          }
        });

        setSendTransport(sendTransport);
        connectSendTransport("audio");
      }
    );
  };

  const createRecvTransport = async () => {
    const { VOICE_SOCKET } = SocketStore.getState();
    const { PARSED_ROOM_URL } = CurrentStore.getState();
    const { DEVICE } = MediaStore.getState();
    try {
      VOICE_SOCKET.emit(
        "createWebRtcTransport",
        {
          roomId: PARSED_ROOM_URL,
          consumer: true,
        },
        (transportParams) => {
          const recvTransport = DEVICE.createRecvTransport(transportParams);

          recvTransport.on(
            "connect",
            async ({ dtlsParameters }, callback, errback) => {
              try {
                await VOICE_SOCKET.emit("transport-recv-connect", {
                  dtlsParameters,
                  consumer: true,
                });

                callback();
              } catch (error) {
                errback(error);
              }
            }
          );
          setRecvTransport(recvTransport);
        }
      );
    } catch (error) {
      console.error(error);
    }
  };

  const connectSendTransport = async (media) => {
    const { SEND_TRANSPORT, AUDIO_PARAMS, VIDEO_PARAMS } =
      MediaStore.getState();
    if (media === "audio") {
      const audioProducer = await SEND_TRANSPORT.produce({
        track: AUDIO_PARAMS.track,
        appData: { mediaTag: "audio" },
      });

      audioProducer.on("trackended", () => {
        console.log("audioProducer track ended");
      });

      audioProducer.on("transportclose", () => {
        console.log("audioProducer transport ended");
      });
      setAudioProducer(audioProducer);
    }

    if (media === "video") {
      const mediaTag = VIDEO_PARAMS.type;
      const videoProducer = await SEND_TRANSPORT.produce({
        track: VIDEO_PARAMS.track,
        appData: { mediaTag },
      });
      videoProducer.on("trackended", () => {
        console.log("videoProducer track ended");
        const { DISPLAY_PRODUCER } = MediaStore.getState();
        closeProducer(DISPLAY_PRODUCER);
        removeDisplayProducer();
        setVideoStream({ ...videoStream, display: null });
      });

      videoProducer.on("transportclose", () => {
        console.log("videoProducer transport ended");
      });

      mediaTag === "camera"
        ? setVideoProducer(videoProducer)
        : setDisplayProducer(videoProducer);
    }
  };

  const getProducers = async (kind, producerId) => {
    const { VOICE_SOCKET } = SocketStore.getState();
    const { PARSED_ROOM_URL } = CurrentStore.getState();

    await VOICE_SOCKET.emit(
      "getProducers",
      {
        roomId: PARSED_ROOM_URL,
        kind,
        producerId,
      },
      (producerIds) => {
        producerIds.forEach((remoteProducerId) => {
          signalNewConsumerTransport(remoteProducerId);
        });
      }
    );
  };

  const getProducersWithoutProducerId = async (kind) => {
    const { VOICE_SOCKET } = SocketStore.getState();
    const { PARSED_ROOM_URL } = CurrentStore.getState();

    await VOICE_SOCKET.emit(
      "getProducers",
      {
        roomId: PARSED_ROOM_URL,
        kind,
      },
      (producerIds) => {
        producerIds.forEach((remoteProducerId) => {
          signalNewConsumerTransport(remoteProducerId);
        });
      }
    );
  };

  const signalNewConsumerTransport = async (remoteProducerId) => {
    const { RECV_TRANSPORT } = MediaStore.getState();
    try {
      connectRecvTransport(RECV_TRANSPORT, remoteProducerId);
    } catch (error) {
      console.error(error);
    }
  };

  const connectRecvTransport = async (recvTransport, remoteProducerId) => {
    const { VOICE_SOCKET } = SocketStore.getState();
    const { PARSED_ROOM_URL } = CurrentStore.getState();
    const { DEVICE } = MediaStore.getState();
    await VOICE_SOCKET.emit(
      "consume",
      {
        rtpCapabilities: DEVICE.rtpCapabilities,
        roomId: PARSED_ROOM_URL,
        remoteProducerId,
      },
      async ({ params }) => {
        if (params.error) {
          console.log(params.error);
          return;
        }
        const consumer = await recvTransport.consume({
          id: params.id,
          producerId: params.producerId,
          kind: params.kind,
          rtpParameters: params.rtpParameters,
        });

        const newConsumerObj = {
          serverConsumerId: params.id,
          remoteProducerId,
          consumer,
        };

        const { track } = consumer;
        const mediaStream = new MediaStream([track]);

        if (params.kind === "audio") {
          setRecvAudioConsumer(newConsumerObj);

          setAudioStream((prevStreams) => ({
            ...prevStreams,
            [remoteProducerId]: {
              kind: params.kind,
              stream: mediaStream,
            },
          }));
          VOICE_SOCKET.emit("consumer-resume", {
            kind: params.kind,
            roomId: PARSED_ROOM_URL,
            serverConsumerId: params.id,
          });
        }
        if (params.kind === "video") {
          setRecvVideoConsumer(newConsumerObj);
          setPeerVideoStream((prevStreams) => ({
            ...prevStreams,
            [remoteProducerId]: {
              kind: params.kind,
              stream: mediaStream,
              consumerId: params.id,
            },
          }));
        }
      }
    );
  };

  const toggleVideo = (consumerId) => {
    const { PARSED_ROOM_URL } = CurrentStore.getState();
    const { VOICE_SOCKET } = SocketStore.getState();
    VOICE_SOCKET.emit("consumer-toggle", {
      roomId: PARSED_ROOM_URL,
      serverConsumerId: consumerId,
      kind: "video",
    });
  };

  const closeProducer = async (producer) => {
    const { VOICE_SOCKET } = SocketStore.getState();
    const { PARSED_ROOM_URL } = CurrentStore.getState();
    if (producer) {
      VOICE_SOCKET.emit("producer-close", {
        roomId: PARSED_ROOM_URL,
        kind: producer.kind,
        mediaTag: producer.appData.mediaTag,
      });
      return new Promise((resolve) => {
        producer.on("@close", resolve);
        producer.close();
      });
    }
  };

  const closeMyProducer = async (producer) => {
    if (producer) {
      return new Promise((resolve) => {
        producer.on("@close", resolve);
        producer.close();
      });
    }
  };

  const closeConsumer = async (consumer) => {
    if (consumer) {
      return new Promise((resolve) => {
        consumer.on("@close", resolve);
        consumer.close();
      });
    }
  };

  const closeTransport = async (transport) => {
    if (transport) {
      return new Promise((resolve) => {
        transport.on("@close", resolve);
        transport.close();
      });
    }
  };

  const closeAll = async () => {
    setAudioStream({});
    setVideoStream({});

    const {
      AUDIO_PRODUCER,
      VIDEO_PRODUCER,
      DISPLAY_PRODUCER,
      RECV_AUDIO_CONSUMER,
      RECV_VIDEO_CONSUMER,
      SEND_TRANSPORT,
      RECV_TRANSPORT,
    } = MediaStore.getState();
    for (const { consumer } of RECV_AUDIO_CONSUMER) {
      await closeConsumer(consumer);
    }
    for (const { consumer } of RECV_VIDEO_CONSUMER) {
      await closeConsumer(consumer);
    }

    await closeMyProducer(AUDIO_PRODUCER);
    await closeMyProducer(VIDEO_PRODUCER);
    await closeMyProducer(DISPLAY_PRODUCER);

    await closeTransport(SEND_TRANSPORT);
    await closeTransport(RECV_TRANSPORT);
  };

  // end of code

  return {
    getLocalAudioStream,
    getLocalCameraStream,
    getLocalDisplayStream,
    signalNewConsumerTransport,
    closeAll,
    closeProducer,
    getProducers,
    getProducersWithoutProducerId,
    toggleVideo,
  };
}

export default useMediasoup;
