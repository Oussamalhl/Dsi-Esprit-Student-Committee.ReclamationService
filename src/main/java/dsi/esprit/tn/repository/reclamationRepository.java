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
    @Query(value = "update reclamations set user_id=?1 where id=?2", nativeQuery = true)
    void setUserReclamation(@Param("user_id") Long user_id,@Param("id") Long id);
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
    @Query(value = "SELECT username,email,firstname,lastname FROM users WHERE username=?1", nativeQuery = true)
    String findUsernameDetails(String username);
    @Query(value = "SELECT * FROM reclamations WHERE user_id=?1", nativeQuery = true)
    List<Reclamation> showUserReclamations(Long user_id);
    @Query(value = "SELECT id FROM users WHERE username=?1", nativeQuery = true)
    Long findUsernameId(String username);

    @Query(value = "SELECT user_id FROM reclamations WHERE id=?1", nativeQuery = true)
    Long findUserId(Long id);

    @Query(value = "SELECT name FROM clubs", nativeQuery = true)
    List<String> getTragetClubs();
    @Query(value = "SELECT username FROM users", nativeQuery = true)
    List<String> getUsers();

    //    @Query(value = "SELECT id FROM users WHERE username=?1", nativeQuery = true)
//    Long getClub (String username);
    @Query(value = "SELECT name FROM events", nativeQuery = true)
    List<String> getTragetevents();

    @Query(value = "SELECT name FROM foyers", nativeQuery = true)
    List<String> getTragetFoyer();

    @Query(value = "SELECT * FROM reclamations WHERE date BETWEEN :startDate and :endDate", nativeQuery = true)
    List<Reclamation> selectReclamationsByDate(@Param("startDate") Date startDate,@Param("endDate") Date endDate);
    @Query(value = "SELECT COUNT(*) FROM reclamations WHERE MONTH(date)=? AND YEAR(date)=?", nativeQuery = true)
    Integer countReclamationsByMonth(@Param("month") int month,@Param("year") int year);

    @Query(value = "SELECT MONTH(date),COUNT(*) FROM reclamations WHERE YEAR(date)=?1 GROUP BY MONTH(date)", nativeQuery = true)
    List<Integer[]> countAllReclamationsByMonth(Integer year);
    @Query(value = "SELECT COUNT(*) FROM reclamations WHERE status=TRUE AND YEAR(date)=?1", nativeQuery = true)
    List<Integer[]> countReclamationStatusByYear(Integer year);
    @Query(value = "SELECT type,COUNT(*) FROM reclamations WHERE YEAR(date)=?1 GROUP BY type", nativeQuery = true)
    List<Object[]> countReclamationTypeByYear(Integer year);
    @Query(value = "SELECT COUNT(*) FROM reclamations", nativeQuery = true)
    Integer countAllReclamations();
    @Query(value = "SELECT COUNT(*) FROM reclamations where status=true", nativeQuery = true)
    Integer countAllDoneReclamations();
    @Query(value = "SELECT target,COUNT(*) FROM reclamations WHERE YEAR(date)=?1 GROUP BY target", nativeQuery = true)
    List<Object[]> countReclamationTargetByYear(Integer year);
}
