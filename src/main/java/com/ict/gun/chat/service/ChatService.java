package com.ict.gun.chat.service;

import com.ict.gun.chat.entity.Chat;
import com.ict.gun.chat.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatService {

    private final ChatRepository chatRepository;

    @Autowired
    public ChatService(ChatRepository chatRepository) {

        this.chatRepository = chatRepository;
    }
    public Chat insertChat(Chat chat) {
        chat.setChat_date(LocalDateTime.now()); // 현재 날짜와 시간 설정
        chat.setChat_fix("N"); // chat_fix 설정
        Chat savedChat = chatRepository.save(chat); // 저장된 Chat 객체 받기

        if (savedChat != null) {
            return savedChat;
        } else {
            // 처리할 작업이나 예외 처리를 추가할 수 있습니다.
            throw new RuntimeException("Failed to save chat");
        }
    }


    public void deleteOldestChats() {
        int numberOfChatsToKeep = 30; // 유지할 최신 채팅의 수를 30으로 설정

        List<Chat> chatsToDelete = chatRepository.findOldestChats();

        // chat_fix가 'N'이고, 삭제할 채팅이 존재하는 경우에만 삭제 수행
        if (chatsToDelete.size() > numberOfChatsToKeep) {
            List<Chat> chatsToDeleteSubList = chatsToDelete.subList(numberOfChatsToKeep, chatsToDelete.size());

            // chat_fix가 'N'인 채팅만 필터링하여 삭제
            List<Chat> chatsToDeleteFiltered = chatsToDeleteSubList.stream()
                    .filter(chat -> "N".equals(chat.getChat_fix()))
                    .collect(Collectors.toList());

            chatRepository.deleteAll(chatsToDeleteFiltered);
        }
    }
    public List<Chat> getAllChats() {
        try {
            return chatRepository.findAll();
        } catch (Exception e) {
            // 에러 로깅 혹은 필요한 예외 처리를 수행할 수 있습니다.
            // 여기서는 간단하게 에러 메시지 출력 후 빈 리스트 반환합니다.
            System.err.println("Failed to fetch all chats: " + e.getMessage());
            return Collections.emptyList(); // 빈 리스트 반환
        }
    }

}
