package pt.iscteiul.gestaohorarios.controller;

import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import pt.iscteiul.gestaohorarios.service.FileManagementService;


/**
 * A classe HorarioController tem a responsabilidade de ser o RestController da aplicação,
 * ou seja, é responsável por atender os pedidos HTTP feitos ao servidor.
 * Contem todos os métodos necessários para satisfazer as necessidades do cliente. Recebe
 * pedidos para guardar e buscar ficheiros na aplicação.
 *
 * @author gpjle
 * @since 2023-4-17
 */
@RequestMapping("/api/v1/horario")
@RestController
public class HorarioController {

    @Autowired
    private FileManagementService fileManagementService;

    /**
     * Instantiates a new horario controller.
     *
     * @param file the file
     */
    public HorarioController(FileManagementService file) {
        this.fileManagementService = file;
    }


    /**
     * Recebe pedidos HTTP do tipo POST e que tenham um objeto "file" que se trate de um MultipartFile
     *
     * @param file ficheiro MultipartFile
     * @return Resposta HTTP a reportar o sucesso (código 200) ou insucesso (código 500) da operação.
     */
    @PostMapping("/uploadFile")
    public ResponseEntity<String> uploadFicheiro(@RequestParam("file") MultipartFile file) {
        boolean uploadSuccessful = fileManagementService.uploadFile(file);
        if (!uploadSuccessful)
            return ResponseEntity.internalServerError().body("The server had trouble saving your file");

        return ResponseEntity.ok().body("File " + file.getOriginalFilename() + " received successfully");
    }

    /**
     * Recebe pedidos HTTP do tipo POST e que tenham um objeto "fileURL",
     * que será o url onde o ficheiro que se pretende carregar na aplicação está
     *
     * @param fileURL URL do ficheiro
     * @return Resposta HTTP a reportar o sucesso (código 200) ou insucesso (código 500) da operação.
     */
    @PostMapping("/uploadUrl")
    public ResponseEntity<String> uploadURL(@RequestParam("file") String fileURL) {
        boolean uploadSuccessful = fileManagementService.uploadFileUsingURL(fileURL);
        if (!uploadSuccessful)
            return ResponseEntity.internalServerError().body("The server had trouble getting your file");

        return ResponseEntity.ok().body("file url received successfully");
    }

    /**
     * Recebe pedidos HTTP do tipo GET, e devolve o ficheiro com o nome que esteja no path do URL.
     *
     * @param fileName nome do ficheiro
     * @return Resposta HTTP a reportar o sucesso (código 200), com o ficheiro no corpo,
     * ou insucesso (código 404) da operação.
     */
    @GetMapping("/downloadFile/{name}")
    public ResponseEntity<UrlResource> getCSVFile(@PathVariable("name") String fileName) {
        UrlResource resource = fileManagementService.getFile(fileName);
        if (resource == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not Found");

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .body(resource);
    }

}
