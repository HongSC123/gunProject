package com.ict.gun.chat.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ict.gun.chat.entity.Chat;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends CrudRepository<Chat, Long> {

//    List<Chat> findByChat_fix(String chatFix);
//    @Modifying
//    @Query(value = "DELETE FROM Chat c WHERE c.chat_fix = 'N' AND c.chat_date = (SELECT MIN(c2.chat_date) FROM Chat c2 WHERE c2.chat_fix = 'N')")
//    List<Chat> deleteOldestChatWithFixN();


    List<Chat> findAll();

    @Query("SELECT COUNT(c) FROM Chat c")
    int getChatCount();

    @Query("SELECT COUNT(c) FROM Chat c WHERE c.chat_fix = 'Y'")
    int getChatYCount();

    Optional<Chat> findById(Long chat_num);

//    @Query("SELECT c FROM Chat c WHERE c.chat_num = :chat_num")
//    Chat findByChatNum(int chatNum);

}
