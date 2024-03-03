package dsi.esprit.tn.services;

import dsi.esprit.tn.Models.reclamationFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IreclamationFileService {
    reclamationFile addFile(MultipartFile file, Long id);
    void removeFile(Long f) ;
    public List<reclamationFile> findAll();
    List<reclamationFile>GetReclamationFiles(Long id);
}
