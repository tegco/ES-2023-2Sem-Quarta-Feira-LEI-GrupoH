import React from 'react';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import { processFile } from '../utils/fileProcessing';

const FileDownload = (props) => {
  
  const { setTempEvents, setFileName, fileName, setCoursesFound, setFileContent } = props;

  const handleDownload = async (fileType) => {
    let newName = fileName.split(".")[0] + "." + fileType;
    try {
      const response = await fetch(`/api/v1/horario/downloadFile/${newName}`);

      if (!response.ok) {
        console.error("Error downloading the file.");
        alert("Error downloading the file.");
        return;
      }

      const data = await response.text();
      const fileType = newName.endsWith('.json') ? 'application/json' : 'text/csv';

      const file = new File([data], newName, { type: fileType });
      await processFile(file, setTempEvents, setCoursesFound, setFileContent);

      const url = window.URL.createObjectURL(file);
      const link = document.createElement("a");
      link.href = url;
      link.setAttribute("download", newName);
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

      {/* <TextField
        label="File Name"
        variant="outlined"
        size="small"
        fullWidth
        onChange={(e) => setFileName(e.target.value)}
        inputProps={{ pattern: ".*\\.(csv|json)" }}
        placeholder="Enter file name (e.g., file.csv)"
        style={{ marginBottom: '1rem' }} 
      /> */}

      <Box display="flex" alignItems="center" justifyContent="center" style={{ width: '100%' }}>
        <Button variant="contained" color="primary" onClick={() => handleDownload('json')} disabled={!fileName} sx={{margin: 1 }}>
          Download JSON
        </Button>
        <Button variant="contained" color="primary" onClick={() => handleDownload('csv')} disabled={!fileName}>
          Download CSV
        </Button>
      </Box>
    </Box>
  );
};

export default FileDownload;