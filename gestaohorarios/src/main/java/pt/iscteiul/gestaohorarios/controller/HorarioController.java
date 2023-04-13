package pt.iscteiul.gestaohorarios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pt.iscteiul.gestaohorarios.service.ConversorCSVJSON;
import pt.iscteiul.gestaohorarios.service.FileManagementService;

import java.io.File;

@RequestMapping("/api/v1/horario")
@RestController
public class HorarioController {

    @Autowired
    private FileManagementService fileManagementService;

    @PostMapping("/uploadFile")
    public ResponseEntity<String> uploadFicheiro(@RequestParam("file") MultipartFile file) {
        boolean uploadSuccessful = fileManagementService.uploadFile(file);
        if(!uploadSuccessful)
            return ResponseEntity.internalServerError().body("The server had trouble saving your file");

        return ResponseEntity.ok().body("File " + file.getOriginalFilename() + " received successfully");
    }

    @PostMapping("/uploadUrl")
    public ResponseEntity<String> uploadURL(@RequestBody String fileURL) {
        boolean uploadSuccessful = fileManagementService.uploadFileUsingURL(fileURL);
        if(!uploadSuccessful)
            return ResponseEntity.internalServerError().body("The server had trouble getting your file");

        return ResponseEntity.ok().body("file url received successfully");
    }

    //Mais tarde será melhor ter um sistema com ids ao invés de nomes
    @GetMapping("/downloadFile/{name}")
    public ResponseEntity<?> getCSVFile(@PathVariable("name") String fileName) {

        UrlResource resource = fileManagementService.getFile(fileName);
        if(resource == null)
            return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);


        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .body(resource);
    }


}
