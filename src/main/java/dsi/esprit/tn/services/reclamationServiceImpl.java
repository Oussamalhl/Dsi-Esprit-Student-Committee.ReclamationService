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

    //    @Override
//    public void addReclamation(Reclamation reclamation, Long userId) {
//         reclamationRepository.addReclamation(reclamation.getName(),
//                reclamation.getDescription(),
//                reclamation.getStatus(),
//                reclamation.getType(),
//                reclamation.getTarget(),
//                reclamation.getDate(),
//                userId);
//    }
    @Override
    public Reclamation addReclamation(Reclamation reclamation, Long userId) {
        Reclamation Reclamation = reclamationRepository.save(reclamation);
        reclamationRepository.setUserReclamation(userId,Reclamation.getId());
        return Reclamation;
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
    public List<Reclamation> showUserReclamations(Long idUser) {
        return reclamationRepository.showUserReclamations(idUser);

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
            case "CLUB":
                return reclamationRepository.getTragetClubs();
            case "EVENT":
                return reclamationRepository.getTragetevents();
        }
        return null;
    }
    @Override
    public List<String> getUsers() {
        return this.reclamationRepository.getUsers();
    }
    @Override
    public List<Reclamation> getReclamationsByDate(Date startDate, Date endDate) {
        return reclamationRepository.selectReclamationsByDate(startDate, endDate);

    }

    @Override
    public Integer countReclamationsByMonth(int month, int year) {
        return reclamationRepository.countReclamationsByMonth(month, year);

    }

    @Override
    public List<Integer[]> countAllReclamationsByMonth(Integer year) {
        return reclamationRepository.countAllReclamationsByMonth(year);

    }

    @Override
    public List<Integer[]> countReclamationStatusByYear(Integer year) {
        return reclamationRepository.countReclamationStatusByYear(year);

    }

    @Override
    public List<Object[]> countReclamationTypeByYear(Integer year) {
        return reclamationRepository.countReclamationTypeByYear(year);

    }

    @Override
    public String getUsernameDetails(String username) {
        return reclamationRepository.findUsernameDetails(username);

    }

    @Override
    public Integer countAllReclamations() {
        return reclamationRepository.countAllReclamations();
    }
    @Override
    public Integer countAllDoneReclamations() {
        return reclamationRepository.countAllDoneReclamations();
    }


    @Override
    public List<Object[]> countReclamationTargetByYear(Integer year) {
        return reclamationRepository.countReclamationTargetByYear(year);

    }

}
