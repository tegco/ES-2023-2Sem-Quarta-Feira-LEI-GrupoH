import React, { useState } from 'react';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import { processFile } from '../utils/fileProcessing';

const UrlUpload = ({ setFileName, setTempEvents }) => {
  const [url, setUrl] = useState('');

  const handleUrlChange = async (e) => {
    setUrl(e.target.value);
    setFileName(getFilenameFromUrl(e.target.value));
    const file = await getFileFromURL(e.target.value);
    processFile(file, setTempEvents);
  };

  const getFileFromURL = async (url) => {
    try {
      const response = await fetch(url);
      const data = await response.text();
      const filename = getFilenameFromUrl(url);
      if (filename.split('.')[1] === 'json') {
        return new File([data], filename, { type: 'application/json' });
      } else if (filename.split('.')[1] === 'csv') {
        console.log('getFileFromURL: ' + filename);
        return new File([data], filename, { type: 'text/csv' });
      } else {
        console.error('Invalid URL: File should be a JSON or CSV file.');
        return null;
      }
    } catch (error) {
      console.error('Error fetching the file', error);
    }
  };

  const getFilenameFromUrl = (url) => {
    const parts = url.split('/');
    const filename = parts[parts.length - 1];
    if (filename.endsWith('.csv') || filename.endsWith('.json')) {
      return filename;
    } else {
      console.error('Invalid URL: File should be a JSON or CSV file.');
      return null;
    }
  }

  const handleUrlUpload = async () => {
    if (!url) return;

    // Altere a URL do endpoint para corresponder à sua API
    const endpointURL = '/api/v1/horario/uploadUrl';

    try {
      // Envie a URL usando fetch
      const response = await fetch(endpointURL, {
        method: 'POST',
        headers: {
          'Content-Type': 'text/plain',
        },
        body: url,
      });

      const data = await response.text();
      if (response.ok) {
          console.log(data);
          alert(data);
        } else {
          console.error(data);
          alert(data);
        }
    } catch (error) {
      console.error('Erro ao enviar a URL', error);
      alert('Erro ao enviar a URL');
    }
  };

  return (
    <div>
      <h1>URL Upload</h1>
      <TextField
        label="URL"
        variant="outlined"
        fullWidth
        value={url}
        onChange={handleUrlChange}
        style={{ marginBottom: '1rem' }}
      />
      <Button variant="contained" color="secondary" onClick={handleUrlUpload}>
        Send
      </Button>
    </div>
  );
};

export default UrlUpload;