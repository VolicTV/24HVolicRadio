import axios from 'axios';

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL || 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

export const getCurrentTrack = () => api.get('/stream/current');

export const getNextTrack = () => api.get('/stream/next');

export const startStreaming = () => api.post('/stream/start');

export const stopStreaming = () => api.post('/stream/stop');

export const searchTracks = (query) => api.get('/tracks/search', { params: { q: query } });

// Add more API calls as needed

export default api;
