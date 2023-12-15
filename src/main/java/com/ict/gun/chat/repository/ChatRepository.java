package com.ict.gun.chat.repository;

import com.ict.gun.ref.data.repository.RefPhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ict.gun.chat.entity.Chat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends CrudRepository<Chat, Long> {


    @Query("SELECT c FROM Chat c WHERE c.mem_email = :memEmail AND c.chat_fix = :chat_fix ORDER BY c.chat_num ASC")
    List<Chat> seekbyCondition(@Param("memEmail") String memEmail, @Param("chat_fix") String chat_fix);

//    void deleteChat(@Param("chat_num") Long chat_num);

    @Query("SELECT COUNT(c) FROM Chat c WHERE c.mem_email = :memEmail and c.chat_fix = :chat_fix")
    int countByCondition(@Param("memEmail") String memEmail, @Param("chat_fix") String chat_fix);


    @Query("SELECT c FROM Chat c WHERE c.mem_email = :memEmail ORDER BY c.chat_num ASC")
    List<Chat> findAll(@Param("memEmail") String memEmail);

    @Query("SELECT c FROM Chat c WHERE c.mem_email = :memEmail and c.chat_fix = 'Y' ORDER BY c.chat_num ASC")
    List<Chat> findFixed(@Param("memEmail") String memEmail);


    @Query("SELECT COUNT(c) FROM Chat c WHERE c.mem_email = :memEmail")
    int getChatCount(@Param("memEmail") String memEmail);


    @Query("SELECT COUNT(c) FROM Chat c WHERE c.chat_fix = 'Y' and c.mem_email = :memEmail")
    int getChatYCount(@Param("memEmail") String memEmail);

    Optional<Chat> findById(Long chat_num);


    @Query("SELECT c FROM Chat c WHERE c.chat_title LIKE %:searchTerm% AND c.mem_email = :memEmail order by c.chat_num asc")
    List<Chat> searchChat(@Param("searchTerm") String searchTerm, @Param("memEmail") String memEmail);

    @Query("SELECT c FROM Chat c WHERE c.chat_title LIKE %:searchTerm% AND c.mem_email = :memEmail and c.chat_fix = 'Y' order by c.chat_num asc")
    List<Chat> findChat(@Param("searchTerm") String searchTerm, @Param("memEmail") String memEmail);
//
//    @Query("SELECT rex.REF_EXNAME FROM RefPhoto rp " +
//            "JOIN Ref re ON rp.REF_CODE = re.REF_CODE " +
//            "JOIN RefEx rex ON re.REF_EXCODE = rex.REF_EXCODE " +
//            "WHERE rp.MEM_EMAIL = :email " +
//            "AND re.REF_END_DATE = (SELECT MIN(REF_END_DATE) FROM Ref WHERE REF_CODE = rp.REF_CODE)")
//    String findRefExNameByMemEmail(String email);
}
