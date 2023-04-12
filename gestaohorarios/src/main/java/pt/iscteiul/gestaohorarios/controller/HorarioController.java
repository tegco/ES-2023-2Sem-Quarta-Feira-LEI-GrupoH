package pt.iscteiul.gestaohorarios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pt.iscteiul.gestaohorarios.service.ConversorCSVJSON;

import java.util.Map;

@RequestMapping("/api/v1/horario")
@RestController
public class HorarioController {
//    @Autowired
//    private ConversorCSVJSON conversorCSVJSON;

    @PostMapping("/upload")
    public ResponseEntity<String> getFicheiro(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok().body("file received successfully");
    }

    @PostMapping("/store/url")
    public ResponseEntity<String> getURL(@RequestBody Map<String, String> payload) {
        return ResponseEntity.ok().body("file received successfully");
    }



}
