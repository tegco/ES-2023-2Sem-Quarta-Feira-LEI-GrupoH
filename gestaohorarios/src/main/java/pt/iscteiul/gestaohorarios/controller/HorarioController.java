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


// TODO: Auto-generated Javadoc
/**
 * The Class HorarioController.
 */
@RequestMapping("/api/v1/horario")
@RestController
public class HorarioController {

	/** The file management service. */
	@Autowired
	    private FileManagementService fileManagementService;
	
	/**
	 * Instantiates a new horario controller.
	 *
	 * @param file the file
	 */
	public HorarioController(FileManagementService file){
		this.fileManagementService = file;
	}
	 	

    /**
     * Upload ficheiro.
     *
     * @param file the file
     * @return the response entity
     */
    @PostMapping("/uploadFile")
    public ResponseEntity<String> uploadFicheiro(@RequestParam("file") MultipartFile file) {
        boolean uploadSuccessful = fileManagementService.uploadFile(file);
        if (!uploadSuccessful)
            return ResponseEntity.internalServerError().body("The server had trouble saving your file");

        return ResponseEntity.ok().body("File " + file.getOriginalFilename() + " received successfully");
    }

    /**
     * Upload URL.
     *
     * @param fileURL the file URL
     * @return the response entity
     * @throws MalformedURLException the malformed URL exception
     */
    @PostMapping("/uploadUrl")
    public ResponseEntity<String> uploadURL(@RequestParam("file") String fileURL) throws MalformedURLException {
        boolean uploadSuccessful = fileManagementService.uploadFileUsingURL(fileURL);
        if (!uploadSuccessful)
            return ResponseEntity.internalServerError().body("The server had trouble getting your file");

        return ResponseEntity.ok().body("file url received successfully");
    }

    /**
     * Gets the CSV file.
     *
     * @param fileName the file name
     * @return the CSV file
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
