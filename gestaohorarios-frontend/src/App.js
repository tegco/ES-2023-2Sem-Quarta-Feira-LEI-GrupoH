import React from 'react';
import TestCommunication from './components/TestCommunication';
import FileUpload from './components/FileUpload';
import UrlUpload from './components/UrlUpload';
import FileDownload from './components/FileDownload';
import Calendar from './components/Calendar';

function App() {
  return (
    <div className="App">
      {/*<TestCommunication />*/}
      <Calendar />
      <FileUpload />
      <UrlUpload />
      <FileDownload />
    </div>
  );
}

export default App;
