import React, {useState, useEffect} from 'react';
import Alert from '@mui/material/Alert';
import { Warning, Error } from '@mui/icons-material';



const CalendarAlert = ({overlapings, overcrowdings}) => {
    
    const [overlapinglines, setOverlapingLines] = useState([]);
    const [overcrowdinglines, setOvercrowdingLines] = useState([]);

    useEffect(() => {
        const splittedLines = overlapings.split('\n');
        setOverlapingLines(splittedLines);
    }, [overlapings]);

    useEffect(() => {
        const splittedLines = overcrowdings.split('\n');
        setOvercrowdingLines(splittedLines);
    }, [overcrowdings]);
    
  return(
        <div>
            {overlapings && (
            <Alert severity="warning" variant="filled">
                <h3>Classes Overlaped</h3>
                {overlapinglines.map((line, index) => (
                    <div key={index}>{line}</div>
                ))}
            </Alert>
            )}
            {overcrowdings && (
            <Alert severity="error" variant="filled">
                <h3>Classes Overcrowded </h3>
                {overcrowdinglines.map((line, index) => (
                    <div key={index}>{line}</div>
                ))}
            </Alert>
            )}
        </div>  
    )
 
}

export default CalendarAlert;





