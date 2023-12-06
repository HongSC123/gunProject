package com.ict.gun.chat.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.ict.gun.chat.entity.Chat;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChatRepository extends CrudRepository<Chat, String> {

    @Query(value = "SELECT c FROM Chat c ORDER BY c.chat_date ASC")
    List<Chat> findOldestChats();

    List<Chat> findAll();
}
