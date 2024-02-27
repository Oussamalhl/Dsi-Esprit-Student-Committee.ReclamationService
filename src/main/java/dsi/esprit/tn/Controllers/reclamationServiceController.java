package dsi.esprit.tn.Controllers;


import dsi.esprit.tn.Models.Reclamation;
import dsi.esprit.tn.Models.reclamationFile;
import dsi.esprit.tn.security.jwt.JwtUtils;
import dsi.esprit.tn.services.IEmailingServiceImpl;
import dsi.esprit.tn.services.IreclamationFileService;
import dsi.esprit.tn.services.IreclamationServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/reclamation")
public class reclamationServiceController {

    private static final Logger logger = LoggerFactory.getLogger(reclamationServiceController.class);

    @Autowired
    IreclamationFileService IRFS;
    @Autowired
    private IreclamationServiceImpl reclamationservice;
    @Autowired
    IEmailingServiceImpl IemailS;
    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/test")
    public String test() {
        return "reclamation api.";
    }

    @GetMapping("/test/auth")
    public String testAuth() {
        return "user authenticated.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public Boolean adminTest() {
        return true;
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public Boolean userTest() {
        return true;
    }

    @GetMapping("/moderator")
    @PreAuthorize("hasRole('MODERATOR')")
    public Boolean moderatorTest() {
        return true;
    }

    @GetMapping("/getuserid")
    public Long getUserId(@RequestParam String username) {
        return reclamationservice.showReclamationUser(username);
    }

    @GetMapping("/getUser")
    public Long getUser(@RequestParam Long idReclamation) {
        return reclamationservice.getUser(idReclamation);
    }

    @GetMapping("/getTargets")
    public List<String> getTargets(@RequestParam String type) {
        return reclamationservice.getTargets(type);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/showallReclamations")
    public ResponseEntity<?> showAllReclamations() {
        return ResponseEntity.ok(reclamationservice.showAllReclamation());
    }

    @GetMapping("/showReclamation")
    public ResponseEntity<?> showReclamation(@Valid @RequestParam long idReclamation) {
        return ResponseEntity.ok(reclamationservice.showReclamation(idReclamation));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/deleteReclamation")
    public ResponseEntity<?> deleteReclamation(@Valid @RequestParam long idReclamation) {

        reclamationservice.deleteReclamation(idReclamation);
        return ResponseEntity.ok("Reclamation Id:" + idReclamation + " is successfully deleted");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/updateReclamation")
    public ResponseEntity<?> updateReclamation(@RequestBody Reclamation reclamation) {

        reclamationservice.updateReclamation(reclamation);
        return ResponseEntity.ok("Reclamationid: " + reclamation.getId() + " is successfully updated");

    }

    @PostMapping("/addreclamation")
    public ResponseEntity<?> addReclamation(HttpServletRequest request, @RequestBody Reclamation reclamation) throws Exception {

        String jwt = jwtUtils.parseJwt(request);
        if (jwt != null) {
            logger.info("RECjwt: {}", jwt);
            String username = jwtUtils.getUserNameFromJwtToken(jwt);
            List<String> user = Arrays.asList(reclamationservice.getUsernameDetails(username).split(",", -1));

            logger.info("RECdetails: {}", username);
            reclamationservice.addReclamation(reclamation, reclamationservice.showReclamationUser(username.trim()));
            IemailS.ReclamationSentMail(user,reclamation);
            return ResponseEntity.ok(reclamation.toString());


        }
        return ResponseEntity
                .badRequest()
                .body("Error: Bad request");
    }

    @PostMapping(path="/addFile/{id}")
    public reclamationFile addFile(@PathVariable("id")long id, @RequestParam("file") MultipartFile file) throws IOException {
        return IRFS.addFile(file, id);
    };

    @DeleteMapping("/{id}/deleteFile/{File}")
    public void deleteFile(@PathVariable("File")Long File, @PathVariable("id") long id ) throws IOException {
        IRFS.removeFile(File, id);};

    @GetMapping("/getFiles/{id}")
    public List<reclamationFile>reclamationFiles(@PathVariable("id")Long id){
        return IRFS.GetReclamationFiles(id);
    }
    @GetMapping("/getAllFiles")
    public List<reclamationFile>reclamationAllFiles(){
        return IRFS.findAll();
    }

    @GetMapping("/getRecByDate")
    public List<Reclamation>getReclamationsByDate(@RequestParam("startDate") String startDate, @RequestParam("endDate")String endDate) throws ParseException {
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        //sdformat.parse(startDate);
        return reclamationservice.getReclamationsByDate(sdformat.parse(startDate),sdformat.parse(endDate));
    }

    @GetMapping("/countRecByMonth")
    public Integer countReclamationsByDate(@RequestParam("month") Integer month, @RequestParam("year")Integer year) {
        //SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        //sdformat.parse(startDate);
        return reclamationservice.countReclamationsByMonth(month,year);
    }
    @GetMapping("/countAllRecByMonth")
    public List<Integer[]> countAllReclamationsByDate() {
        //SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        //sdformat.parse(startDate);
        return reclamationservice.countAllReclamationsByMonth();
    }

    @GetMapping("/countRecStatusByYear")
    public List<Integer[]> countReclamationStatusByYear() {
        return reclamationservice.countReclamationStatusByYear();
    }
    @GetMapping("/countRecTypeByYear")
    public List<Object[]> countReclamationTypeByYear() {
        return reclamationservice.countReclamationTypeByYear();
    }

}
