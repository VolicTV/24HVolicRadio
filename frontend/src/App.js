import React, { useState, useEffect } from 'react';
import NowPlaying from './components/NowPlaying';
import Player from './components/Player';
import { getCurrentTrack, startStreaming, stopStreaming } from './services/api';
import './App.css'; // Assuming you have some global styles

function App() {
  const [currentTrack, setCurrentTrack] = useState(null);
  const [isPlaying, setIsPlaying] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchCurrentTrack = async () => {
      try {
        const response = await getCurrentTrack();
        setCurrentTrack(response.data);
      } catch (err) {
        setError('Failed to fetch current track');
        console.error('Error fetching current track:', err);
      }
    };

    fetchCurrentTrack();
    const intervalId = setInterval(fetchCurrentTrack, 30000);
    return () => clearInterval(intervalId);
  }, []);

  const handlePlayPause = async () => {
    try {
      if (isPlaying) {
        await stopStreaming();
      } else {
        await startStreaming();
      }
      setIsPlaying(!isPlaying);
    } catch (err) {
      setError('Failed to control playback');
      console.error('Error controlling playback:', err);
    }
  };

  return (
    <div className="App">
      <header className="App-header">
        <h1>24 Hour Volic Radio</h1>
      </header>
      <main className="player-container">
        <NowPlaying track={currentTrack} />
        <Player 
          isPlaying={isPlaying} 
          onPlayPause={handlePlayPause}
          streamUrl={currentTrack?.streamUrl}
        />
        {error && <div className="error-message">{error}</div>}
      </main>
    </div>
  );
}

export default App;