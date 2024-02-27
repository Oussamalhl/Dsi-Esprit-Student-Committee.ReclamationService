package dsi.esprit.tn.services;



import dsi.esprit.tn.Models.Reclamation;

import java.util.List;

public interface IEmailingServiceImpl {
    void ReclamationSentMail(List<String> user, Reclamation e) throws Exception;

}
