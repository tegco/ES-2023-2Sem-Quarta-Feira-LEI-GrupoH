import React, { useState } from 'react';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';

const WebCalUpload = () => {
  const [uri, setUri] = useState('');
 
  const handleWebcalChange= async (event) => {
    setUri(event.target.value);
  };

  const handleWebCalUpload = async (event) => {
    event.preventDefault();
}

    return (
        <Box>
            <Typography variant="h4" component="h1" style={{ marginTop: '1rem', marginBottom: '2rem' }}>
                Web Calendar Upload
            </Typography>
            
            <Box sx={{
                width: 650,
                maxWidth: '95%',
            }}
            >
            <TextField
                label="Web Calendar URL"
                placeholder='Enter web calendar URL'
                variant="outlined"
                size="small"
                fullWidth
                value={uri}
                onChange={handleWebcalChange}
                style={{ marginBottom: '16px' }}
            />
            </Box>

            <Box display="flex" alignItems="center" justifyContent="center" style={{ width: '100%' }}>
                <Button variant="contained" color="primary" onClick={handleWebCalUpload}>
                    Send
                </Button>
            </Box>
        </Box>
  );
}
  
export default WebCalUpload;

