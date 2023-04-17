package pt.iscteiul.gestaohorarios.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pt.iscteiul.gestaohorarios.model.Horario;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;


@Service
public class FileManagementService {
    @Autowired
    private ConversorCSVJSON conversorCSVJSON;
    public static final Path CSV_UPLOAD_PATH = Path.of(System.getProperty("user.dir") + "/data/horarios/csv/");
    public static final Path JSON_UPLOAD_PATH = Path.of(System.getProperty("user.dir") + "/data/horarios/json/");
    Logger logger = LoggerFactory.getLogger(FileManagementService.class);

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

            } else {
                saveFile(file, destinationPath);

                //Fazer a conversão para CSV
                List<Horario> horario = conversorCSVJSON.lerJSON(originalFileName);
                conversorCSVJSON.gerarArquivoCSV(horario, originalFileName.split("[.]")[0] + ".csv");
            }
        } catch (IOException e) {
            logger.error("Erro ao guardar o ficheiro", e);
            return false;
        }
        return true;
    }

    private void saveFile(MultipartFile file, Path destinationPath) throws IOException {
        File destination = new File(destinationPath + "\\" + file.getOriginalFilename());
        file.transferTo(destination);
    }

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
