package pt.iscteiul.gestaohorarios.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.core.type.TypeReference;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.opencsv.CSVWriter;
import pt.iscteiul.gestaohorarios.model.Horario;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ConversorCSVJSON {
	private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public List<Horario> lerCSV(String arquivoCSV) throws IOException, CsvException {
        List<Horario> horarios = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader("data/horarios/csv/" + arquivoCSV))) {
            List<String[]> linhas = reader.readAll();
            
            for (int i = 1; i < linhas.size(); i++) { // Pula a primeira linha (cabeçalho)
            	String[] linha = linhas.get(i);
                String curso = linha[0].trim();
                String unidadeCurricular = linha[1].trim();
                String turno = linha[2].trim();
                String turma = linha[3].trim();
                int inscritosNoTurno = linha[4].trim().isEmpty() ? 0 : Integer.parseInt(linha[4].trim());
                String diaDaSemana = linha[5].trim();
                LocalTime horaInicioAula = linha[6].trim().isEmpty() ? LocalTime.MIN : LocalTime.parse(linha[6].trim(), timeFormatter);
                LocalTime horaFimAula = linha[7].trim().isEmpty() ? LocalTime.MIN : LocalTime.parse(linha[7].trim(), timeFormatter);
                LocalDate dataDaAula = linha[8].trim().isEmpty() ? LocalDate.MIN : LocalDate.parse(linha[8].trim(), dateFormatter);
                String salaAtribuida = linha[9].trim();
                int lotacaoDaSala = linha[10].trim().isEmpty() ? 0 : Integer.parseInt(linha[10].trim());

                Horario novoHorario = new Horario(curso, unidadeCurricular, turno, turma, inscritosNoTurno, diaDaSemana, horaInicioAula, horaFimAula, dataDaAula, salaAtribuida, lotacaoDaSala);
                horarios.add(novoHorario);

            }
        }

        return horarios;
    }

    public List<Horario> lerJSON(String arquivoJSON) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
            
        List<Horario> horarios;
        
        try (FileReader fileReader = new FileReader("data/horarios/json/" + arquivoJSON)) {
            horarios = objectMapper.readValue(fileReader, new TypeReference<List<Horario>>() {});
        }
        
        return horarios;
    }

    public void gerarArquivoJSON(List<Horario> horarios, String arquivoJSON) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Regista o módulo JavaTimeModule

        String jsonString = objectMapper.writerWithDefaultPrettyPrinter()
                                        .writeValueAsString(horarios);

        try (FileWriter fileWriter = new FileWriter("data/horarios/json/" + arquivoJSON)) {
            fileWriter.write(jsonString);
        }
    }

    public void gerarArquivoCSV(List<Horario> horarios, String arquivoCSV) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter("data/horarios/csv/" + arquivoCSV))) {
            // Escreve o cabeçalho do arquivo CSV
            String[] header = { "Curso", "Unidade Curricular", "Turno", "Turma", "Inscritos no Turno", "Dia da Semana", "Hora Início Aula", "Hora Fim Aula", "Data da Aula", "Sala Atribuída", "Lotação da Sala" };
            writer.writeNext(header);

            // Escreve os registros no arquivo CSV
            for (Horario horario : horarios) {
                String[] record = new String[] {
                    horario.getCurso(),
                    horario.getUnidadeCurricular(),
                    horario.getTurno(),
                    horario.getTurma(),
                    Integer.toString(horario.getInscritosNoTurno()),
                    horario.getDiaDaSemana(),
                    horario.getHoraInicioAula().format(timeFormatter),
                    horario.getHoraFimAula().format(timeFormatter),
                    horario.getDataDaAula().format(dateFormatter),
                    horario.getSalaAtribuida(),
                    Integer.toString(horario.getLotacaoDaSala())
                };
                writer.writeNext(record);
            }
        }
    }
    
    // Main só para testes
    public static void main(String[] args) {
        ConversorCSVJSON conversor = new ConversorCSVJSON();
        /*String arquivoCSV = "horario-exemplo.csv";
        String arquivoJSON = "horario-exemplo.json";

        try {
            List<Horario> horarios = conversor.lerCSV(arquivoCSV);
            conversor.gerarArquivoJSON(horarios, arquivoJSON);
            System.out.println("Arquivo JSON gerado com sucesso!");
        } catch (IOException | CsvException e) {
            System.err.println("Erro ao processar arquivos CSV/JSON: " + e.getMessage());
        }*/
        
        /*String arquivoCSV = "horario-exemplo-2.csv";
        String arquivoJSON = "horario-exemplo.json";

        try {
            List<Horario> horarios = conversor.lerJSON(arquivoJSON);
            conversor.gerarArquivoCSV(horarios, arquivoCSV);
            System.out.println("Arquivo CSV gerado com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao processar arquivos CSV/JSON: " + e.getMessage());
        }*/
    }
}

