import React, { useState, useEffect } from 'react';
import { getCurrentTrack } from '../services/api';

const NowPlaying = () => {
  const [currentTrack, setCurrentTrack] = useState(null);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchCurrentTrack = async () => {
      try {
        setIsLoading(true);
        const response = await getCurrentTrack();
        setCurrentTrack(response.data);
        setError(null);
      } catch (err) {
        setError('Failed to fetch current track');
        console.error('Error fetching current track:', err);
      } finally {
        setIsLoading(false);
      }
    };

    fetchCurrentTrack();
    const intervalId = setInterval(fetchCurrentTrack, 30000);
    return () => clearInterval(intervalId);
  }, []);

  if (isLoading) {
    return <div>Loading...</div>;
  }

  if (error) {
    return <div>Error: {error}</div>;
  }

  return (
    <div className="now-playing">
      <h2>Now Playing</h2>
      {currentTrack ? (
        <>
          <p>Title: {currentTrack.title}</p>
          <p>Artist: {currentTrack.artist}</p>
          <p>Album: {currentTrack.album}</p>
          {currentTrack.sourceType === 'YouTube' && (
            <img 
              src={`https://img.youtube.com/vi/${currentTrack.id}/0.jpg`} 
              alt={`${currentTrack.title} thumbnail`}
            />
          )}
        </>
      ) : (
        <p>No track currently playing</p>
      )}
    </div>
  );
};

export default NowPlaying;
