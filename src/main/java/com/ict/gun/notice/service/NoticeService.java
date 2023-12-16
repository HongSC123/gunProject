package com.ict.gun.notice.service;

import com.ict.gun.notice.entity.Notice;
import com.ict.gun.notice.repository.NoticeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class NoticeService {
    private final NoticeRepository noticeRepository;
    public List<Notice> getNoticeList() {
        return noticeRepository.findAll();
    }

    public void insertNotice(Notice notice) {
        noticeRepository.save(notice);
    }

    public void updateNotice(Notice notice) {
        noticeRepository.save(notice);
    }

    public Notice getNotice(long noticeNum) {
        return noticeRepository.findById(noticeNum).get();
    }

    public Notice findById(long noticeNum) {
        return noticeRepository.findById(noticeNum).get();
    }

}
