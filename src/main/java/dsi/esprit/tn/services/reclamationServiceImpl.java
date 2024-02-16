package dsi.esprit.tn.services;

import dsi.esprit.tn.Models.Reclamation;
import dsi.esprit.tn.repository.reclamationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class reclamationServiceImpl implements IreclamationServiceImpl {

    @Autowired
    reclamationRepository reclamationRepository;

    @Override
    public void addReclamation(Reclamation reclamation, Long userId) {
        reclamation.setDate(new Date());
        reclamationRepository.addReclamation(reclamation.getName(),
                reclamation.getDescription(),
                reclamation.getStatus(),
                reclamation.getType(),
                reclamation.getTarget(),
                reclamation.getDate(),
                userId);
    }

    @Override
    public void deleteReclamation(long idReclamation) {
        reclamationRepository.deleteById(idReclamation);
    }

    @Override
    public Reclamation updateReclamation(Reclamation reclamation) {
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

    @Override
    public Long getUser(Long idReclamation) {
        return reclamationRepository.findUserId(idReclamation);

    }

    @Override
    public List<String> getTargets(String type) {
        switch (type) {
            case "club":
                return reclamationRepository.getTragetClubs();
            case "event":
                return reclamationRepository.getTragetevents();
        }
        return null;
    }
}
