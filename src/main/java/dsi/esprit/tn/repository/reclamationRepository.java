package dsi.esprit.tn.repository;

import dsi.esprit.tn.Models.Reclamation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface reclamationRepository extends JpaRepository<Reclamation, Long> {

    @Modifying
    @Transactional
    @Query(value = "insert into reclamations (name,description,status,type,target,date,user_id) VALUES (:name,:description,:status,:type,:target,:date,:user_id)", nativeQuery = true)
    void addReclamation(@Param("name") String name,
                        @Param("description") String description,
                        @Param("status") Boolean status,
                        @Param("type") String type,
                        @Param("target") String target,
                        @Param("date") Date date,
                        @Param("user_id") Long user_id
    );

    @Modifying
    @Transactional
    @Query(value = "insert into reclamations (name,description,status,type,user_id,club_id) VALUES (:name,:description,:status,:type,:user_id,:club_id)", nativeQuery = true)
    void addClubReclamation(@Param("name") String name,
                            @Param("description") String description,
                            @Param("status") Boolean status,
                            @Param("type") String type,
                            @Param("user_id") Long user_id,
                            @Param("club_id") Long club_id
    );

    @Modifying
    @Transactional
    @Query(value = "insert into reclamations (name,description,status,type,user_id,event_id) VALUES (:name,:description,:status,:type,:user_id,:event_id)", nativeQuery = true)
    void addEventReclamation(@Param("name") String name,
                             @Param("description") String description,
                             @Param("status") Boolean status,
                             @Param("type") String type,
                             @Param("user_id") Long user_id,
                             @Param("event_id") Long event_id
    );

    @Query(value = "SELECT id FROM users WHERE username=?1", nativeQuery = true)
    Long findUsernameId(String username);

    @Query(value = "SELECT user_id FROM reclamations WHERE id=?1", nativeQuery = true)
    Long findUserId(Long id);

    @Query(value = "SELECT name FROM clubs", nativeQuery = true)
    List<String> getTragetClubs();

    //    @Query(value = "SELECT id FROM users WHERE username=?1", nativeQuery = true)
//    Long getClub (String username);
    @Query(value = "SELECT name FROM events", nativeQuery = true)
    List<String> getTragetevents();

    @Query(value = "SELECT name FROM foyers", nativeQuery = true)
    List<String> getTragetFoyer();
}
