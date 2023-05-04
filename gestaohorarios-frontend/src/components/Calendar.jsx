import React, { useState, useEffect, useRef } from 'react';
import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import listPlugin from '@fullcalendar/list';
import multiMonthPlugin from '@fullcalendar/multimonth';
import Popover from '@mui/material/Popover';
import Typography from '@mui/material/Typography';

const Calendar = (props) => {
  const { events } = props;
  const [selectedEvent, setSelectedEvent] = useState(null);
  const [popoverPosition, setPopoverPosition] = useState({ left: 0, top: 0 });
  const [anchorEl, setAnchorEl] = useState(null);

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

  return (
    events && events.length > 0 && (
      <>
        <div style={{ padding: '2rem' }}>
          <FullCalendar
            plugins={[dayGridPlugin, timeGridPlugin, listPlugin, multiMonthPlugin]}
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
            initialView="dayGridMonth"
            events={events}
            eventClick={handleEventClick}
          />
        </div>
        {renderEventPopover()}
      </>
    )
  );
};

export default Calendar;