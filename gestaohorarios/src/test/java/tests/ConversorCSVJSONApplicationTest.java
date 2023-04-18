package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;

import org.springframework.beans.factory.annotation.Autowired;

import pt.iscteiul.gestaohorarios.model.*;
import pt.iscteiul.gestaohorarios.service.ConversorCSVJSON;

class ConversorCSVJSONApplicationTest {

	@Autowired
	ConversorCSVJSON conversor = new ConversorCSVJSON();

	@AfterEach
	void tearDown() throws Exception {
		List<File> files = new ArrayList<>();
		files.add(new File("data/horarios/json/JSONTest.json"));
		files.add(new File("data/horarios/csv/CSVTest.csv"));
		for (File file : files)
			if (file.exists())
				file.delete();
	}

	@Test
	void testLerCSV() throws IOException {
		List<Horario> horarios = conversor.lerCSV("horario-exemplo-professor.csv");
		assertNotNull(horarios);
		assertFalse(horarios.isEmpty());

		Horario horario1 = horarios.get(0);
		assertEquals("ME", horario1.getCurso());
		assertEquals("Teoria dos Jogos e dos Contratos", horario1.getUnidadeCurricular());
		assertEquals("01789TP01", horario1.getTurno());
		assertEquals("MEA1", horario1.getTurma());
		assertEquals(30, horario1.getInscritosNoTurno());
		assertEquals("Sex", horario1.getDiaDaSemana());
		assertEquals(LocalTime.parse("13:00:00"), horario1.getHoraInicioAula());
		assertEquals(LocalTime.parse("14:30:00"), horario1.getHoraFimAula());
		assertEquals(LocalDate.parse("2022-12-02"), horario1.getDataDaAula());
		assertEquals("AA2.25", horario1.getSalaAtribuida());
		assertEquals(34, horario1.getLotacaoDaSala());
	}

	// TODO Nestes ficheiros temos apenas dois horarios validos um com todos os
	// dados
	// e outro com apenas os dados de hora e data.
	@Test
	void testNullInLerCSV() throws IOException {
		List<Horario> horarios = conversor.lerCSV("NullTest.csv");
		assertEquals(2, horarios.size());
	}

	@Test
	void testNullInLerJSON() throws IOException {
		List<Horario> horarios = conversor.lerJSON("NullTest.json");
		assertEquals(2, horarios.size());
	}

	@Test
	void testGerarArquivoJSON() throws IOException {
		String filename = "JSONTest.json";
		Path filepath = Paths.get("data/horarios/json/" + filename);
		assertFalse(Files.exists(filepath));
		List<Horario> horarios = conversor.lerCSV("NullTest.csv");
		conversor.gerarArquivoJSON(horarios, filename);
		assertTrue(Files.exists(filepath));
	}

	@Test
	void testLerJSON() throws IOException {
		List<Horario> horarios = conversor.lerJSON("horario-exemplo-professor.json");
		assertNotNull(horarios);
		assertFalse(horarios.isEmpty());

		Horario horario1 = horarios.get(0);
		assertEquals("ME", horario1.getCurso());
		assertEquals("Teoria dos Jogos e dos Contratos", horario1.getUnidadeCurricular());
		assertEquals("01789TP01", horario1.getTurno());
		assertEquals("MEA1", horario1.getTurma());
		assertEquals(30, horario1.getInscritosNoTurno());
		assertEquals("Sex", horario1.getDiaDaSemana());
		assertEquals(LocalTime.parse("13:00:00"), horario1.getHoraInicioAula());
		assertEquals(LocalTime.parse("14:30:00"), horario1.getHoraFimAula());
		assertEquals(LocalDate.parse("2022-12-02"), horario1.getDataDaAula());
		assertEquals("AA2.25", horario1.getSalaAtribuida());
		assertEquals(34, horario1.getLotacaoDaSala());
	}

	@Test
	void testGerarArquivoCSV() throws IOException {
		String filename = "CSVTest.csv";
		Path filepath = Paths.get("data/horarios/csv/" + filename);
		assertFalse(Files.exists(filepath));
		List<Horario> horarios = conversor.lerJSON("NullTest.json");
		conversor.gerarArquivoCSV(horarios, filename);
		assertTrue(Files.exists(filepath));
	}

}
