import React, { useState } from 'react';
import Player from './components/Player';
import NowPlaying from './components/NowPlaying';
import './App.css';

function App() {
  const [currentTrack, setCurrentTrack] = useState(null);

  const handleTrackChange = (track) => {
    setCurrentTrack(track);
  };

  return (
    <div className="App">
      <header className="App-header">
        <h1>24 Hour Volic Radio</h1>
      </header>
      <main className="player-container">
        <Player onTrackChange={handleTrackChange} />
        <NowPlaying track={currentTrack} />
      </main>
    </div>
  );
}

export default App;