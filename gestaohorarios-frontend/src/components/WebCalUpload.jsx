import React, { useState } from 'react';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import { processWebCal } from '../utils/webCalProcessing';

const WebCalUpload = (props) => {

  const { setTempEvents, setFileName } = props;

  const [uri, setUri] = useState('');
 
  const handleChange = async (event) => {
    setUri(event.target.value);
  }

  const fetchCalendar = async (uri) => {
    if (!uri) {
      throw new Error('Missing URI parameter');
    }
    
    let httpsUrl = uri.replace('webcal', 'https');
    const response = await fetch(`/icalendar?targetUrl=${encodeURIComponent(httpsUrl)}`);
      
      if (!response.ok) {
        throw new Error(`Error fetching the file: ${response.statusText}`);
      }
      const data = await response.text()
      return new File([data], 'calendar.ics', { type: 'text/calendar' });
    };

  const handleUpload = async (event) => {
    event.preventDefault();
    const file = await fetchCalendar(uri);
    await processWebCal(file, setTempEvents, setFileName);
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
                onChange={handleChange}
                style={{ marginBottom: '16px' }}
            />
            </Box>

            <Box display="flex" alignItems="center" justifyContent="center" style={{ width: '100%' }}>
                <Button variant="contained" color="primary" onClick={handleUpload}>
                    Send
                </Button>
            </Box>
        </Box>
  );
}
  
export default WebCalUpload;

