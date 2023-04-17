package pt.iscteiul.gestaohorarios.controller;

import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pt.iscteiul.gestaohorarios.service.FileManagementService;


@RequestMapping("/api/v1/horario")
@RestController
public class HorarioController {

    @Autowired
    private FileManagementService fileManagementService;

    @PostMapping("/uploadFile")
    public ResponseEntity<String> uploadFicheiro(@RequestParam("file") MultipartFile file) {
        boolean uploadSuccessful = fileManagementService.uploadFile(file);
        if (!uploadSuccessful)
            return ResponseEntity.internalServerError().body("The server had trouble saving your file");

        return ResponseEntity.ok().body("File " + file.getOriginalFilename() + " received successfully");
    }

    @PostMapping("/uploadUrl")
    public ResponseEntity<String> uploadURL(@RequestParam("file") String fileURL) throws MalformedURLException {
        boolean uploadSuccessful = fileManagementService.uploadFileUsingURL(fileURL);
        if(!uploadSuccessful)
            return ResponseEntity.internalServerError().body("The server had trouble getting your file");

        return ResponseEntity.ok().body("file url received successfully");
    }

//    @GetMapping("/downloadFile/{name}")
//    public ResponseEntity<UrlResource> getCSVFile(@PathVariable("name") String fileName) {
//
//        UrlResource resource = fileManagementService.getFile(fileName);
//        if (resource == null)
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not Found");
//
//
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
//                .body(resource);
//    }

}
