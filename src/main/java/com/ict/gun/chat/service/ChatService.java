package com.ict.gun.chat.service;

import com.ict.gun.chat.entity.Chat;
import com.ict.gun.chat.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatService {

    @Autowired
    private final ChatRepository chatRepository;

    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }


    @Transactional
    public void saveChat(String chat_content, String chat_title, String mem_email) {
        Chat chat = new Chat();

        // chat_content 필드 설정
        if (chat_content != null && !chat_content.isEmpty()) {
            chat.setChat_content(chat_content);
        } else {
            // chat_content가 null이거나 빈 문자열인 경우 예외 처리 또는 기본값 설정
            // 예를 들어, 기본값으로 "No content" 등을 설정할 수 있습니다.
            chat.setChat_content("No content");
        }

        // chat_title 필드 설정
        chat.setChat_title(chat_title);

        // chat_date는 LocalDateTime.now()를 통해 현재 시간으로 자동 설정됨
        chat.setChat_date(LocalDateTime.now());

        // chat_fix 필드 설정 (기본값으로 설정하는 경우)
        chat.setChat_fix("N");

        // mem_email 필드 설정 (값이 넘어오면 설정)
        if (mem_email != null && !mem_email.isEmpty()) {
            chat.setMem_email(mem_email);
        }

        // chat_num은 시퀀스를 통해 자동으로 생성됨

        // chatRepository를 통해 chat 저장
        chatRepository.save(chat);
    }




//    @Transactional
//    public String deleteOldestChats() {
//        try {
//            int numberOfChatsToKeep = 5; // 유지할 최신 채팅의 수를 일단(5)로 설정
//
//            List<Chat> chatsWithFixN = chatRepository.findByChat_fix("N");
//
//            if (chatsWithFixN.size() > numberOfChatsToKeep) {
//                chatRepository.deleteOldestChatWithFixN();
//                return "성공적으로 삭제했습니다.";
//            }
//
//            return "삭제할 대상이 없습니다.";
//        } catch (Exception e) {
//            return "삭제에 실패했습니다.";
//        }
//    }
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

    // 글의 총 갯수를 반환하는 메서드
    public int getChatCount() {
        try {
            return chatRepository.getChatCount();
        } catch (Exception e) {
            // 에러 처리 로직 추가
            return 0;
        }
    }

}
