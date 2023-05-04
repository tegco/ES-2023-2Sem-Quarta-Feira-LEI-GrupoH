import React, { useState } from 'react';
import Button from '@mui/material/Button';
import Input from '@mui/material/Input';
import Box from '@mui/material/Box';
import InputLabel from '@mui/material/InputLabel';
import Typography from '@mui/material/Typography';
import { processFile } from '../utils/fileProcessing';


const FileUpload = (props) => {

  const { tempEvents, setTempEvents, setFileName } = props;

  const [file, setFile] = useState(null);

  const handleChange = async (e) => {
    const selectedFile = e.target.files[0];
    if (selectedFile) {
      setFileName(selectedFile.name);
    } else {
      setFileName('');
    }
    setFile(selectedFile);
    console.log('handleChange: ' + selectedFile);
    await processFile(selectedFile, setTempEvents);
    console.log("Temporary events " + tempEvents);
  };


  const handleUpload = async () => {
    if (!file) return;

    const formData = new FormData();
    formData.append('file', file);

    const endpointURL = '/api/v1/horario/uploadFile';

    try {
        const response = await fetch(endpointURL, {
          method: 'POST',
          body: formData,
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
        console.error('Erro ao enviar o arquivo', error);
        alert('Erro ao enviar o arquivo');
      }
    };

    return (
      <Box>
        <Typography variant="h4" component="h1" style={{ marginTop: '1rem', marginBottom: '2rem' }}>
          File Upload
        </Typography>
  
        <InputLabel htmlFor="upload-file">Select a file to upload:</InputLabel>
        <Input
          type="file"
          id="upload-file"
          onChange={handleChange}
          inputProps={{ accept: '.csv,.json' }}
          style={{ marginBottom: '16px' }}
        />
  
        <Box display="flex" alignItems="center" justifyContent="center" style={{ width: '100%' }}>
          <Button variant="contained" color="primary" onClick={handleUpload}>
            Send
          </Button>
        </Box>
      </Box>
    );
};

export default FileUpload;