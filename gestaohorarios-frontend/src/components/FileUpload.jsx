import React, { useState } from 'react';
import Button from '@mui/material/Button';
import Input from '@mui/material/Input';

const FileUpload = () => {
  const [file, setFile] = useState(null);

  const handleChange = (e) => {
    setFile(e.target.files[0]);
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
      <Input
        type="file"
        onChange={handleChange}
        inputProps={{ accept: '.csv,.json' }}
        style={{ marginBottom: '1rem' }}
      />
      <Button variant="contained" color="primary" onClick={handleUpload}>
        Enviar
      </Button>
    </div>
  );
};

export default FileUpload;