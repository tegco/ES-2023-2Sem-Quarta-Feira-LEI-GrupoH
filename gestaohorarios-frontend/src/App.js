import React from 'react';
import TestCommunication from './components/TestCommunication';
import FileUpload from './components/FileUpload';
import UrlUpload from './components/UrlUpload';
import FileDownloadComponent from './components/FileDownload';
import WebCalUpload from './components/WebCalUpload';

function App() {
  return (
    <div className="App">
      {/*<TestCommunication />*/}
      <h1>Carregar Arquivo</h1>
      <FileUpload />
      <h1>Enviar URL</h1>
      <UrlUpload />
      <h1>Guardar ficheiros</h1>
      <FileDownloadComponent />
      <WebCalUpload/>
    </div>
  );
}

export default App;
