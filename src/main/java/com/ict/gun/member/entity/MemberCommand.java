package com.ict.gun.member.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.sql.Date;

@Data
@Entity
@QueryEntity
public class MemberCommand {
    @Id
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
    private String memAka;
    private UserRole role;
    private String memGen;
    private float memWeight;
    private float memHeight;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date memBir;
    private float memActLevel;
    private int mem_reco_daily_calories;

    // 기본 생성자
    public MemberCommand() {
    }

    // 모든 필드를 사용하는 생성자
    public MemberCommand(String memEmail, String memPw, String memType, String memPhoto, Date memEn, Date memMod, Date memQuit, String memAct, String memAka, UserRole role, String memGen, float memWeight, float memHeight, Date memBir, float memActLevel, int mem_reco_daily_calories) {
        this.memEmail = memEmail;
        this.memPw = memPw;
        this.memType = memType;
        this.memPhoto = memPhoto;
        this.memEn = memEn;
        this.memMod = memMod;
        this.memQuit = memQuit;
        this.memAct = memAct;
        this.memAka = memAka;
        this.role = role;
        this.memGen = memGen;
        this.memWeight = memWeight;
        this.memHeight = memHeight;
        this.memBir = memBir;
        this.memActLevel = memActLevel;
        this.mem_reco_daily_calories = mem_reco_daily_calories;
    }
}
