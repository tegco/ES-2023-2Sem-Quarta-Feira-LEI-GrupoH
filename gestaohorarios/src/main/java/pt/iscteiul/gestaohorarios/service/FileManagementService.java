package pt.iscteiul.gestaohorarios.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import pt.iscteiul.gestaohorarios.model.Horario;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

/**
 * A classe FileManagementService tem como objetivo incorporar
 * todos os métodos necessários para adicionar/buscar ficheiros.
 *
 * @author gpjle
 * @author tegco
 * @since 2023-4-17
 */
@Service
public class FileManagementService {

    @Autowired
    private ConversorCSVJSON conversorCSVJSON = new ConversorCSVJSON();

    /**
     * Diretoria onde ficheiros CSV Recebidos irão ser guardados.
     */
    public static final Path CSV_UPLOAD_PATH = Path.of(System.getProperty("user.dir") + "/data/horarios/csv/");

    /**
     * Diretoria onde ficheiros JSON Recebidos irão ser guardados.
     */
    public static final Path JSON_UPLOAD_PATH = Path.of(System.getProperty("user.dir") + "/data/horarios/json/");

    /**
     * Standard SpringBoot application logger.
     */
    Logger logger = LoggerFactory.getLogger(FileManagementService.class);

    /**
     * Guarda ficheiros JSON e CSV nas suas determinadas diretorias.
     * Converte e cria o ficheiro equivalente em JSON, se o file for CSV e vice-versa.
     *
     * @param file ficheiro recebido via HTTP.
     * @return true, se o ficheiro for guardado na diretoria com sucesso.
     */
    public boolean uploadFile(MultipartFile file) {
        Path destinationPath = JSON_UPLOAD_PATH;
        String originalFileName = file.getOriginalFilename();
        try {
            if (Objects.requireNonNull(originalFileName).contains("csv")) {
                destinationPath = CSV_UPLOAD_PATH;
                saveFile(file, destinationPath);
                //Fazer a conversão para JSON
                List<Horario> horario = conversorCSVJSON.lerCSV(originalFileName);
                conversorCSVJSON.gerarArquivoJSON(horario, originalFileName.split("[.]")[0] + ".json");

            } else if (Objects.requireNonNull(originalFileName).contains("json")) {
                saveFile(file, destinationPath);
                //Fazer a conversão para CSV
                List<Horario> horario = conversorCSVJSON.lerJSON(originalFileName);
                conversorCSVJSON.gerarArquivoCSV(horario, originalFileName.split("[.]")[0] + ".csv");
            } else {
                return false;
            }
        } catch (IOException e) {
            logger.error("Erro ao guardar o ficheiro", e);
            return false;
        }
        return true;
    }

    /**
     * Método responsável por adicionar o ficheiro na diretoria destino.
     *
     * @param file            ficheiro recebido via HTTP.
     * @param destinationPath diretoria destino.
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void saveFile(MultipartFile file, Path destinationPath) throws IOException {
        File destination = new File(destinationPath + "\\" + file.getOriginalFilename());
        file.transferTo(destination);
    }

    /**
     * Recebe o URL onde está localizado o ficheiro que se pretence adicionar à aplicação
     * e guarda-o, chamando a função uploadFile.
     *
     * @param fileURL URL do ficheiro.
     * @return true, se o ficheiro for guardado na diretoria com sucesso.
     */
    public boolean uploadFileUsingURL(String fileURL) {
        RestTemplate rest = new RestTemplate();
        ResponseEntity<byte[]> response = rest.exchange(fileURL, HttpMethod.GET, null, byte[].class);
        byte[] fileBytes = response.getBody();

        URL url;
        try {
            url = new URL(fileURL);
        } catch (MalformedURLException e) {
            logger.error("Erro ao obter ficheiro", e);
            return false;
        }
        String contentType = Objects.requireNonNull(response.getHeaders().getContentType()).getType();
        String originalFileName = Paths.get(url.getPath()).getFileName().toString();
        MultipartFile result = new MockMultipartFile(originalFileName, originalFileName, contentType, fileBytes);

        return this.uploadFile(result);
    }

    /**
     * Recebe o nome do ficheiro e vai buscá-lo à diretoria correspondente à sua extensão.
     *
     * @param name nome do ficheiro.
     * @return o ficheiro, caso seja encontrado. null, caso contrário.
     */
    public UrlResource getFile(String name) {
        Path searchingPath = JSON_UPLOAD_PATH;
        if (name.contains("csv"))
            searchingPath = CSV_UPLOAD_PATH;
        try (var wantedDir = Files.list(searchingPath)) {
            var wantedFile = wantedDir
                    .filter(f -> f.getFileName().toString().equals(name))
                    .toList();

            if (!wantedFile.isEmpty())
                return new UrlResource(wantedFile.get(0).toUri());

        } catch (IOException e) {
            logger.error("Erro ao buscar o ficheiro", e);
        }

        return null;
    }

}
