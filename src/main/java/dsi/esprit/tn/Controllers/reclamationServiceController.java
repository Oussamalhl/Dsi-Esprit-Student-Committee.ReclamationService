package dsi.esprit.tn.Controllers;


import dsi.esprit.tn.Models.Reclamation;
import dsi.esprit.tn.repository.reclamationRepository;
import dsi.esprit.tn.security.jwt.JwtUtils;
import dsi.esprit.tn.services.IreclamationServiceImpl;
import dsi.esprit.tn.services.reclamationServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/reclamation")
public class reclamationServiceController {

    private static final Logger logger = LoggerFactory.getLogger(reclamationServiceController.class);

    @Autowired
    private IreclamationServiceImpl reclamationservice;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/test")
    public String test(){
        return "reclamation api.";
    }
    @GetMapping("/test/auth")
    public String testAuth(){
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
    public Long getUserId(@RequestParam String username){
        return reclamationservice.showReclamationUser(username);
    }
    @PostMapping("/addreclamation")
    public ResponseEntity<?> addReclamation (HttpServletRequest request, @RequestBody Reclamation reclamation, @RequestParam(required = false) Long clubId, @RequestParam(required = false) Long eventId){

        String jwt = jwtUtils.parseJwt(request);
        if (jwt != null) {
            logger.info("RECjwt: {}", jwt);
            List<String> details = jwtUtils.getDetailsFromJwtToken(jwt);
            logger.info("RECdetails: {}", details);
            if(clubId !=null && eventId==null) {
                reclamationservice.addClubReclamation(reclamation, reclamationservice.showReclamationUser(details.get(0).trim()), clubId);
                return ResponseEntity.ok(reclamation.toString()+clubId);
            }
            else if(eventId!=null && clubId==null) {
                reclamationservice.addEventReclamation(reclamation, reclamationservice.showReclamationUser(details.get(0).trim()), eventId);
                return ResponseEntity.ok(reclamation.toString()+eventId);
            }
            else {
                reclamationservice.addReclamation(reclamation, reclamationservice.showReclamationUser(details.get(0).trim()));
                return ResponseEntity.ok(reclamation.toString());
            }

        }
        return ResponseEntity
                .badRequest()
                .body("Error: Bad request");
    }

}
