import React, { createContext, useState, useContext } from "react";

export const MediaStreamContext = createContext();

export const MediaStreamProvider = ({ children }) => {
  const [mediaStreams, setMediaStreams] = useState({});

  return (
    <MediaStreamContext.Provider value={{ mediaStreams, setMediaStreams }}>
      {children}
    </MediaStreamContext.Provider>
  );
};

export const useMediaStream = () => useContext(MediaStreamContext);
