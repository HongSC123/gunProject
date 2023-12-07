package com.ict.gun.chat.controller;

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
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RestController
public class ChatController {
    private final ChatService chatService;

    @Autowired
    private ChatRepository chatRepository; // ChatRepository 주입

    @Transactional
    @CrossOrigin("http://localhost:8888")
    @PostMapping("/chatinsert")
    public ResponseEntity<String> insertChat(@RequestBody dataToSend dataToSend, @RequestParam(required = false) String mem_email) {
        try {
            String userQuery = dataToSend.getChat_title();
            String aiResponse = dataToSend.getChat_content();

            Chat chat = new Chat();
            chat.setChat_title(userQuery);
            chat.setChat_content(aiResponse);
            chat.setChat_date(LocalDateTime.now());
            chat.setChat_fix("N");

            if (mem_email != null) {
                chat.setMem_email(mem_email);
            } else {
                chat.setMem_email(null);
                // 또는 다른 처리를 수행할 수 있음
            }

            chatService.saveChat(aiResponse, userQuery, mem_email);

            return new ResponseEntity<>("성공적으로 저장되었습니다.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("저장에 실패하였습니다. " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//    @Transactional
//    @DeleteMapping("/chatdelete")
//    public String deleteOldestChats() {
//        return chatService.deleteOldestChats();
//    }

    @Transactional
    @GetMapping("/chatrecent")
    public List<Chat> selectChatList() {
        try {
            return chatService.getAllChats();
        } catch (Exception e) {
            log.error("불러오는데 실패했습니다.", e.getMessage());

            return Collections.emptyList(); // 빈 리스트 반환
        }
    }

    @Transactional
    @GetMapping("/chatcount")
    public int getChatCount() {
        try {
            return chatService.getChatCount();
        } catch (Exception e) {
            log.error("글 갯수를 불러오는데 실패했습니다.", e.getMessage());
            return 0; // 실패 시 0 반환 혹은 다른 적절한 방법으로 처리
        }
    }
}