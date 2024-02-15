import { useEffect, useState } from "react";

const useAudioOutput = (mediaStreams) => {
  const [audioContexts, setAudioContexts] = useState({});

  useEffect(() => {
    const newAudioContexts = {};

    for (const id in mediaStreams) {
      const stream = mediaStreams[id];

      if (!stream) {
        continue;
      }

      // AudioContext 생성
      const context = new AudioContext();

      // MediaStream을 AudioContext에 연결
      const source = context.createMediaStreamSource(stream);
      source.connect(context.destination);

      newAudioContexts[id] = context;
    }

    setAudioContexts(newAudioContexts);

    // 컴포넌트가 언마운트되거나 mediaStreams이 변경될 때 AudioContext 정리
    return () => {
      for (const id in newAudioContexts) {
        const context = newAudioContexts[id];
        context.close();
      }
    };
  }, [mediaStreams]);

  return audioContexts;
};

export default useAudioOutput;
