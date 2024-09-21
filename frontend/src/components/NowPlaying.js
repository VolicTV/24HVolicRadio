import React from 'react';

const NowPlaying = ({ track }) => {
  if (!track) return null;

  return (
    <div className="now-playing">
      <h2>Now Playing</h2>
      <p>{track.title}</p>
      <p>Artist: {track.artist}</p>
      {track.album && <p>Album: {track.album}</p>}
      {track.year && <p>Year: {track.year}</p>}
      {track.duration && <p>Duration: {track.duration}</p>}
    </div>
  );
};

export default NowPlaying;
