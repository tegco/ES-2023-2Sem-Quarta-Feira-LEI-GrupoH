import TestCommunication from './components/TestCommunication';
import FileUpload from './components/FileUpload';
import UrlUpload from './components/UrlUpload';
import FileDownloadComponent from './components/FileDownload';
import ScheduleCalendar from './components/ScheduleCalendar';

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
      <h1>Representação</h1>
      <ScheduleCalendar />
    </div>
  );
}

export default App;
