package com.ict.gun.notice.controller;

import com.ict.gun.common.FileHandler;
import com.ict.gun.notice.entity.Notice;
import com.ict.gun.notice.service.NoticeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
@CrossOrigin
public class NoticeController {
    private final NoticeService noticeService;

    @GetMapping("/list")// 모든 권한 접근
    public List<Notice> getNoticeList() {
            return noticeService.getNoticeList();
    }
    @PostMapping("/insert")
    public ResponseEntity<String> insertNotice(@RequestParam("adminId") String adminId,
                                               @RequestParam("noticeTitle") String noticeTitle,
                                               @RequestParam("noticeCon") String noticeCon,
                                               @RequestParam(name = "noticeFile", required = false) MultipartFile noticeFile) {
        FileHandler handler = new FileHandler();
        Notice notice = Notice.builder()
                .adminId(adminId)
                .noticeTitle(noticeTitle)
                .noticeCon(noticeCon)
                .noticeInputDate(new Date(System.currentTimeMillis()))
                .build();
        log.info("notice : {}", notice);
        String folderName = "notice";
        if(handler.handleFileUpload(noticeFile, folderName, adminId)) {
            notice.setNoticeFile(folderName + "/" + noticeFile.getOriginalFilename());
        }else{
            notice.setNoticeFile(null);
        }
        try {
            noticeService.insertNotice(notice);
        }catch (Exception e) {
            return ResponseEntity.status(500).body("fail");
        }
        return ResponseEntity.ok("success");
    }
    @PatchMapping("/update")
    public ResponseEntity<String> updateNotice(@RequestParam("noticeNum") int noticeNum,
                                               @RequestParam(name = "adminId", required = false) String adminId,
                                               @RequestParam(name = "noticeTitle", required = false) String noticeTitle,
                                               @RequestParam(name = "noticeCon", required = false) String noticeCon,
                                               @RequestParam(name = "noticeFile", required = false) MultipartFile noticeFile) {
        FileHandler handler = new FileHandler();
        Notice notice = noticeService.getNotice(noticeNum);
        log.info("notice : {}", notice);
        notice.setAdminId(adminId);
        notice.setNoticeTitle(noticeTitle);
        notice.setNoticeCon(noticeCon);
        notice.setNoticeModDate(new Date(System.currentTimeMillis()));
        log.info("notice : {}", notice);
        String folderName = "notice";
        if(handler.handleFileUpload(noticeFile, folderName, adminId)) {
            notice.setNoticeFile(folderName + "/" + noticeFile.getOriginalFilename());
        }else{
            notice.setNoticeFile(null);
        }
        try {
            noticeService.updateNotice(notice);
        }catch (Exception e) {
            return ResponseEntity.status(500).body("fail");
        }
        return ResponseEntity.ok("success");
    }

    @PatchMapping("/addcount/{noticeNum}")
    public void addCount(@PathVariable("noticeNum") int noticeNum) {
        Notice notice = noticeService.findById(noticeNum);
        notice.setNoticeCount(notice.getNoticeCount() + 1);
        noticeService.updateNotice(notice);
    }

    @GetMapping("/detail/{noticeNum}")
    public Notice getNotice(@PathVariable("noticeNum") int noticeNum) {
        return noticeService.getNotice(noticeNum);
    }

    @PatchMapping("/delete/{noticeNum}")
    public void deleteNotice(@PathVariable("noticeNum") int noticeNum) {
        Notice notice = noticeService.findById(noticeNum);
        notice.setNoticeDelDate(new Date(System.currentTimeMillis()));
        noticeService.updateNotice(notice);
    }

}
