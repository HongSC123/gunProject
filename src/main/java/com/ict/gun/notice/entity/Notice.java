package com.ict.gun.notice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "notice")
@Builder
@Entity
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notice_sequence_generator")
    @SequenceGenerator(name="notice_sequence_generator", sequenceName = "notice_sequence", allocationSize = 1)
    @Column(name = "NOTICE_NUM")
    private Long noticeNum;
    @Column(name = "ADMIN_ID")
    private String adminId;
    @Column(name = "NOTICE_TITLE")
    private String noticeTitle;
    @Column(name = "NOTICE_CON")
    private String noticeCon;
    @Column(name = "NOTICE_FILE")
    private String noticeFile;
    @Column(name = "NOTICE_INPUT_DATE")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date noticeInputDate;
    @Column(name = "NOTICE_MOD_DATE")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date noticeModDate;
    @Column(name = "NOTICE_DEL_DATE")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date noticeDelDate;
    @Column(name = "NOTICE_COUNT")
    private int noticeCount;

}
