package pt.iscteiul.gestaohorarios.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.server.ResponseStatusException;

import pt.iscteiul.gestaohorarios.service.FileManagementService;

class HorarioControllerApplicationTest {
	
	HorarioController controller;
	
	@BeforeEach
    void setUp() {
        controller = new HorarioController(new FileManagementService());
        
		List<File> files = new ArrayList<>();
		files.add(new File("data/horarios/json/Test-horario-exemplo-professor.json"));
		files.add(new File("data/horarios/csv/Test-horario-exemplo-professor.csv"));
		files.add(new File("data/horarios/json/BadFileTest.json"));
		files.add(new File("data/horarios/csv/BadFileTest.csv"));
		files.add(new File("data/horarios/csv/urltest.csv"));
		files.add(new File("data/horarios/json/urltest.json"));
		for (File file : files)
			if (file.exists())
				file.delete();
    }
	
    @Test
    void uploadFicheiroJSONTest() throws IOException {
        File file = new File("data/horarios/json/horario-exemplo-professor.json");
        System.out.println(file.getName());
        
        FileInputStream inputStream = new FileInputStream(file);
        
        MockMultipartFile goodFile = new MockMultipartFile("file",file.getName(), "text/json", inputStream);
        MockMultipartFile badFile = new MockMultipartFile("file","BadFileTest.json", "text/json", "a".getBytes());
        
        ResponseEntity<String> goodResponse = controller.uploadFicheiro(goodFile);
        ResponseEntity<String> badResponse = controller.uploadFicheiro(badFile);
        
        assertEquals(HttpStatus.OK, goodResponse.getStatusCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, badResponse.getStatusCode());
        
        
        assertEquals("File " + file.getName() + " received successfully", goodResponse.getBody());
        assertEquals("The server had trouble saving your file", badResponse.getBody());
    }
    
    @Test
    void uploadFicheiroCSVTest() throws IOException {
        File file = new File("data/horarios/csv/horario-exemplo-professor.csv");
        System.out.println(file.getName());
        
        FileInputStream inputStream = new FileInputStream(file);
        MockMultipartFile goodFile = new MockMultipartFile("file",file.getName(), "text/csv", inputStream);
        MockMultipartFile badFile = new MockMultipartFile("file","BadFileTest.csv", "text/csv", "\"married\": true,".getBytes());

        ResponseEntity<String> goodResponse = controller.uploadFicheiro(goodFile);
        ResponseEntity<String> badResponse = controller.uploadFicheiro(badFile);

        assertEquals(HttpStatus.OK, goodResponse.getStatusCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, badResponse.getStatusCode());
        
        assertEquals("File " + file.getName() + " received successfully", goodResponse.getBody());
        assertEquals("The server had trouble saving your file", badResponse.getBody());
    }
    
    
    @Test
    void uploadURLTest() throws IOException {
    	 ResponseEntity<String> response = controller.uploadURL("https://raw.githubusercontent.com/malca1-iscte/urltest/main/urltest.csv");
    	 
    	 assertEquals(HttpStatus.OK,response.getStatusCode());
    	 assertEquals("file url received successfully",response.getBody());
    }
    
    @Test
    void getCSVFileTest() throws IOException {
    	ResponseEntity<?> goodResponse = controller.getCSVFile("horario-exemplo-professor.csv");
    	
    	assertEquals(HttpStatus.OK,goodResponse.getStatusCode());
    	assertEquals(MediaType.parseMediaType(MediaType.APPLICATION_OCTET_STREAM_VALUE),goodResponse.getHeaders().getContentType());
    	
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> controller.getCSVFile("NaoExiste.csv"));
        System.out.println(exception.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    	assertEquals("File not Found",exception.getBody().getDetail());
    
    }
    
}
