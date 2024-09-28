import axios from 'axios';

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL || 'http://localhost:8080';

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

export const searchYoutube = async (searchQuery) => {
  try {
    const response = await api.get(`/api/search?q=${encodeURIComponent(searchQuery)}`);
    return response.data;
  } catch (error) {
    console.error('Error searching YouTube:', error);
    throw error;
  }
};

export const processAudio = async (videoId) => {
  try {
    const response = await api.post(`/api/process?videoId=${videoId}`);
    return `${API_BASE_URL}/api/audio/${response.data}`;
  } catch (error) {
    console.error('Error processing audio:', error);
    throw error;
  }
};

export const uploadAudio = async (file) => {
  const formData = new FormData();
  formData.append('file', file);
  try {
    const response = await api.post('/api/upload', formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
    return response.data;
  } catch (error) {
    console.error('Error uploading audio:', error);
    throw error;
  }
};

// Add more API calls as needed

export default api;
