import React from 'react';
import Button from '@mui/material/Button';

const ShowOnCalendarButton = (props) => {

  const { setEvents, tempEvents, fileName } = props;

  const shouldButtonBeDisabled = !tempEvents || tempEvents.length === 0;

  const handleClick = () => {
    setEvents(tempEvents);
  };

  return (
    <Button
      variant="contained"
      color="secondary"
      size="large"
      onClick={handleClick}
      disabled={shouldButtonBeDisabled}
      sx={{
        fontWeight: 'bold',
        fontSize: '1.25rem',
        padding: '0.75rem 1rem',
        marginTop: '2rem',
        '&:hover': {
          backgroundColor: '#ff8a8a',
        },
        '&.Mui-disabled': {
          backgroundColor: 'rgba(0, 0, 0, 0.12)',
        },
      }}
    >
      Show on Calendar: {fileName || 'No file selected'} 
    </Button>
  );
};

export default ShowOnCalendarButton;