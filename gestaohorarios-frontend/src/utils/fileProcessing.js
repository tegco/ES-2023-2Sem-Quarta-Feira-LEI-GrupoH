import { dsvFormat } from 'd3-dsv';


export const processFile = async (file, setTempEvents) => {
const reader = new FileReader();
reader.onload = async (event) => {
    const fileContent1 = event.target.result;
    const fileContent = fileContent1.trim();
    
    const isJSON = file.type === 'application/json';
    const isCSV = file.type === 'text/csv';
    
    let parsedData;

    if (isJSON) {
    parsedData = JSON.parse(fileContent);
    } else if (isCSV) {
        parsedData = dsvFormat(';').parse(fileContent);   
    } else {
    console.error('Unsupported file format.');
    alert('Unsupported file format. Please upload a JSON or CSV file.');
    return;
    }

    const calendarEvents = parsedData.map((event) => {
        if (event['Data da aula'] === undefined || event['Hora início da aula'] === undefined || event['Hora fim da aula'] === undefined) return null;
            return {
                title: event['Unidade Curricular'],
                start: convertDateFormat(event['Data da aula']) + 'T' + event['Hora início da aula'],
                end: convertDateFormat(event['Data da aula']) + 'T' + event['Hora fim da aula'],
                extendedProps: {
                curso: event['Curso'],
                turno: event['Turno'],
                turma: event['Turma'],
                inscritos: event['Inscritos no turno'],
                diaSemana: event['Dia da semana'],
                dataAula: event['Data da aula'],
                sala: event['Sala atribuída à aula'],
                lotacao: event['Lotação da sala'],
                },
            };
        }).filter((event) => event !== null);
    setTempEvents(calendarEvents);
};

reader.readAsText(file);
};

const convertDateFormat = (date) => {
    const [day, month, year] = date.split('/');
    return `${year}-${month}-${day}`;
}

export const getFilenameFromUrl = (url) => {
    const parts = url.split('/');
    const filename = parts[parts.length - 1];
    if (filename.endsWith('.csv') || filename.endsWith('.json')) {
      return filename;
    } else {
      console.error('Invalid URL: File should be a JSON or CSV file.');
      return null;
    }
}