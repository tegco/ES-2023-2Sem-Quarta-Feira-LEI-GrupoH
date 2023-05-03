import React, { useState } from 'react';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';

const UrlUpload = () => {
  const [url, setUrl] = useState('');

  const handleUrlChange = (e) => {
    setUrl(e.target.value);
    getFileFromURL(e.target.value);
  };

  const getFileFromURL = async (url) => {
    try {
      const response = await fetch(url);
      const data = await response.text();
      console.log(data);
      return data;
    } catch (error) {
      console.error('Erro ao obter o arquivo', error);
    }
  };

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