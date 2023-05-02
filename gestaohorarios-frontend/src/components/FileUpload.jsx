import React, { useState, useContext } from 'react';
import Button from '@mui/material/Button';
import Input from '@mui/material/Input';
import CalendarContext from './CalendarContext';

const FileUpload = () => {
  const [file, setFile] = useState(null);
  const { processCSVData } = useContext(CalendarContext);

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

    const handleUpdateCalendar = () => {
      const csvData = '';
      processCSVData(csvData);
    };

  return (
    <div>
      <h1>Carregar Arquivo</h1>
      <Input
        type="file"
        onChange={handleChange}
        inputProps={{ accept: '.csv,.json' }}
        style={{ marginBottom: '1rem' }}
      />
      <Button variant="contained" color="primary" onClick={handleUpload}>
        Enviar
      </Button>
      {file && <Button variant="contained" color="secondary" onClick={handleUpdateCalendar}>
        Mostrar no Calendário
      </Button>}
    </div>
  );
};

export default FileUpload;