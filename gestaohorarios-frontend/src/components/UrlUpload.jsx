import React, { useState } from 'react';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';

const UrlUpload = () => {
  const [url, setUrl] = useState('');

  const handleUrlChange = (e) => {
    setUrl(e.target.value);
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
      console.log('-----------------');
      console.log(response);
      console.log('-----------------');
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
      <h1>Enviar URL</h1>
      <TextField
        label="URL"
        variant="outlined"
        fullWidth
        value={url}
        onChange={handleUrlChange}
        style={{ marginBottom: '1rem' }}
      />
      <Button variant="contained" color="secondary" onClick={handleUrlUpload}>
        Enviar URL
      </Button>
    </div>
  );
};

export default UrlUpload;