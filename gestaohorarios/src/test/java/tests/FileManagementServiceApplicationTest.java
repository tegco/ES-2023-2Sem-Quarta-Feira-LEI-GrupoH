package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import java.nio.file.Path;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.UrlResource;
import org.springframework.mock.web.MockMultipartFile;

import pt.iscteiul.gestaohorarios.service.FileManagementService;


/**
 * Classe de testes para a classe FileManagementService.
 * 
 * 
 * @author jplemos5
 * @author malca1-iscte
 * @author gabriel-bitrefill
 * 
 * @since 2023-4-18
 */
class FileManagementServiceApplicationTest {
	@BeforeEach
	void setUp() throws Exception {
		List<File> files = new ArrayList<>();
		files.add(new File("data/horarios/json/Test-horario-exemplo-professor.json"));
		files.add(new File("data/horarios/csv/Test-horario-exemplo-professor.csv"));
		files.add(new File("data/horarios/csv/urltest.csv"));
		files.add(new File("data/horarios/json/urltest.json"));
		for (File file : files)
			if (file.exists())
				file.delete();
	}
	
	@AfterEach
	void tearDown() throws Exception {
		List<File> files = new ArrayList<>();
		files.add(new File("data/horarios/json/Test-horario-exemplo-professor.json"));
		files.add(new File("data/horarios/csv/Test-horario-exemplo-professor.csv"));
		files.add(new File("data/horarios/csv/urltest.csv"));
		files.add(new File("data/horarios/json/urltest.json"));
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
		Path path = Paths.get("data/horarios/csv/urltest.csv");
		assertFalse(Files.exists(path));
        String originalFileName = (new FileManagementService()).uploadFileUsingURL("https://raw.githubusercontent.com/malca1-iscte/urltest/main/urltest.csv");
        assertTrue(Files.exists(path));
		assertEquals("urltest.csv", originalFileName);
	}

	@Test
	void uploadFileUsingURLJSONTest() throws MalformedURLException {
		Path path = Paths.get("data/horarios/json/urltest.json");
		assertFalse(Files.exists(path));
		String originalFileName = (new FileManagementService()).uploadFileUsingURL("https://raw.githubusercontent.com/malca1-iscte/urltest/main/urltest.json");
        assertTrue(Files.exists(path));
		assertEquals("urltest.json", originalFileName);
	}
	
	@Test
	void getFileCSVTest() throws MalformedURLException {
		Path path = Path.of((FileManagementService.CSV_UPLOAD_PATH + "/horario-exemplo-professor.csv"));
		assertEquals(new UrlResource(path.toUri()), new FileManagementService().getFile("horario-exemplo-professor.csv"));
	}
	
	@Test
	void getFileJSONTest() throws MalformedURLException {
		Path path = Path.of((FileManagementService.JSON_UPLOAD_PATH + "/horario-exemplo-professor.json"));
		assertEquals(new UrlResource(path.toUri()), new FileManagementService().getFile("horario-exemplo-professor.json"));
	}
	
}
