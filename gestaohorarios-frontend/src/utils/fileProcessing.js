import { csvParse, dsvFormat } from 'd3-dsv';


export const processFile = async (file, setTempEvents) => {
console.log('processFile: ' + file);

// Read the file and parse based on the format
const reader = new FileReader();
reader.onload = async (event) => {
    const fileContent1 = event.target.result;
    const fileContent = fileContent1.trim();
    
    // Check the file's type (MIME type)
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

