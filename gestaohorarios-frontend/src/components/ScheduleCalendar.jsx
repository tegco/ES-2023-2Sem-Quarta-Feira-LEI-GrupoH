import React, { useState } from 'react';
import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid';
import csv from 'csvtojson';


function ScheduleCalendar() {
  const [events, setEvents] = useState([]);

  const handleFileChange = async (event) => {
    const file = event.target.files[0];
    const text = await file.text();
    const data = await csv().fromString(text);
    setEvents(data.map(formatEvent));
  };

  const formatEvent = (event) => {
    const [dia, mes, ano] = event['Data da aula'].split('/');
    const start = new Date(ano, mes - 1, dia, ...event['Hora iní­cio da aula'].split(':'));
    const end = new Date(ano, mes - 1, dia, ...event['Hora fim da aula'].split(':'));

    return {
      title: event['Unidade Curricular'],
      start,
      end,
    };
  };

  return (
    <>
    <input type="file" accept=".csv" onChange={handleFileChange} />
    <FullCalendar
      plugins={[dayGridPlugin]}
      initialView="dayGridMonth"
      events={events}
    />
    </> 
  );
}

export default ScheduleCalendar;