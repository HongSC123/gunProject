package com.ict.gun.member.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberCommand{
    private String memEmail;
    private String memPw;
    private String memType;
    private String memPhoto;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date memEn;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date memMod;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date memQuit;
    private String memAct;
    @Transient
    private String memGen;
    @Transient
    private float memWeight;
    @Transient
    private float memHeight;
    @Transient
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date memBir;
    @Transient
    private String memActLevel;
}
