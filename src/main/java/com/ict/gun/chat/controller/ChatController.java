package com.ict.gun.chat.controller;

import com.ict.gun.chat.entity.Chat;
import com.ict.gun.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RestController
public class ChatController {
    private final ChatService chatService;

    @Transactional
    @CrossOrigin("http://localhost:8888")
    @PostMapping("/insert")
    public ResponseEntity<String> insertChat(@RequestBody Chat chat) {
        try {
            Chat insertedChat = chatService.insertChat(chat);
            if (insertedChat != null) {
                return new ResponseEntity<>("Chat inserted successfully", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Failed to insert chat", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to insert chat: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Transactional
    @DeleteMapping("/chats/delete")
    public String deleteOldestChats() {
        try {
            chatService.deleteOldestChats();
            return "성공적으로 삭제했습니다.";
        } catch (Exception e) {
            log.error("삭제에 실패했습니다 : ", e.getMessage());
            return "삭제에 실패했습니다.";
        }
    }
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
}