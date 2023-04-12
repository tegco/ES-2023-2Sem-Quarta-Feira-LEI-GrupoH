package pt.iscteiul.gestaohorarios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pt.iscteiul.gestaohorarios.service.ConversorCSVJSON;
import pt.iscteiul.gestaohorarios.service.FileUploadService;

import java.util.Map;

@RequestMapping("/api/v1/horario")
@RestController
public class HorarioController {
    @Autowired
    private ConversorCSVJSON conversorCSVJSON;

    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("/upload/file")
    public ResponseEntity<String> uploadFicheiro(@RequestParam("file") MultipartFile file) {
        boolean uploadSuccessful = fileUploadService.uploadFile(file);
        if(!uploadSuccessful)
            return ResponseEntity.internalServerError().body("The server had trouble saving your file");

        return ResponseEntity.ok().body("File received successfully");
    }

    @PostMapping("/upload/url")
    public ResponseEntity<String> uploadURL(@RequestBody String fileURL) {
        boolean uploadSuccessful = fileUploadService.uploadFileUsingURL(fileURL);
        if(!uploadSuccessful)
            return ResponseEntity.internalServerError().body("The server had trouble getting your file");

        return ResponseEntity.ok().body("file url received successfully");
    }




}
