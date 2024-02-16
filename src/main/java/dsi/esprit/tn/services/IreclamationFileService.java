package dsi.esprit.tn.services;

import dsi.esprit.tn.Models.reclamationFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IreclamationFileService {
    public reclamationFile addFile(MultipartFile file, Long id);
    public void removeFile(Long f, Long id) ;
    public List<reclamationFile> findAll();
    byte[] decompressBytes(byte[] data);
    public List<reclamationFile>GetReclamationFiles(Long id);
    reclamationFile getImageByName(String name);
}
