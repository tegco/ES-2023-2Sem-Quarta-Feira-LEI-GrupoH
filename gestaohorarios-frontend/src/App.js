import React, { useState, useEffect, useRef } from 'react';
import FileUpload from './components/FileUpload';
import UrlUpload from './components/UrlUpload';
import FileDownload from './components/FileDownload';
import Calendar from './components/Calendar';
import Grid from '@mui/material/Grid';
import Container from '@mui/material/Container';
import ShowOnCalendarButton from './components/ShowOnCalendarButton';
import ChooseCourseButton from './components/ChooseCourseButton';
import Typography from '@mui/material/Typography';
import { displayDataInsideFileObject } from './utils/fileProcessing';
import WebCalUpload from './components/WebCalUpload';
import { dsvFormat } from 'd3-dsv';
import { red } from '@mui/material/colors';

function App() {

  const [events, setEvents] = useState([]); // This represents the events that are being shown on the calendar (formatted in a way that fullcalendar module can understand)
  const [tempEvents, setTempEvents] = useState([]); // This represents the events that are ready to be shown but awaits user confirmation
  const [file, setFile] = useState(null); // This represents the file that is being processed
  const [fileName, setFileName] = useState(null); // This represents the name of the file that is being processed
  const [fileContent, setFileContent] = useState(null); // This represents the raw content of the file that is being processed (csv or json) before being parsed.
  const [coursesFound, setCoursesFound] = useState([]); // This represents the courses found in the file that is being processed
  const [coursesSelected, setCoursesSelected] = useState([]); // This represents the courses that the user selected to be shown on the calendar

  useEffect(() => {
    const updateFileWithData = () => {
      let serializedFileContent;
      let fileType;
      if (fileName.endsWith('.json')) {
        serializedFileContent = JSON.stringify(fileContent);
        fileType = 'application/json';
      } else if (fileName.endsWith('.csv')) {
        serializedFileContent = dsvFormat(';').format(fileContent);
        fileType = 'text/csv';
      }
  
      const auxFile = new File([serializedFileContent], fileName, { type: fileType });
      setFile(auxFile);
    };
  
    if (fileContent && fileName) {
      updateFileWithData();
    }
  }, [fileContent, fileName, setFile]);

  useEffect(() => {
    console.log("-----------------------------------");
    console.log("Events: ", events);
    console.log("Temp Events: ", tempEvents);
    console.log("File: ", file);
    if (file) {
      displayDataInsideFileObject(file, (data) => {
        console.log("Data inside File object: ", data);
      });
    }
    console.log("File name: ", fileName);
    console.log("File contents: ", fileContent);
  }, [fileContent, tempEvents, events, file, fileName]);

  useEffect(() => {
    let totalSobrelotacao = 0;
    let totalSobreposicao = 0;
    let textoSobrelotacao = "";
    console.log('Events changed: ', events);
    events.map((event) =>{
      const inscritos = parseInt(event.extendedProps.inscritos);
      const lotacao = parseInt(event.extendedProps.lotacao);
      const inicio = event.start;
      const fim = event.end;
      if(inscritos > lotacao){
        textoSobrelotacao += event.title + " that starts at " + inicio + " and ends at " + fim + " is overcrowded! \n ";
        totalSobrelotacao++;
      } 
    });
    textoSobrelotacao += "Total of classes overcrowded: " + totalSobrelotacao;
     if(totalSobrelotacao > 0){
       document.getElementById('output').innerText = textoSobrelotacao;
    }
  }, [events]);

  return (
    <Container maxWidth="lg">
      <Typography variant="h3" component="h1" align="center" style={{ marginTop: '2rem', marginBottom: '2rem' }}>
        ISCTE Calendar
      </Typography>
      <Grid container spacing={4} justifyContent="center" alignItems="center" style={{ flexWrap: 'nowrap', display: 'flex' }}>
        <Grid item xs={12} sm={4}>
          <FileUpload 
            events={events} setEvents={setEvents}
            setTempEvents={setTempEvents}
            file={file} setFile={setFile}
            fileName={fileName} setFileName={setFileName}
            fileContent={fileContent} setFileContent={setFileContent}
            setCoursesFound={setCoursesFound}
          />
        </Grid>
        <Grid item xs={12} sm={4}>
          <UrlUpload setFileName={setFileName} setTempEvents={setTempEvents} />
        </Grid>
        <Grid item xs={12} sm={4}>
          <FileDownload setTempEvents={setTempEvents} setFileName={setFileName} fileName={fileName} setCoursesFound={setCoursesFound} setFileContent={setFileContent} />
        </Grid>
        <Grid item xs={12} sm={4}>
          <WebCalUpload setTempEvents={setTempEvents} />
        </Grid>
      </Grid>
      <Grid container justifyContent="center" alignItems="center">
        <Grid item>
          <ShowOnCalendarButton setEvents={setEvents} tempEvents={tempEvents} fileName={fileName} />
        </Grid>
        <Grid item>
          <ChooseCourseButton tempEvents={tempEvents} coursesFound={coursesFound} coursesSelected={coursesSelected} setCoursesSelected={setCoursesSelected} setEvents={setEvents} setTempEvents={setTempEvents} fileName={fileName} fileContent={fileContent} setFileContent={setFileContent} setFile={setFile} />
        </Grid>
      </Grid>
      <Grid container justifyContent="center">
        <Grid item xs={12}>
          <Calendar events={events} setEvents={setEvents} />
        </Grid>
      </Grid>
      <div style={{ color: '#730000', backgroundColor: '#ff5f5f' }} id="output"></div>
    </Container>
  );
}

export default App;