package pt.iscteiul.gestaohorarios.service;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

class FileManagementServiceApplicationTest {
	@BeforeEach
	void setUp() throws Exception {
		List<File> files = new ArrayList<>();
		files.add(new File("data/horarios/json/Test-horario-exemplo-professor.json"));
		files.add(new File("data/horarios/csv/Test-horario-exemplo-professor.csv"));
		//files.add(new File("data/horarios/csv/addresses.csv"));
		for (File file : files)
			if (file.exists())
				file.delete();
	}

	@Test
	void uploadFileJSONTest() throws IOException {
		File file = new File("data/horarios/json/horario-exemplo-professor.json");
        
        FileInputStream inputStream = new FileInputStream(file);
        
        MockMultipartFile mFile = new MockMultipartFile("file","Test-" + file.getName(), "text/json", inputStream);
        assertFalse(Files.exists(Paths.get("data/horarios/json/Test-horario-exemplo-professor.json")));
        boolean b = (new FileManagementService()).uploadFile(mFile);
        assertTrue(Files.exists(Paths.get("data/horarios/json/Test-horario-exemplo-professor.json")));
		assertTrue(b);
	}
	
	@Test
	void uploadFileCSVTest() throws IOException {
		File file = new File("data/horarios/csv/horario-exemplo-professor.csv");
        
        FileInputStream inputStream = new FileInputStream(file);
        
        MockMultipartFile mFile = new MockMultipartFile("file","Test-" + file.getName(), "text/csv", inputStream);
        assertFalse(Files.exists(Paths.get("data/horarios/csv/Test-horario-exemplo-professor.csv")));
        boolean b = (new FileManagementService()).uploadFile(mFile);
        assertTrue(Files.exists(Paths.get("data/horarios/csv/Test-horario-exemplo-professor.csv")));
		assertTrue(b);
	}
	
	@Test
	void uploadFileWrongTypeTest() throws IOException {
        MockMultipartFile mFile = new MockMultipartFile("file","Test-Wrong-horario-exemplo-professor.jpg", "text/plain", "a".getBytes());
        boolean b = (new FileManagementService()).uploadFile(mFile);
		assertFalse(b);
	}
	
	

	@Test
	void uploadFileUsingURLCSVTest() throws MalformedURLException {
		assertFalse(Files.exists(Paths.get("data/horarios/csv/addresses.csv")));
        boolean b = (new FileManagementService()).uploadFileUsingURL("https://people.sc.fsu.edu/~jburkardt/data/csv/addresses.csv");
        assertTrue(Files.exists(Paths.get("data/horarios/csv/addresses.csv")));
		assertTrue(b);
	}
	
	@Test
	void uploadFileUsingURLJSONTest() throws MalformedURLException {
		assertFalse(Files.exists(Paths.get("data/horarios/csv/addresses.csv")));
        boolean b = (new FileManagementService()).uploadFileUsingURL("https://people.sc.fsu.edu/~jburkardt/data/csv/addresses.csv");
        assertTrue(Files.exists(Paths.get("data/horarios/csv/addresses.csv")));
		assertTrue(b);
	}
	
	
	
	
	@Test
	void getFileTest() {
		
	}
	
	
}
