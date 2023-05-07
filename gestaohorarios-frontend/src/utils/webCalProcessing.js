import ical from "ical.js";

export const processWebCal = async (file, setTempEvents, setFileName) => {
    console.log('File name:', file.name);
    setFileName(file.name);
    console.log('File type:', file.type);

    const reader = new FileReader();

    reader.onload = () => {
        const data = reader.result;
     
        const parsedCalendar = ical.parse(data);
        const comp = new ical.Component(parsedCalendar);
        const events = comp.getAllSubcomponents('vevent');

        const calendarEvents = [];

        events.forEach((event) => {
            const description = event.getFirstPropertyValue('description');
            const location = event.getFirstPropertyValue('location');
            //console.log(`Description: ${description}, Location: ${location}`);

            const [portugues, ingles] = description.split('\n\n')
            const [semestre, unidadeCurricular, codigo, turno, inicio, fim, docente] = portugues.split('\n')
            const[sala, andar, edificio, local] = location.split('\, ')
          
            const evento_tratado =  {
            title: unidadeCurricular.split(': ')[1],
            start: inicio.split(': ')[1].split(' ')[0] + 'T' + inicio.split(': ')[1].split(' ')[1] + ':00',
            end: fim.split(': ')[1].split(' ')[0] + 'T' + inicio.split(': ')[1].split(' ')[1] + ':00',
            extendedProps: {
                curso: undefined ,
                turno: turno.split(': ')[1],
                sala: sala,
                lotacao: ""
                },
            }
            calendarEvents.push(evento_tratado);
            console.log(evento_tratado)
          });
       
        setTempEvents(calendarEvents);
    }
    reader.readAsText(file);
}