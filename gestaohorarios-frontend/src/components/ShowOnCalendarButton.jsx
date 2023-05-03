// ShowOnCalendarButton.js
import React from 'react';
import Button from '@mui/material/Button';

const ShowOnCalendarButton = ({ disabled, setEvents, tempEvents, fileName }) => {
  const handleClick = () => {
    alert('Button clicked');
    setEvents(tempEvents);
  };

  return (
    <Button
      variant="contained"
      color="secondary"
      size="large"
      onClick={handleClick}
      disabled={disabled}
      sx={{
        fontWeight: 'bold',
        '&:hover': {
          backgroundColor: '#ff8a8a',
        },
      }}
    >
      Show on Calendar: {fileName}
    </Button>
  );
};

export default ShowOnCalendarButton;