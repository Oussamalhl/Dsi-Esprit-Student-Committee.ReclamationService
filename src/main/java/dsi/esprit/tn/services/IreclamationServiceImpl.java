package dsi.esprit.tn.services;

import dsi.esprit.tn.Models.Reclamation;

import java.util.Date;
import java.util.List;

public interface IreclamationServiceImpl {
    Long showReclamationUser(String username);

    Reclamation addReclamation(Reclamation reclamation, Long userId);

    Long getUser(Long idReclamation);

    void deleteReclamation(long idReclamation);

    Reclamation updateReclamation(Reclamation user);

    List<Reclamation> showAllReclamation();

    Reclamation showReclamation(Long id);

    List<String> getTargets(String type);
    List<Reclamation> getReclamationsByDate(Date startDate, Date endDate);
    Integer countReclamationsByMonth(int month,int year);
    List<Integer[]> countAllReclamationsByMonth(Integer year);
    List<Integer[]> countReclamationStatusByYear(Integer year);
    List<Object[]> countReclamationTypeByYear(Integer year);
    String getUsernameDetails(String username);
    Integer countAllReclamations();
    Integer countAllDoneReclamations();
    List<Reclamation> showUserReclamations(Long idUser);
    List<Object[]> countReclamationTargetByYear(Integer year);
    List<String> getUsers();
}
