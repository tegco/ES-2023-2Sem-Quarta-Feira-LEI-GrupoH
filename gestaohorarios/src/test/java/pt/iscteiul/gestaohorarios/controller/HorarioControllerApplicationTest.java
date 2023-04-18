package pt.iscteiul.gestaohorarios.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import pt.iscteiul.gestaohorarios.service.FileManagementService;

class HorarioControllerApplicationTest {
	
	HorarioController controller;
	
	@BeforeEach
    void setUp() {
        controller = new HorarioController(new FileManagementService());
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
    //TODO
    }
    
//    @Test
//    void getCSVFileTest() throws IOException {
//    	ResponseEntity<?> goodResponse = controller.getCSVFile("horario-exemplo-professor.csv");
//    	ResponseEntity<?> badResponse = controller.getCSVFile("NaoExiste.csv");
//    	
//    	assertEquals(HttpStatus.OK,goodResponse.getStatusCode());
//    	assertEquals(MediaType.parseMediaType(MediaType.APPLICATION_OCTET_STREAM_VALUE),goodResponse.getHeaders().getContentType());
//    	
//    	assertEquals(HttpStatus.NOT_FOUND,badResponse.getStatusCode());
//    	assertEquals("File not found",badResponse.getBody());
//    
//    }
    
}
