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
 * The Class FileManagementService.
 */
@Service
public class FileManagementService {

    /**
     * The conversor CSVJSON.
     */
    @Autowired
    private ConversorCSVJSON conversorCSVJSON = new ConversorCSVJSON();

    /**
     * The Constant CSV_UPLOAD_PATH.
     */
    public static final Path CSV_UPLOAD_PATH = Path.of(System.getProperty("user.dir") + "/data/horarios/csv/");

    /**
     * The Constant JSON_UPLOAD_PATH.
     */
    public static final Path JSON_UPLOAD_PATH = Path.of(System.getProperty("user.dir") + "/data/horarios/json/");

    /**
     * Standard SpringBoot application logger.
     */
    Logger logger = LoggerFactory.getLogger(FileManagementService.class);

    /**
     * Upload file.
     *
     * @param file the file
     * @return true, if successful
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
     * Save file.
     *
     * @param file            the file
     * @param destinationPath the destination path
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void saveFile(MultipartFile file, Path destinationPath) throws IOException {
        File destination = new File(destinationPath + "\\" + file.getOriginalFilename());
        file.transferTo(destination);
    }

    /**
     * Upload file using URL.
     *
     * @param fileURL the file URL
     * @return true, if successful
     * @throws MalformedURLException the malformed URL exception
     */
    public boolean uploadFileUsingURL(String fileURL) throws MalformedURLException {

        RestTemplate rest = new RestTemplate();
        ResponseEntity<byte[]> response = rest.exchange(fileURL, HttpMethod.GET, null, byte[].class);
        byte[] fileBytes = response.getBody();

        String contentType = "";
        URL url = new URL(fileURL);
        String originalFileName = Paths.get(url.getPath()).getFileName().toString();

        if (originalFileName.contains("csv")) {
            contentType = "csv";
        } else if (originalFileName.contains("json")) {
            contentType = "json";
        }

        try {
            MultipartFile result = new MockMultipartFile(originalFileName, originalFileName, contentType, fileBytes);
            this.uploadFile(result);

        } catch (Exception e) {
            logger.error("Erro ao obter ficheiro", e);
            return false;
        }

        return true;
    }

    /**
     * Gets the file.
     *
     * @param name the name
     * @return the file
     */
    public UrlResource getFile(String name) {
        Path searchingPath = JSON_UPLOAD_PATH;
        if (name.contains("csv"))
            searchingPath = CSV_UPLOAD_PATH;
        try (var wantedDir = Files.list(searchingPath)) {
            var wantedFile = wantedDir.filter(f -> f.getFileName().toString().equals(name))
                    .toList();

            if (!wantedFile.isEmpty())
                return new UrlResource(wantedFile.get(0).toUri());

        } catch (IOException e) {
            logger.error("Erro ao buscar o ficheiro", e);
        }

        return null;
    }

}
