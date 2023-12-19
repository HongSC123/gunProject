package com.ict.gun.notice.repository;

import com.ict.gun.notice.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {

    List<Notice> findByNoticeDelDateIsNullOrderByNoticeNumDesc();
}
