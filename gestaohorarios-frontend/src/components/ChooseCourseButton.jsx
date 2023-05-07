import React, { useState, useEffect } from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import FormGroup from '@mui/material/FormGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';
import Snackbar from '@mui/material/Snackbar';
import Alert from '@mui/material/Alert';
import { csvFormat } from 'd3-dsv';

const ChooseCourseButton = (props) => {

  const { tempEvents, coursesFound, coursesSelected, setCoursesSelected, setFileContent, fileContent, setTempEvents, fileName, setFile } = props;

  const [openDialog, setOpenDialog] = useState(false);
  const [warnToChooseCoursesSnackbarOpen, setWarnToChooseCoursesSnackbarOpen] = useState(false);
  const [selectedCoursesSnackbarOpen, setSelectedCoursesSnackbarOpen] = useState(false);

  useEffect(() => {
    if (tempEvents && tempEvents.length > 0) {
      setWarnToChooseCoursesSnackbarOpen(true);
    }
  }, [tempEvents]);

  const handleClickOpen = () => {
    setOpenDialog(true);
  };

  const closeDialog = () => {
    setOpenDialog(false);
  };

  const applyAndClose = () => {
    console.log('Courses selected: ' + coursesSelected);
    updateFileContentWithSpecificCourses();
    updateTempEventsWithSpecificCourses();
  
    setSelectedCoursesSnackbarOpen(true);
    setOpenDialog(false);
  };

  const updateFileContentWithSpecificCourses = () => {
    if (fileName.endsWith('.json')) {
      const newFileContent = fileContent.filter((row) => coursesSelected.includes(row['Unidade Curricular']));
      setFileContent(newFileContent);
    } else if (fileName.endsWith('.csv')) {
      console.log('fileContent before filtering:', fileContent);
      const newFileContent = fileContent.filter((row) => coursesSelected.includes(row['Unidade Curricular']));
      newFileContent.columns = fileContent.columns;
      console.log('fileContent after filtering:', newFileContent);
      setFileContent(newFileContent);
    }
  };

  const updateTempEventsWithSpecificCourses = () => {
    const newTempEvents = tempEvents.filter((event) => coursesSelected.includes(event.title));
    setTempEvents(newTempEvents);
  };

  const handleWarnToChooseCoursesSnackbarClose = () => {
    setWarnToChooseCoursesSnackbarOpen(false);
  };

  const handleSelectedCoursesSnackbarClose = () => {
    setSelectedCoursesSnackbarOpen(false);
  };

  const handleCheckboxChange = (event) => {
    if (event.target.checked) {
      setCoursesSelected([...coursesSelected, event.target.name]);
    } else {
      setCoursesSelected(coursesSelected.filter((course) => course !== event.target.name));
    }
  };

  return (
    tempEvents && tempEvents.length > 0 && (
      <>
        <Button onClick={handleClickOpen} variant="outlined">
          Choose Specific Courses
        </Button>
        <Dialog open={openDialog} onClose={closeDialog}>
          <DialogTitle>Choose Courses</DialogTitle>
          <DialogContent>
            <FormGroup>
              {coursesFound.map((course) => (
                <FormControlLabel
                  key={course}
                  control={
                    <Checkbox
                      checked={coursesSelected.includes(course)}
                      onChange={handleCheckboxChange}
                      name={course}
                    />
                  }
                  label={course}
                />
              ))}
            </FormGroup>
          </DialogContent>
          <DialogActions>
            <Button onClick={applyAndClose}>Apply Changes</Button>
          </DialogActions>
        </Dialog>
        <Snackbar
          open={warnToChooseCoursesSnackbarOpen}
          autoHideDuration={6000}
          onClose={handleWarnToChooseCoursesSnackbarClose}
          anchorOrigin={{ vertical: 'bottom', horizontal: 'left' }}
        >
          <Alert onClose={handleWarnToChooseCoursesSnackbarClose} severity="info" sx={{ width: '100%' }}>
            You can choose specific courses to display!
          </Alert>
        </Snackbar>
        <Snackbar
          open={selectedCoursesSnackbarOpen}
          autoHideDuration={6000}
          onClose={handleSelectedCoursesSnackbarClose}
          anchorOrigin={{ vertical: 'bottom', horizontal: 'left' }}
        >
          <Alert onClose={handleSelectedCoursesSnackbarClose} severity="success" sx={{ width: '100%' }}>
            Courses selected: {coursesSelected.join(', ')}
          </Alert>
        </Snackbar>
      </>
    )
  );
};

export default ChooseCourseButton;