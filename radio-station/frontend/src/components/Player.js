import React, { useState, useEffect, useRef } from 'react';
import axios from 'axios';

const Player = () => {
  const [isPlaying, setIsPlaying] = useState(false);
  const [volume, setVolume] = useState(1);
  const [streamUrl, setStreamUrl] = useState('');
  const audioRef = useRef(null);

  useEffect(() => {
    const fetchStreamUrl = async () => {
      try {
        const response = await axios.get('/api/stream/current');
        setStreamUrl(response.data.streamUrl);
      } catch (error) {
        console.error('Error fetching stream URL:', error);
      }
    };

    fetchStreamUrl();
  }, []);

  useEffect(() => {
    if (audioRef.current) {
      audioRef.current.volume = volume;
    }
  }, [volume]);

  const togglePlayPause = () => {
    if (audioRef.current) {
      if (isPlaying) {
        audioRef.current.pause();
      } else {
        audioRef.current.play();
      }
      setIsPlaying(!isPlaying);
    }
  };

  const handleVolumeChange = (e) => {
    setVolume(parseFloat(e.target.value));
  };

  return (
    <div className="player">
      <audio ref={audioRef} src={streamUrl} />
      <button onClick={togglePlayPause}>
        {isPlaying ? 'Pause' : 'Play'}
      </button>
      <input
        type="range"
        min="0"
        max="1"
        step="0.01"
        value={volume}
        onChange={handleVolumeChange}
      />
      <span>{Math.round(volume * 100)}%</span>
    </div>
  );
};

export default Player;
