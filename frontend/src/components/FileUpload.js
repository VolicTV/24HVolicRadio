import React, { useState } from 'react';
import { uploadAudio } from '../services/api';

const FileUpload = ({ onUploadSuccess }) => {
  const [file, setFile] = useState(null);

  const handleFileChange = (e) => {
    setFile(e.target.files[0]);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!file) {
      alert('Please select a file');
      return;
    }
    try {
      const response = await uploadAudio(file);
      onUploadSuccess(response);
      setFile(null);
    } catch (error) {
      console.error('Error uploading file:', error);
      alert('Failed to upload file');
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <input type="file" onChange={handleFileChange} accept="audio/*" />
      <button type="submit">Upload</button>
    </form>
  );
};

export default FileUpload;
