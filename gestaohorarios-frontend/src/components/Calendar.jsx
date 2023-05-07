import React, { useState, useEffect } from 'react';
import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import listPlugin from '@fullcalendar/list';
import interactionPlugin from '@fullcalendar/interaction';
import multiMonthPlugin from '@fullcalendar/multimonth';
import Popover from '@mui/material/Popover';
import Typography from '@mui/material/Typography';
import CalendarAlert from './CalendarAlert';

const Calendar = (props) => {
  const { events } = props;
  const [selectedEvent, setSelectedEvent] = useState(null);
  const [popoverPosition, setPopoverPosition] = useState({ left: 0, top: 0 });
  const [anchorEl, setAnchorEl] = useState(null);
  const calendarRef = React.createRef();
  const [currentEvents, setCurrentEvents] = useState([]);
  const [overlaping, setOverlaping] = useState(null);
  const [overcrowding, setOvercrowding] = useState(null);

  const handleEventClick = (eventClickInfo) => {
    setSelectedEvent(eventClickInfo.event);
    setPopoverPosition({
      left: eventClickInfo.jsEvent.pageX,
      top: eventClickInfo.jsEvent.pageY,
    });
    setAnchorEl(eventClickInfo.jsEvent.currentTarget);
  };

  const handleClosePopover = () => {
    setSelectedEvent(null);
    setAnchorEl(null);
  };

  const renderEventPopover = () => {
    if (!selectedEvent) return null;

    return (
      <Popover
        open={selectedEvent !== null}
        onClose={handleClosePopover}
        anchorEl={anchorEl}
        anchorReference="anchorPosition"
        anchorPosition={{
          left: popoverPosition.left,
          top: popoverPosition.top,
        }}
        anchorOrigin={{
          vertical: 'bottom',
          horizontal: 'center',
        }}
        transformOrigin={{
          vertical: 'top',
          horizontal: 'center',
        }}
      >
        <Typography
          sx={{ padding: '8px 16px' }}
          variant="h6"
          component="div"
          color="text.primary"
        >
          {selectedEvent.title}
        </Typography>
        <Typography sx={{ padding: '0 16px 8px 16px' }}>
          {selectedEvent.extendedProps.curso && (
            <div>Curso: {selectedEvent.extendedProps.curso}</div>
          )}
          {selectedEvent.extendedProps.turno && (
            <div>Turno: {selectedEvent.extendedProps.turno}</div>
          )}
          {selectedEvent.extendedProps.turma && (
            <div>Turma: {selectedEvent.extendedProps.turma}</div>
          )}
          {selectedEvent.extendedProps.inscritos && (
            <div>Inscritos: {selectedEvent.extendedProps.inscritos}</div>
          )}
          {selectedEvent.extendedProps.sala && (
            <div>Sala: {selectedEvent.extendedProps.sala}</div>
          )}
          {selectedEvent.extendedProps.lotacao && (
            <div>Lotação: {selectedEvent.extendedProps.lotacao}</div>
          )}
        </Typography>
      </Popover>
    );
  };

  function onViewChange() {
    if(calendarRef.current){
      const calendarApi = calendarRef.current.getApi();
      const start = calendarApi.view.currentStart;
      const end = calendarApi.view.currentEnd;
      setCurrentEvents(events.map((event) => {
        const startEvent = new Date(event.start.split("T")[0]);
        const endEvent = new Date(event.end.split("T")[0]);
        if(startEvent > start && end > endEvent){
          return event;
        }
        return [];
      }).filter((event) => event !== undefined));
    }
  };

  useEffect(() => {
    checkForOvercrowdedEvents(currentEvents);
    checkForOverlappingEvents(currentEvents);
  }, [currentEvents]);


  const checkForOvercrowdedEvents = (events) => {
    let totalSobrelotacao = 0;
    let textoSobrelotacao = "";
    events.map((event) => {
      const inscritos = parseInt(event.extendedProps.inscritos);
      const lotacao = parseInt(event.extendedProps.lotacao);
      const inicio = new Date(event.start);
      const fim = new Date(event.end);
      if(inscritos > lotacao){
        textoSobrelotacao += event.title + " that starts at " + stringDate(inicio) + " and ends at " + stringDate(fim) + " is overcrowded! \n";
        totalSobrelotacao++;
      } 
    });
    textoSobrelotacao = "Total of classes overcrowded: " + totalSobrelotacao + "\n" + textoSobrelotacao;
    console.log("Sobrelotacao: " + totalSobrelotacao);
    if(totalSobrelotacao > 0){
      setOvercrowding(textoSobrelotacao);
    }
    else{
      setOvercrowding("");
    }
  }

  const checkForOverlappingEvents = (events) => {
    let totalSobreposicao = 0;
    let textoSobreposicao = "";
    events.map((event, index) => {
      const startTime = new Date(event.start);
      const endTime = new Date(event.end);
      for (let i = index + 1; i < events.length; i++) {
        const otherStartTime = new Date(events[i].start);
        const otherEndTime = new Date(events[i].end);
        if (
          (startTime >= otherStartTime && startTime < otherEndTime) || // aula 1 comeca depois e acaba antes da outra
          (endTime > otherStartTime && endTime <= otherEndTime) ||     // aula 1 acaba depois do comeco da outra e acaba antes da outra 
          (otherStartTime >= startTime && otherStartTime < endTime) || // aula 2 comeca depois do inicio da outra e comeca antes do fim 
          (otherEndTime > startTime && otherEndTime <= endTime)        // aula 2 fim depois do inicio da 1 e termina antes do fim da 1
        ) {
          textoSobreposicao = textoSobreposicao + "The " + event.title + " class that starts at " + stringDate(startTime) + " is overlaping with the " + events[i].title + " class that starts at " + stringDate(otherStartTime) + "\n";
          totalSobreposicao++;
        }
      }
    });
    textoSobreposicao = "The total of classes overlaped is: " + totalSobreposicao + "\n" + textoSobreposicao;
    console.log("Sobreposicao: " + totalSobreposicao);
    if(totalSobreposicao > 0){
      setOverlaping(textoSobreposicao);
    }
    else{
      setOverlaping("");
    }
     
  }

  function stringDate(date){
    const year = date.getFullYear();
    const month = date.getMonth();
    const day = date.getDay();
    const hours = date.getHours();
    const minutes = date.getMinutes();
    if(minutes === 0){
      return year + "/" + month + "/" + day + " " + hours + ":00";
    }
    return year + "/" + month + "/" + day + " " + hours + ":" + minutes;
  }
  
  return (
    
    events && events.length > 0 && (
      <>
        <div style={{ padding: '2rem' }} >
          <FullCalendar
            ref={ calendarRef }
            plugins={[dayGridPlugin, timeGridPlugin, listPlugin, multiMonthPlugin, interactionPlugin]}
            headerToolbar={{
              left: 'prev,next today',
              center: 'title',
              right: 'dayGridMonth,timeGridWeek,timeGridDay,listWeek,multiMonthYear',
            }}
            buttonText={{
              day: 'Dia',
              week: 'Semana',
              month: 'Mês',
              today: 'Hoje',
              list: 'Lista',
              multiMonthYear: 'Ano',
            }}
            datesSet={onViewChange}
            initialView="dayGridMonth"
            events={events}
            eventClick={handleEventClick}
          />
        </div>
        {renderEventPopover()}
        <CalendarAlert overlapings={ overlaping } overcrowdings={ overcrowding }/>
      </>
    )
    
    
  );
};

export default Calendar;