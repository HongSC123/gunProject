package com.ict.gun.chat.service;

import com.ict.gun.chat.entity.Chat;
import com.ict.gun.chat.repository.ChatRepository;
import com.ict.gun.ref.data.repository.RefPhotoRepository;
import com.ict.gun.ref.data.repository.RefRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ChatService {

    @Autowired
    private final ChatRepository chatRepository;
    private final RefPhotoRepository refPhotoRepository;
    private final RefRepository refRepository;

    public ChatService(ChatRepository chatRepository, RefRepository refRepository, RefPhotoRepository refPhotoRepository) {
        this.chatRepository = chatRepository;
        this.refPhotoRepository = refPhotoRepository;
        this.refRepository = refRepository;
    }


//    @Transactional
//    public void saveChat(String chat_content, String chat_title, String memEmail) {
//        Chat chat = new Chat();
//
//        if (chat_content != null && !chat_content.isEmpty()) {
//            chat.setChat_content(chat_content);
//        } else {
//            chat.setChat_content("No content");
//        }
//
//        chat.setChat_title(chat_title);
//        chat.setChat_date(LocalDateTime.now());
//        chat.setChat_fix("N");
//        chat.setMem_email(memEmail);
//
//        chatRepository.save(chat);
//    }

    @Transactional
    public void saveChat(String chat_content, String chat_title, String mem_email) {
        Chat chat = new Chat();

        if (chat_content != null && !chat_content.isEmpty()) {
            chat.setChat_content(chat_content);
        } else {
            chat.setChat_content("No content");
        }

        chat.setChat_title(chat_title);
        chat.setChat_date(LocalDateTime.now());
        chat.setChat_fix("N");
        chat.setMem_email(mem_email);

        chatRepository.save(chat);

        // 추가 작업 - 특정 조건을 검사하고 삭제 등의 로직 수행
        String chat_fix = "N";
        while (countNChats(mem_email, chat_fix) >= 30) {
            deleteOldestNChat(mem_email, chat_fix);
        }
    }

    private int countNChats(String mem_email, String chat_fix) {
        return chatRepository.countByCondition(mem_email, chat_fix);
    }

    private void deleteOldestNChat(String mem_email, String chat_fix) {
        List<Chat> nChats = chatRepository.seekbyCondition(mem_email, chat_fix);
        if (!nChats.isEmpty()) {
            Chat oldestChat = nChats.get(0);
            chatRepository.delete(oldestChat);
        }
    }

    @Transactional
    public List<Chat> getAllChats(String memEmail) {
        try {
            // 이메일 값과 DB의 mem_email 값이 일치하는 데이터만을 가져오는 메소드 호출
            return chatRepository.findAll(memEmail);
        } catch (Exception e) {
            // 에러 로깅 혹은 필요한 예외 처리를 수행할 수 있습니다.
            // 여기서는 간단하게 에러 메시지 출력 후 빈 리스트 반환합니다.
            System.err.println("Failed to fetch chats for the user: " + e.getMessage());
            return Collections.emptyList(); // 빈 리스트 반환
        }
    }

    @Transactional
    public List<Chat> getFixedChats(String memEmail) {
        try {
            // 이메일 값과 DB의 mem_email 값이 일치하는 데이터만을 가져오는 메소드 호출
            return chatRepository.findFixed(memEmail);
        } catch (Exception e) {
            // 에러 로깅 혹은 필요한 예외 처리를 수행할 수 있습니다.
            // 여기서는 간단하게 에러 메시지 출력 후 빈 리스트 반환합니다.
            System.err.println("Failed to fetch chats for the user: " + e.getMessage());
            return Collections.emptyList(); // 빈 리스트 반환
        }
    }

    // 글의 총 갯수를 반환하는 메서드
    public int getChatCount(String memEmail) {
        try {
            return chatRepository.getChatCount(memEmail);
        } catch (Exception e) {
            // 에러 처리 로직 추가
            return 0;
        }
    }

    public int getChatsearchCount(String memEmail) {
        try {
            return chatRepository.getsearchChatCount(memEmail);
        } catch (Exception e) {
            log.error("채팅 갯수를 불러오는데 실패했습니다.", e); // 예외 정보 전체를 로깅
            return 0; // 예외 발생 시 0을 반환하도록 수정
        }
    }


    public int getChatYCount(String memEmail) {
        try {
            return chatRepository.getChatYCount(memEmail);
        } catch (Exception e) {
            // 에러 처리 로직 추가
            return 0;
        }
    }

    //버튼 누르면 속성변환하는 메서드
    public Chat updateChatFixStatus(int chat_num, String newFixStatus) {
        Optional<Chat> optionalChat = chatRepository.findById((long) chat_num);
        if (optionalChat.isPresent()) {
            Chat chat = optionalChat.get();
            chat.setChat_fix(newFixStatus);
            return chatRepository.save(chat);
        } else {
            throw new IllegalArgumentException("Chat not found with ID: " + chat_num);
        }
    }

    public Optional<Chat> getOneChat(int chat_num) {
        try {
            return chatRepository.findById((long) chat_num).stream().findFirst();
        } catch (Exception e) {
            // 에러 로깅 혹은 필요한 예외 처리를 수행할 수 있습니다.
            System.err.println("Failed to fetch chat: " + e.getMessage());
            return Optional.empty(); // 빈 Optional 반환
        }
    }

    public List<Chat> searchChat(String searchTerm, String memEmail) {
        return chatRepository.searchChat(searchTerm, memEmail);
    }

    public List<Chat> findChat(String searchTerm, String memEmail) {
        return chatRepository.findChat(searchTerm, memEmail);
    }

    public int getChatfindCount(String memEmail) {
        try {
            return chatRepository.getfindChatCount(memEmail);
        } catch (Exception e) {
            log.error("채팅 갯수를 불러오는데 실패했습니다.", e); // 예외 정보 전체를 로깅
            return 0; // 예외 발생 시 0을 반환하도록 수정
        }
    }

//    public String getDietRecipeFromFridge(String email) {
//        // 냉장고 정보를 기반으로 다이어트 레시피를 생성하는 로직을 구현합니다.
//        // 필요한 정보를 조회하고 적절한 레시피를 생성합니다.
//        String recommendedRecipe = "";
//
//        // 냉장고 정보를 기반으로 식품명을 조회합니다.
//        String foodName = chatRepository.findRefExNameByMemEmail(email);
//
//        // 추천 레시피를 생성하는 로직을 구현합니다.
//        recommendedRecipe = generateDietRecipeBasedOnFood(foodName);
//
//        return recommendedRecipe;
//    }
//
//    private String generateDietRecipeBasedOnFood(String foodName) {
//        // 식품명을 기반으로 다이어트 레시피를 생성하는 로직을 구현합니다.
//        // 실제로 레시피를 추천하거나 관련된 레시피를 가져오는 작업을 수행합니다.
//        String dietRecipe = "냉장고에 있는 " + foodName + "을(를) 활용한 다이어트 레시피 추천: ...";
//
//        return dietRecipe;
//    }

}