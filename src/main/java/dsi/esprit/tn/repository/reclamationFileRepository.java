package dsi.esprit.tn.repository;

import dsi.esprit.tn.Models.reclamationFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface reclamationFileRepository  extends JpaRepository<reclamationFile,Long> {
    //Optional<reclamationFile> findByFileName(String name);
}
