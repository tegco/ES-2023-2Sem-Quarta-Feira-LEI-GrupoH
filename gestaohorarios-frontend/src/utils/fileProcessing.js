import { dsvFormat } from 'd3-dsv';

export const processFile = async (file, setTempEvents, setCoursesFound, setFileContent) => {
    const reader = new FileReader();
    reader.onload = async (event) => {
        const fileContentRaw = event.target.result;
        const fileContent = fileContentRaw.trim(); // Remove empty lines
        
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

        setFileContent(parsedData);
        
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
                color: (parseInt(event['Inscritos no turno']) > 0 && parseInt(event['Lotação da sala']) > 0 && parseInt(event['Inscritos no turno']) > parseInt(event['Lotação da sala'])) ? '#B00020' : '#1976D2',
            };
        }).filter((event) => event !== null);

        const titlesSet = new Set();
        calendarEvents.forEach(event => {
            titlesSet.add(event.title);
        });
        const titlesArray = Array.from(titlesSet);
        setCoursesFound(titlesArray);
        
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

export const getFileFromURL = async (url) => {
    try {
        const response = await fetch(url);
        const data = await response.text();
        const filename = getFilenameFromUrl(url);
        if (filename.split('.')[1] === 'json') {
            return new File([data], filename, { type: 'application/json' });
        } else if (filename.split('.')[1] === 'csv') {
            console.log('getFileFromURL: ' + filename);
            return new File([data], filename, { type: 'text/csv' });
        } else {
            console.error('Invalid URL: File should be a JSON or CSV file.');
            return null;
      }
    } catch (error) {
        console.error('Error fetching the file', error);
    }
};


export const displayDataInsideFileObject = (inputFile, callback) => {
    const fileReader = new FileReader();
  
    fileReader.onload = (event) => {
      const fileContent = event.target.result;
      callback(fileContent);
    };
  
    fileReader.onerror = () => {
      console.error("Error reading file");
    };
  
    fileReader.readAsText(inputFile);
};