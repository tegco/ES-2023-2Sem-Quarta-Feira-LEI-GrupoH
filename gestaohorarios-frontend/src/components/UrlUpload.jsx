import React, { useState } from 'react';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import { processFile } from '../utils/fileProcessing';
import { getFilenameFromUrl } from '../utils/fileProcessing';
import { getFileFromURL } from '../utils/fileProcessing';

const UrlUpload = (props) => {

  const { setFileName, setTempEvents, setCoursesFound, setFileContent } = props;

  const [url, setUrl] = useState('');

  const handleUrlChange = async (e) => {
    setUrl(e.target.value);
    
    setFileName(getFilenameFromUrl(e.target.value));
    const file = await getFileFromURL(e.target.value);
    await processFile(file, setTempEvents, setCoursesFound, setFileContent);
  };

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
        size="small"
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