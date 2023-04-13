package pt.iscteiul.gestaohorarios.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.core.type.TypeReference;
import com.opencsv.CSVWriter;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.stereotype.Service;
import pt.iscteiul.gestaohorarios.model.Horario;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * A classe ConversorCSVJSON contém métodos para
 * ler ficheiros CSV e JSON e métodos para converter
 * de um CSV para JSON e vice versa.
 *
 * @author djcos1
 * @author tegco
 *
 * @since 2023-4-11
 *
 */
@Service
public class ConversorCSVJSON {
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    /**
     * Recebe o nome do ficheiro JSON e devolve uma lista com o horário contido no ficheiro.
     * @param arquivoJSON o nome do ficheiro.
     * @exception IOException Se existir erro de input.
     * @return List<Horario> devolve uma lista com o horário contido no ficheiro dado como argumento.
     */
    public List<Horario> lerJSON(String arquivoJSON) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        List<Horario> horarios;

        try (FileReader fileReader = new FileReader("data/horarios/json/" + arquivoJSON)) {
            horarios = objectMapper.readValue(fileReader, new TypeReference<List<Horario>>() {});
        }

        return horarios;
    }

    /**
     * Recebe uma lista com o horario e o nome do ficheiro e
     * gera um arquivo CSV com esse nome que contém o horário escolhido.
     * Esse ficheiro é guardado em data/horarios/csv/.
     * @param horarios lista com o horário a colocar no ficheiro.
     * @param arquivoCSV o nome do ficheiro a ser gerado.
     * @exception IOException Se existir erro de input.
     * @return Nada.
     */
    public void gerarArquivoCSV(List<Horario> horarios, String arquivoCSV) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter("data/horarios/csv/" + arquivoCSV))) {
            // Escreve o cabeçalho do arquivo CSV
            String[] header = { "Curso", "Unidade Curricular", "Turno", "Turma", "Inscritos no Turno", "Dia da Semana", "Hora Início Aula", "Hora Fim Aula", "Data da Aula", "Sala Atribuída", "Lotação da Sala" };
            writer.writeNext(header);

            // Escreve os registros no arquivo CSV
            for (Horario horario : horarios) {
                String[] registo = new String[] {
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
                writer.writeNext(registo);
            }
        }
    }

    /**
     * Recebe o nome do ficheiro CSV e devolve uma lista com o horário contido no ficheiro.
     * @param arquivoCSV o nome do ficheiro.
     * @exception IOException Se existir erro de input.
     * @exception CsvException Se existir um erro na leitura do ficheiro csv.
     * @return List<Horario> devolve uma lista com o horário contido no ficheiro dado como argumento.
     */
    public List<Horario> lerCSV(String arquivoCSV) throws IOException, CsvException {
        List<Horario> horarios = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader("data/horarios/csv/" + arquivoCSV))) {
            List<String[]> linhas = reader.readAll();

            for (int i = 1; i < linhas.size(); i++) { // Salta a primeira linha (cabeçalho)
                String[] linha = linhas.get(i);
                // Verifica se existe data, hora inicio e hora fim
                if(linha[6].trim().isEmpty() || linha[7].trim().isEmpty() || linha[8].trim().isEmpty())
                    continue;
                String curso = linha[0].trim();
                String unidadeCurricular = linha[1].trim();
                String turno = linha[2].trim();
                String turma = linha[3].trim();
                int inscritosNoTurno = linha[4].trim().isEmpty() ? 0 : Integer.parseInt(linha[4].trim());
                String diaDaSemana = linha[5].trim();
                LocalTime horaInicioAula = LocalTime.parse(linha[6].trim(), timeFormatter);
                LocalTime horaFimAula = LocalTime.parse(linha[7].trim(), timeFormatter);
                LocalDate dataDaAula = LocalDate.parse(linha[8].trim(), dateFormatter);
                String salaAtribuida = linha[9].trim();
                int lotacaoDaSala = linha[10].trim().isEmpty() ? 0 : Integer.parseInt(linha[10].trim());

                Horario novoHorario = new Horario(curso, unidadeCurricular, turno, turma, inscritosNoTurno, diaDaSemana, horaInicioAula, horaFimAula, dataDaAula, salaAtribuida, lotacaoDaSala);
                horarios.add(novoHorario);

            }
        }

        return horarios;
    }


    /**
     * Recebe uma lista com o horario e o nome do ficheiro e
     * gera um arquivo JSON com esse nome que contém o horário escolhido.
     * Esse ficheiro é guardado em data/horarios/json/.
     * @param horarios lista com o horário a colocar no ficheiro.
     * @param arquivoJSON o nome do ficheiro a ser gerado.
     * @exception IOException Se existir erro de input.
     * @return Nada.
     */
    public void gerarArquivoJSON(List<Horario> horarios, String arquivoJSON) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new FileWriter("data/horarios/json/" + arquivoJSON), horarios);

    }

}
