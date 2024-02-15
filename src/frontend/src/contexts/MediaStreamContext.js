import React, { createContext, useState, useContext } from "react";

export const MediaStreamContext = createContext();

export const MediaStreamProvider = ({ children }) => {
  const [audioStream, setAudioStream] = useState({});
  // "type" : {track, track....}
  const [videoStream, setVideoStream] = useState({});

  return (
    <MediaStreamContext.Provider
      value={{ audioStream, setAudioStream, videoStream, setVideoStream }}
    >
      {children}
    </MediaStreamContext.Provider>
  );
};

export const useMediaStream = () => useContext(MediaStreamContext);
