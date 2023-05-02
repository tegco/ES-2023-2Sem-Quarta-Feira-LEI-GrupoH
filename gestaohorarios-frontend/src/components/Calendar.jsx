import React, { useState, useEffect } from 'react';
import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import listPlugin from '@fullcalendar/list';
import { csvParse } from 'd3-dsv';
import CalendarContext from './CalendarContext';

const Calendar = () => {
  const [events, setEvents] = useState([]);

  // Função para processar os dados CSV e criar eventos de calendário
  const processCSVData = (csvData) => {
    const parsedData = [
        { title: 'Aula de Programação', start: '2023-04-26T10:00:00', end: '2023-04-26T11:30:00' }
    ] //csvParse(csvData)
    const calendarEvents = parsedData.map((event) => {
      return {
        title: event['title'],
        start: event['start'],
        end: event['end'],
      };
    });

    setEvents(calendarEvents);
  };

  const contextValue = {
    processCSVData,
  };

  // Função para buscar os dados CSV do backend
  /*const fetchCSVData = async () => {
    try {
      const response = await fetch('api/' + FILE);
      const csvData = await response.text();
      const parsedData = csvParse(csvData);

      processCSVData(parsedData);
    } catch (error) {
      console.error('Error fetching CSV data:', error);
    }
  };*/

  // Carrega os dados do CSV ao carregar o componente
  useEffect(() => {
    processCSVData();
  }, []);

  return (
    <CalendarContext.Provider value={contextValue}>
        <div style={{ padding: '2rem' }}>
        <FullCalendar
            plugins={[dayGridPlugin, timeGridPlugin, listPlugin]}
            headerToolbar={{
                left: 'prev,next today',
                center: 'title',
                right: 'dayGridMonth,timeGridWeek,timeGridDay,listWeek',
            }}
            buttonText={{
                today: 'Hoje',
                month: 'Mês',
                week: 'Semana',
                day: 'Dia',
                list: 'Lista',
            }}
            initialView="dayGridMonth"
            events={events}
            />
        </div>
    </CalendarContext.Provider>
  );
};

export default Calendar;