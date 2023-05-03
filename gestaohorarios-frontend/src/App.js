import React, { useState, useEffect } from 'react';
import FileUpload from './components/FileUpload';
import UrlUpload from './components/UrlUpload';
import FileDownload from './components/FileDownload';
import Calendar from './components/Calendar';
import Grid from '@mui/material/Grid';
import Container from '@mui/material/Container';
import ShowOnCalendarButton from './components/ShowOnCalendarButton';

function App() {

  const [events, setEvents] = useState([]);
  const [tempEvents, setTempEvents] = useState([]);
  const [fileName, setFileName] = useState(null);

  useEffect(() => {
    console.log('Events changed: ', events);
  }, [events]);

  const shouldButtonBeDisabled = !tempEvents || tempEvents.length === 0;

  return (
    <Container maxWidth="lg">
      <Grid container spacing={4} justifyContent="center" alignItems="center">
        <Grid item xs={12} sm={4}>
          <FileUpload events={events} setEvents={setEvents} tempEvents={tempEvents} setTempEvents={setTempEvents} setFileName={setFileName} />
        </Grid>
        <Grid item xs={12} sm={4}>
          <UrlUpload />
        </Grid>
        <Grid item xs={12} sm={4}>
          <FileDownload />
        </Grid>
      </Grid>
      <Grid container justifyContent="center" alignItems="center">
        <Grid item>
          <ShowOnCalendarButton disabled={shouldButtonBeDisabled} setEvents={setEvents} tempEvents={tempEvents} fileName={fileName} />
        </Grid>
      </Grid>
      <Grid container justifyContent="center">
        <Grid item xs={12}>
          <Calendar events={events} setEvents={setEvents} />
        </Grid>
      </Grid>
    </Container>
  );
}

export default App;
