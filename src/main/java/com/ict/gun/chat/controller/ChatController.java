package com.ict.gun.chat.controller;

import com.ict.gun.chat.dto.FridgeInfoRequest;
import com.ict.gun.chat.dto.chatKeyword;
import com.ict.gun.chat.dto.dataToSend;
import com.ict.gun.chat.entity.Chat;
import com.ict.gun.chat.repository.ChatRepository;
import com.ict.gun.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RestController
public class ChatController {
    private final ChatService chatService;

    @Autowired
    private ChatRepository chatRepository; // ChatRepository 주입


//    @Transactional
//    @CrossOrigin("http://localhost:8888")
//    @PostMapping("/chatinsert")
//    public ResponseEntity<String> insertChat(@RequestBody dataToSend dataToSend) {
//        try {
//            String userQuery = dataToSend.getChat_title();
//            String aiResponse = dataToSend.getChat_content();
//            String memEmail = dataToSend.getMemEmail();
//
//            Chat chat = new Chat();
//            chat.setChat_title(userQuery);
//            chat.setChat_content(aiResponse);
//            chat.setChat_date(LocalDateTime.now());
//            chat.setChat_fix("N");
//            chat.setMem_email(memEmail);
//
//            chatService.saveChat(aiResponse, userQuery, memEmail);
//
//            return new ResponseEntity<>("성공적으로 저장되었습니다.", HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>("저장에 실패하였습니다. " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @Transactional
    @CrossOrigin("http://localhost:8888")
    @PostMapping("/chatinsert")
    public ResponseEntity<String> insertChat(@RequestBody dataToSend dataToSend) {
        try {
            String userQuery = dataToSend.getChat_title();
            String aiResponse = dataToSend.getChat_content();
            String memEmail = dataToSend.getMemEmail();

            chatService.saveChat(aiResponse, userQuery, memEmail);

            return new ResponseEntity<>("성공적으로 저장되었습니다.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("저장에 실패하였습니다. " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/chatrecent")
    public List<Chat> selectChatList(@RequestParam("memEmail") String memEmail) {
        try {
            // 이메일 값과 DB의 mem_email 값이 일치하는 데이터만을 가져오는 메소드 호출
            return chatService.getAllChats(memEmail);
        } catch (Exception e) {
            // 에러 로깅 혹은 필요한 예외 처리를 수행할 수 있습니다.
            // 여기서는 간단하게 에러 메시지 출력 후 빈 리스트 반환합니다.
            System.err.println("Failed to fetch chats for the user: " + e.getMessage());
            return Collections.emptyList(); // 빈 리스트 반환
        }
    }

    @GetMapping("/chatfixed")
    public List<Chat> selectChatFixedList(@RequestParam("memEmail") String memEmail) {
        try {
            // 이메일 값과 DB의 mem_email 값이 일치하는 데이터만을 가져오는 메소드 호출
            return chatService.getFixedChats(memEmail);
        } catch (Exception e) {
            // 에러 로깅 혹은 필요한 예외 처리를 수행할 수 있습니다.
            // 여기서는 간단하게 에러 메시지 출력 후 빈 리스트 반환합니다.
            System.err.println("Failed to fetch chats for the user: " + e.getMessage());
            return Collections.emptyList(); // 빈 리스트 반환
        }
    }

    @Transactional
    @GetMapping("/chatcount")
    public int getChatCount(@RequestParam("memEmail") String memEmail) {
        try {
            return chatService.getChatCount(memEmail);
        } catch (Exception e) {
            log.error("글 갯수를 불러오는데 실패했습니다.", e.getMessage());
            return 0; // 실패 시 0 반환 혹은 다른 적절한 방법으로 처리
        }
    }

    @Transactional
    @GetMapping("/chatycount")
    public int getChatYCount(@RequestParam("memEmail") String memEmail) {
        try {
            return chatService.getChatYCount(memEmail);
        } catch (Exception e) {
            log.error("글 갯수를 불러오는데 실패했습니다.", e.getMessage());
            return 0; // 실패 시 0 반환 혹은 다른 적절한 방법으로 처리
        }
    }

    @PutMapping("/chatupdate/{chat_num}")
    public ResponseEntity<Chat> updateChatFixStatus(@PathVariable("chat_num") int chat_num, @RequestBody String newFixStatus) {
        try {
            Chat updatedChat = chatService.updateChatFixStatus(chat_num, newFixStatus);
            return ResponseEntity.ok(updatedChat);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/recipe/chatdetail")
    public ResponseEntity<Chat> getChatByChatNum(@RequestParam("chat_num") int chat_num) {
        Optional<Chat> chat = chatService.getOneChat(chat_num);
        return chat.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

//    @GetMapping("/search")
//    public List<Chat> searchChatByKeywordAndMemEmail(@RequestParam("keyword") String keyword, @RequestParam("memEmail") String memEmail) {
//        return chatService.searchChatByKeywordAndMemEmail(keyword, memEmail);

    @GetMapping("/search")
    public List<Chat> searchChat(@RequestParam(name = "searchTerm") String searchTerm,
                                 @RequestParam(name = "memEmail") String memEmail
    ) {
        // searchTerm과 memEmail을 이용하여 검색하거나 처리하는 로직을 작성합니다.
        // 예를 들어, searchTerm과 memEmail을 이용하여 DB에서 검색을 수행하고 결과를 반환할 수 있습니다.

        return chatService.searchChat(searchTerm, memEmail);
    }

    @GetMapping("/chatsearchcount")
    public int getChatsearchCount(@RequestParam("memEmail") String memEmail) {
        try {
            return chatService.getChatsearchCount(memEmail);
        } catch (Exception e) {
            log.error("글 갯수를 불러오는데 실패했습니다.", e); // 예외 정보 전체를 로깅
            return 0; // 실패 시 0 반환 혹은 다른 적절한 방법으로 처리
        }
    }

    @GetMapping("/find")
    public List<Chat> findChat(@RequestParam(name = "searchTerm") String searchTerm,
                                 @RequestParam(name = "memEmail") String memEmail
    ) {
        // searchTerm과 memEmail을 이용하여 검색하거나 처리하는 로직을 작성합니다.
        // 예를 들어, searchTerm과 memEmail을 이용하여 DB에서 검색을 수행하고 결과를 반환할 수 있습니다.

        return chatService.findChat(searchTerm, memEmail);
    }

    @GetMapping("/chatfindcount")
    public int getChatfindCount(@RequestParam("memEmail") String memEmail) {
        try {
            return chatService.getChatfindCount(memEmail);
        } catch (Exception e) {
            log.error("글 갯수를 불러오는데 실패했습니다.", e); // 예외 정보 전체를 로깅
            return 0; // 실패 시 0 반환 혹은 다른 적절한 방법으로 처리
        }
    }

//    @PostMapping("/fridgeinfo")
//    public String processFridgeInfo(@RequestBody FridgeInfoRequest fridgeInfoRequest) {
//        return chatService.getDietRecipeFromFridge(fridgeInfoRequest);
//    }
}