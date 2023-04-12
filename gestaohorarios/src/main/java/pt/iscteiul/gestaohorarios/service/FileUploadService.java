package pt.iscteiul.gestaohorarios.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileUploadService {

    public static final String UPLOAD_PATH = "data/horarios/csv/";

    public boolean uploadFile(MultipartFile file) {
        try {
            //TODO Adicinar algum tipo de ID ao ficheiro para não haver conflitos
            file.transferTo(new File(UPLOAD_PATH + file.getOriginalFilename()));
        } catch (IOException e) {
            //Talvez fazer log do erro?
            System.err.println("Erro ao guardar ficheiro");
            return false;
        }
        return true;
    }
    public boolean uploadFileUsingURL(String fileURL) {
        //TODO
        return false;
    }

}
