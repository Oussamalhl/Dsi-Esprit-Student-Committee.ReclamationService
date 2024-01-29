package dsi.esprit.tn.services;

import dsi.esprit.tn.Models.Reclamation;

import java.util.List;

public interface IreclamationServiceImpl {
    Long showReclamationUser(String username);
    void addReclamation(Reclamation reclamation,Long userId);
    void addClubReclamation(Reclamation reclamation,Long userId,Long clubId);
    void addEventReclamation(Reclamation reclamation,Long userId,Long eventId);
    void deleteReclamation(long idReclamation);
    Reclamation updateReclamation(Reclamation user);
    List<Reclamation> showAllReclamation();
    Reclamation showReclamation(Long id);
}
