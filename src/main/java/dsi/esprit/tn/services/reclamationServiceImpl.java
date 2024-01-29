package dsi.esprit.tn.services;

import dsi.esprit.tn.Models.Reclamation;
import dsi.esprit.tn.repository.reclamationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class reclamationServiceImpl implements IreclamationServiceImpl {

    @Autowired
    reclamationRepository reclamationRepository;

    @Override
    public void addReclamation(Reclamation reclamation,Long userId) {
         reclamationRepository.addReclamation(reclamation.getName(),
                reclamation.getDescription(),
                reclamation.getStatus(),
                reclamation.getType(),userId);
    }
    @Override
    public void addClubReclamation(Reclamation reclamation,Long userId,Long clubId) {
        reclamationRepository.addClubReclamation(reclamation.getName(),
                reclamation.getDescription(),
                reclamation.getStatus(),
                reclamation.getType(),userId,clubId);
    }
    @Override
    public void addEventReclamation(Reclamation reclamation,Long userId,Long eventId) {
        reclamationRepository.addEventReclamation(reclamation.getName(),
                reclamation.getDescription(),
                reclamation.getStatus(),
                reclamation.getType(),userId,eventId);
    }
    @Override
    public void deleteReclamation(long idReclamation){
        reclamationRepository.deleteById(idReclamation);
    }
    @Override
    public Reclamation updateReclamation(Reclamation reclamation){
        return reclamationRepository.save(reclamation);
    }
    @Override
    public List<Reclamation> showAllReclamation() {
        return reclamationRepository.findAll();
    }

    @Override
    public Long showReclamationUser(String username) {
        return reclamationRepository.findUsernameId(username);

    }
    @Override
    public Reclamation showReclamation(Long idReclamation) {
        return reclamationRepository.findById(idReclamation).orElse(null);

    }
}
