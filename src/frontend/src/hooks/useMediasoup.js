import * as MediasoupClient from "mediasoup-client";
import MediaStore from "../store/MediaStore";
import SocketStore from "../store/SocketStore";
import CurrentStore from "../store/CurrentStore";
import AuthStore from "../store/AuthStore";
import { useMediaStream } from "../contexts/MediaStreamContext";

function useMediasoup() {
  const {
    setAudioParams,
    setRtpCapabilities,
    setDevice,
    setSendTransport,
    setAudioProducer,
    setRecvTransport,
    setRecvAudioConsumer,
  } = MediaStore();
  const { setMediaStreams } = useMediaStream();

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
              console.log(">> audio producer connected");
            } catch (error) {
              errback(error);
            }
          }
        );

        sendTransport.on("produce", async (parameters, callback, errback) => {
          try {
            await VOICE_SOCKET.emit(
              "transport-produce",
              {
                kind: parameters.kind,
                rtpParameters: parameters.rtpParameters,
                roomId: PARSED_ROOM_URL,
                consumer: false,
              },
              ({ producerId, producerExist }) => {
                callback({ producerId });

                console.log(">> audio produce!!");

                // 추후 switch case로 교체?
                if (parameters.kind === "audio") {
                  if (producerExist) {
                    console.log(">> producer exist, get Producers");
                    getProducers("audio", producerId);
                  }
                  VOICE_SOCKET.emit("start-voice-call", {
                    guildId: CURRENT_JOIN_GUILD,
                    channelId: CURRENT_JOIN_CHANNEL,
                    userId: USER_ID,
                  });
                }
                // else if (parameters.kind === "video") {
                //   videoRef.current.srcObject = new MediaStream([
                //     videoParamsRef.current.track,
                //   ]);
                // }
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
                console.log(">> recvTransport connected");
              } catch (error) {
                errback(error);
              }
            }
          );
          setRecvTransport(recvTransport);
          console.log(">> recvTransport 생성", recvTransport);
        }
      );
    } catch (error) {
      console.error(error);
    }
  };

  const connectSendTransport = async (media) => {
    const { SEND_TRANSPORT, AUDIO_PARAMS } = MediaStore.getState();
    if (media === "audio") {
      const audioProducer = await SEND_TRANSPORT.produce(AUDIO_PARAMS);

      audioProducer.on("trackended", () => {
        console.log("audioProducer track ended");
      });

      audioProducer.on("transportclose", () => {
        console.log("audioProducer transport ended");
      });
      setAudioProducer(audioProducer);
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
        console.log(producerIds);
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

        if (params.kind === "audio") {
          setRecvAudioConsumer(newConsumerObj);

          const { track } = consumer;
          console.log(">> consuming audio");

          // 이부분
          const mediaStream = new MediaStream([track]);

          setMediaStreams((prevStreams) => ({
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
      }
    );
  };

  const closeProducer = async (producer) => {
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
    setMediaStreams({});
    const {
      AUDIO_PRODUCER,
      VIDEO_PRODUCER,
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

    await closeProducer(AUDIO_PRODUCER);
    await closeProducer(VIDEO_PRODUCER);

    await closeTransport(SEND_TRANSPORT);
    await closeTransport(RECV_TRANSPORT);
  };

  // end of code

  return { getLocalAudioStream, signalNewConsumerTransport, closeAll };
}

export default useMediasoup;
