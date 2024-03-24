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
import java.util.Collections;
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

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("/getUser")
    public Long getUser(@RequestParam Long idReclamation) {
        return reclamationservice.getUser(idReclamation);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("/getTargets")
    public List<String> getTargets(@RequestParam String type) {
        return reclamationservice.getTargets(type);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/showallReclamations")
    public List<Reclamation> showAllReclamations() {
        return reclamationservice.showAllReclamation();
    }

    @GetMapping("/showReclamation")
    public Reclamation showReclamation(@Valid @RequestParam long idReclamation) {
        return reclamationservice.showReclamation(idReclamation);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("/showURec")
    public List<Reclamation> showUserReclamations(HttpServletRequest request) {
        String jwt = jwtUtils.parseJwt(request);
        if (jwt != null) {
            String username = jwtUtils.getUserNameFromJwtToken(jwt);
            return reclamationservice.showUserReclamations(reclamationservice.showReclamationUser(username.trim()));
        }
        return Collections.emptyList();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/deleteReclamation")
    public void deleteReclamation(@Valid @RequestParam long idReclamation) {

        reclamationservice.deleteReclamation(idReclamation);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/updateReclamation")
    public Reclamation updateReclamation(@RequestBody Reclamation reclamation) {

        return reclamationservice.updateReclamation(reclamation);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PostMapping("/addreclamation")
    public Reclamation addReclamation(HttpServletRequest request, @RequestBody Reclamation reclamation) throws Exception {

        String jwt = jwtUtils.parseJwt(request);
        if (jwt != null) {
            reclamation.setDate(new Date());
            reclamation.setStatus(true);
            logger.info("RECjwt: {}", jwt);
            String username = jwtUtils.getUserNameFromJwtToken(jwt);
            List<String> user = Arrays.asList(reclamationservice.getUsernameDetails(username).split(",", -1));

            logger.info("RECdetails: {}", username);
            //reclamationservice.addReclamation(reclamation, reclamationservice.showReclamationUser(username.trim()));
            IemailS.ReclamationSentMail(user, reclamation);

            return reclamationservice.addReclamation(reclamation, reclamationservice.showReclamationUser(username.trim()));

        }
        return null;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getusers")
    public List<String> getUsers() {
        return reclamationservice.getUsers();
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/addreclamationa")
    public Reclamation addReclamationAdmin(@RequestBody Reclamation reclamation, @RequestParam String username) throws Exception {

        reclamation.setDate(new Date());
        try{
            reclamationservice.addReclamation(reclamation, reclamationservice.showReclamationUser(username.trim()));
        }catch (Exception e) {
            System.out.println("Something went wrong.");
        }
        return reclamation;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PostMapping(path = "/addFile/{id}")
    public reclamationFile addFile(@PathVariable("id") long id, @RequestParam("file") MultipartFile file) throws IOException {
        return IRFS.addFile(file, id);
    }

    ;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @DeleteMapping("/deleteFile/{File}")
    public void deleteFile(@PathVariable("File") Long File) {
        IRFS.removeFile(File);
    }

    ;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("/getFiles/{id}")
    public List<reclamationFile> reclamationFiles(@PathVariable("id") Long id) {
        return IRFS.GetReclamationFiles(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getAllFiles")
    public List<reclamationFile> reclamationAllFiles() {
        return IRFS.findAll();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getRecByDate")
    public List<Reclamation> getReclamationsByDate(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) throws ParseException {
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        //sdformat.parse(startDate);
        return reclamationservice.getReclamationsByDate(sdformat.parse(startDate), sdformat.parse(endDate));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/countRecByMonth")
    public Integer countReclamationsByDate(@RequestParam("month") Integer month, @RequestParam("year") Integer year) {
        //SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        //sdformat.parse(startDate);
        return reclamationservice.countReclamationsByMonth(month, year);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/countAllRecByMonth")
    public List<Integer[]> countAllReclamationsByDate(@RequestParam Integer year) {
        //SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        //sdformat.parse(startDate);
        return reclamationservice.countAllReclamationsByMonth(year);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/countRecStatusByYear")
    public List<Integer[]> countReclamationStatusByYear(@RequestParam Integer year) {
        return reclamationservice.countReclamationStatusByYear(year);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/countRecTypeByYear")
    public List<Object[]> countReclamationTypeByYear(@RequestParam Integer year) {
        return reclamationservice.countReclamationTypeByYear(year);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/countAllRec")
    public Integer countAllReclamations() {
        return reclamationservice.countAllReclamations();
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/countRecTargetByYear")
    public List<Object[]> countReclamationTargetByYear(@RequestParam Integer year) {
        return reclamationservice.countReclamationTargetByYear(year);
    }
}
