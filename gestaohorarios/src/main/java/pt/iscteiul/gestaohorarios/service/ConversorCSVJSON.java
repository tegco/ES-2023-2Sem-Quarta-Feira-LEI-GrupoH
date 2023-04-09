package pt.iscteiul.gestaohorarios.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.core.type.TypeReference;
import com.opencsv.CSVWriter;
import pt.iscteiul.gestaohorarios.model.Horario;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.time.format.DateTimeFormatter;

public class ConversorCSVJSON {
	private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");


    public List<Horario> lerJSON(String arquivoJSON) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
            
        List<Horario> horarios;
        
        try (FileReader fileReader = new FileReader("data/horarios/json/" + arquivoJSON)) {
            horarios = objectMapper.readValue(fileReader, new TypeReference<List<Horario>>() {});
        }
        
        return horarios;
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
            List<Horario> horarios = conversor.lerJSON(arquivoJSON);
            conversor.gerarArquivoCSV(horarios, arquivoCSV);
            System.out.println("Arquivo CSV gerado com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao processar arquivos CSV/JSON: " + e.getMessage());
        }*/
    }
}