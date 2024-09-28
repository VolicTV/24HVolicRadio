import React, { useState, useEffect, useRef } from 'react';
import { searchYoutube, processAudio, uploadAudio } from '../services/api';
import FileUpload from './FileUpload';

const Player = ({ onTrackChange }) => {
  const [isPlaying, setIsPlaying] = useState(false);
  const [volume, setVolume] = useState(1);
  const [currentTrack, setCurrentTrack] = useState(null);
  const [searchQuery, setSearchQuery] = useState('');
  const [searchResults, setSearchResults] = useState([]);
  const audioRef = useRef(null);
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    if (audioRef.current) {
      audioRef.current.volume = volume;
    }
  }, [volume]);

  useEffect(() => {
    const audio = audioRef.current;
    if (audio) {
      const handleCanPlayThrough = () => {
        setIsLoading(false);
        if (isPlaying) {
          audio.play().catch(error => console.error('Error playing audio:', error));
        }
      };

      audio.addEventListener('canplaythrough', handleCanPlayThrough);
      return () => {
        audio.removeEventListener('canplaythrough', handleCanPlayThrough);
      };
    }
  }, [isPlaying]);

  const togglePlayPause = () => {
    if (audioRef.current) {
      if (isPlaying) {
        audioRef.current.pause();
      } else {
        audioRef.current.play().catch(error => console.error('Error playing audio:', error));
      }
      setIsPlaying(!isPlaying);
    }
  };

  const handleVolumeChange = (e) => {
    setVolume(parseFloat(e.target.value));
  };

  const handleSearch = async () => {
    try {
      const results = await searchYoutube(searchQuery);
      setSearchResults(results);
    } catch (error) {
      console.error('Error searching YouTube:', error);
      // Handle error (e.g., show error message to user)
    }
  };

  const playTrack = async (track) => {
    setIsLoading(true);
    setCurrentTrack(track);
    onTrackChange(track);
    try {
      const audioUrl = await processAudio(track.id);
      if (audioRef.current) {
        audioRef.current.src = audioUrl;
        audioRef.current.play().catch(error => console.error('Error playing audio:', error));
        setIsPlaying(true);
      }
    } catch (error) {
      console.error('Error processing audio:', error);
    } finally {
      setIsLoading(false);
    }
  };

  const handleUploadSuccess = (response) => {
    console.log('File uploaded:', response);
    // We'll update this function later to refresh the playlist
  };

  return (
    <div className="player">
      <audio ref={audioRef} src={currentTrack?.fileUrl} />
      
      <FileUpload onUploadSuccess={handleUploadSuccess} />
      
      <div className="search-container">
        <input
          type="text"
          value={searchQuery}
          onChange={(e) => setSearchQuery(e.target.value)}
          placeholder="Search for tracks..."
        />
        <button onClick={handleSearch}>Search</button>
      </div>

      <div className="search-results">
        {searchResults.map((track) => (
          <div key={track.id} className="track-item" onClick={() => playTrack(track)}>
            {track.title} - {track.artist}
          </div>
        ))}
      </div>

      <div className="controls">
        <button onClick={togglePlayPause} disabled={isLoading}>
          {isLoading ? 'Loading...' : isPlaying ? 'Pause' : 'Play'}
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
    </div>
  );
};

export default Player;
