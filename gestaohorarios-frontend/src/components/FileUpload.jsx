import React, { useState, useEffect } from 'react';
import Button from '@mui/material/Button';
import Input from '@mui/material/Input';
import { csvParse } from 'd3-dsv';
import { processFile } from '../utils/fileProcessing';

const FileUpload = (props) => {

  const { events, setEvents, tempEvents, setTempEvents, setFileName } = props;

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
  };


  const handleUpload = async () => {
    if (!file) return;

    // Crie uma instância FormData para armazenar o arquivo
    const formData = new FormData();
    formData.append('file', file);

    // Altere a URL do endpoint para corresponder à sua API
    const endpointURL = '/api/v1/horario/uploadFile';

    try {
        // Envie o arquivo usando fetch
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
    <div>
      <h1>File Upload</h1>
      <Input
        type="file"
        onChange={handleChange}
        inputProps={{ accept: '.csv,.json' }}
      />
      <Button variant="contained" color="primary" onClick={handleUpload}>
        Send
      </Button>
    </div>
  );
};

export default FileUpload;