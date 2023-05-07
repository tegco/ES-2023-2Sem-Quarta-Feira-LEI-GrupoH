package pt.iscteiul.gestaohorarios.service;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.core.type.TypeReference;
import com.opencsv.CSVWriter;
import com.opencsv.ICSVWriter;

import pt.iscteiul.gestaohorarios.model.Horario;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.time.format.DateTimeFormatter;

/**
 * A classe ConversorCSVJSON contém métodos para
 * ler ficheiros CSV e JSON e métodos para converter
 * de um CSV para JSON e vice versa.
 *
 * @author djcos1
 * @author tegco
 * @since 2023-4-11
 */

@Service
public class ConversorCSVJSON {
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    /**
     * Recebe o nome do ficheiro JSON e devolve uma lista com o horário contido no ficheiro.
     *
     * @param arquivoJSON o nome do ficheiro.
     * @return List&lt;Horario&gt; devolve uma lista com o horário contido no ficheiro dado como argumento.
     * @throws IOException Se existir erro de input.
     */
    public List<Horario> lerJSON(String arquivoJSON) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        List<Horario> horarios;

        try (FileReader fileReader = new FileReader("data/horarios/json/" + arquivoJSON)) {
            horarios = objectMapper.readValue(fileReader, new TypeReference<List<Horario>>() {
            });
        }

        return horarios;
    }

    /**
     * Recebe uma lista com o horario e o nome do ficheiro e
     * gera um arquivo CSV com esse nome que contém o horário escolhido.
     * Esse ficheiro é guardado em data/horarios/csv/.
     *
     * @param horarios   lista com o horário a colocar no ficheiro.
     * @param arquivoCSV o nome do ficheiro a ser gerado.
     * @throws IOException Se existir erro de input.
     */
    public void gerarArquivoCSV(List<Horario> horarios, String arquivoCSV) throws IOException {
        try (CSVWriter writer = new CSVWriter(
                new FileWriter("data/horarios/csv/" + arquivoCSV),
                ';',
                ICSVWriter.NO_QUOTE_CHARACTER,
                ICSVWriter.DEFAULT_ESCAPE_CHARACTER,
                ICSVWriter.DEFAULT_LINE_END
        )) {
            // Escreve o cabeçalho do arquivo CSV
            String[] header = {"Curso", "Unidade Curricular", "Turno", "Turma", "Inscritos no turno", "Dia da semana", "Hora início da aula", "Hora fim da aula", "Data da aula", "Sala atribuída à aula", "Lotação da sala"};
            writer.writeNext(header);

            // Escreve os registros no arquivo CSV
            for (Horario horario : horarios) {
                String[] registo = new String[]{
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
     *
     * @param arquivoCSV o nome do ficheiro.
     * @return List&lt;Horario&gt; devolve uma lista com o horário contido no ficheiro dado como argumento.
     * @throws IOException Se existir erro de input.
     */
    public List<Horario> lerCSV(String arquivoCSV) throws IOException {
        List<Horario> horarios = new ArrayList<>();
        CsvMapper csvMapper = new CsvMapper();
        csvMapper.enable(CsvParser.Feature.SKIP_EMPTY_LINES);
        csvMapper.registerModule(new JavaTimeModule());

        CsvSchema cabecalho = csvMapper.schemaFor(Horario.class).withColumnSeparator(';').withHeader();
        try (FileReader reader = new FileReader("data/horarios/csv/" + arquivoCSV)) {
            MappingIterator<Horario> it = csvMapper.readerFor(Horario.class).with(cabecalho).readValues(reader);
            while (it.hasNext()) {
                Horario horario = it.next();
                if (horario.getDataDaAula() != null && horario.getHoraInicioAula() != null && horario.getHoraFimAula() != null) {
                    horarios.add(horario);
                }
            }
        }
        return horarios;
    }

    /**
     * Recebe uma lista com o horario e o nome do ficheiro e
     * gera um arquivo JSON com esse nome que contém o horário escolhido.
     * Esse ficheiro é guardado em data/horarios/json/.
     *
     * @param horarios    lista com o horário a colocar no ficheiro.
     * @param arquivoJSON o nome do ficheiro a ser gerado.
     * @throws IOException Se existir erro de input.
     */
    public void gerarArquivoJSON(List<Horario> horarios, String arquivoJSON) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new FileWriter("data/horarios/json/" + arquivoJSON), horarios);
    }

}
