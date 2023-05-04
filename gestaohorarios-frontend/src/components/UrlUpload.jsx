import React, { useState } from 'react';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
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

    const endpointURL = '/api/v1/horario/uploadUrl';

    try {
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
    <Box>
      <Typography variant="h4" component="h1" style={{ marginTop: '1rem', marginBottom: '2rem' }}>
        URL Upload
      </Typography>

      <TextField
        label="URL"
        variant="outlined"
        fullWidth
        value={url}
        onChange={handleUrlChange}
        style={{ marginBottom: '1rem' }}
      />

      <Box display="flex" alignItems="center" justifyContent="center" style={{ width: '100%' }}>
        <Button variant="contained" color="primary" onClick={handleUrlUpload}>
          Send
        </Button>
      </Box>
    </Box>
  );
};

export default UrlUpload;