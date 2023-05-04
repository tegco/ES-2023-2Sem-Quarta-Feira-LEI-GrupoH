import React from 'react';
import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import listPlugin from '@fullcalendar/list';
import multiMonthPlugin from '@fullcalendar/multimonth';

const Calendar = (props) => {

  const { events } = props;

  return (
    events && events.length > 0 && (
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
        />
      </div>
    )
  );
};

export default Calendar;