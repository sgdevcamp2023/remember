import React, { useEffect, useRef } from "react";
import "../css/MyVideoBox.css";
import { useMediaStream } from "../contexts/MediaStreamContext";

const MyVideoBox = () => {
  const camera = useRef(null);
  const display = useRef(null);
  const { videoStream, audioStream } = useMediaStream();

  useEffect(() => {
    if (videoStream && videoStream.camera) {
      camera.current.srcObject = new MediaStream([videoStream.camera.track]);
      camera.current.play();
    }

    if (videoStream && videoStream.display) {
      display.current.srcObject = new MediaStream([videoStream.display.track]);
      display.current.play();
    }
  }, [videoStream]);

  useEffect(() => {
    console.log("videoStream", videoStream);
    console.log("audioStream", audioStream);
  }, [videoStream, audioStream]);

  return (
    <div className="my-video-box">
      {videoStream.camera ? (
        <div className="video-container">
          <video ref={camera} autoPlay className="video" />
        </div>
      ) : (
        <> </>
      )}
      {videoStream.display ? (
        <div className="video-container">
          <video ref={display} autoPlay className="video" />
        </div>
      ) : (
        <> </>
      )}
    </div>
  );
};

export default MyVideoBox;
