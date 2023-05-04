import React, { useState } from 'react';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';

const FileDownload = () => {
  const [fileName, setFileName] = useState("");

  const handleDownload = async () => {
    try {
      const response = await fetch(`/api/v1/horario/downloadFile/${fileName}`);

      if (!response.ok) {
        console.error("Error downloading the file.");
        alert("Error downloading the file.");
        return;
      }

      const data = await response.blob();
      const url = window.URL.createObjectURL(data);
      const link = document.createElement("a");
      link.href = url;
      link.setAttribute("download", fileName);
      document.body.appendChild(link);
      link.click();
      link.remove();
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <Box>
      <Typography variant="h4" component="h1" style={{ marginTop: '1rem', marginBottom: '2rem' }}>
        File Download
      </Typography>

      <TextField
        label="File Name"
        variant="outlined"
        fullWidth
        value={fileName}
        onChange={(e) => setFileName(e.target.value)}
        inputProps={{ pattern: ".*\\.(csv|json)" }}
        placeholder="Enter file name (e.g., file.csv)"
        style={{ marginBottom: '1rem' }} 
      />

      <Box display="flex" alignItems="center" justifyContent="center" style={{ width: '100%' }}>
        <Button variant="contained" color="primary" onClick={handleDownload}>
          Download
        </Button>
      </Box>
    </Box>
  );
};

export default FileDownload;