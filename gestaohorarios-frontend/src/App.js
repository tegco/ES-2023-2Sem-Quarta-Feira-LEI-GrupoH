import TestCommunication from './components/TestCommunication';
import FileUpload from './components/FileUpload';
import UrlUpload from './components/UrlUpload';

function App() {
  return (
    <div className="App">
      {/*<TestCommunication />*/}
      <h1>Carregar Arquivo</h1>
      <FileUpload />
      <h1>Enviar URL</h1>
      <UrlUpload />
    </div>
  );
}

export default App;
