package pt.iscteiul.gestaohorarios.service;

import com.opencsv.exceptions.CsvException;
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

    public boolean uploadFile(MultipartFile file) {
        Path destinationPath = JSON_UPLOAD_PATH;
        String originalFileName = file.getOriginalFilename();
        try {
            if (Objects.requireNonNull(originalFileName).contains("csv")){
                destinationPath = CSV_UPLOAD_PATH;

                //TODO Adicinar algum tipo de ID ao ficheiro para não haver conflitos
                File destination = new File(destinationPath + "\\" + file.getOriginalFilename());
                file.transferTo(destination);

                //Fazer a conversão para JSON
                List<Horario> horario = conversorCSVJSON.lerCSV(originalFileName);
                conversorCSVJSON.gerarArquivoJSON(horario, originalFileName.split("[.]")[0] + ".json");

            } else {
                File destination = new File(destinationPath + "\\" + file.getOriginalFilename());
                file.transferTo(destination);

                //Fazer a conversão para CSV
                List<Horario> horario = conversorCSVJSON.lerJSON(originalFileName);
                conversorCSVJSON.gerarArquivoCSV(horario, originalFileName.split("[.]")[0] + ".csv");

            }


        } catch (IOException | CsvException e) {
            System.err.println("Erro ao guardar ficheiro");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean uploadFileUsingURL(String fileURL) {
        //TODO
        return false;
    }

    public UrlResource getFile(String name) {
        Path searchingPath = JSON_UPLOAD_PATH;
        try {
            if (name.contains("csv"))
                searchingPath = CSV_UPLOAD_PATH;

            var wantedFile = Files.list(searchingPath)
                    .filter(f -> f.getFileName().toString().equals(name))
                    .toList();

            if (!wantedFile.isEmpty()) {
                System.out.println(wantedFile);
                return new UrlResource(wantedFile.get(0).toUri());
            }

        } catch (IOException e) {
            System.err.println("Erro ao buscar o ficheiro");
        }

        return null;
    }

}
