package com.ict.gun.member.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.sql.Date;

@Data
@Table(name = "member")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicInsert
public class Member {
    @Id
    @Email
    @NotBlank
    @Column(name ="mem_email")
    private String memEmail;

    @Column(name ="mem_pw")
    private String memPw;

    @Column(name ="mem_type")
    private String memType;

    @Column(name ="mem_photo")
    private String memPhoto;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "mem_en")
    private Date memEn;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "mem_mod")
    private Date memMod;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "mem_quit")
    private Date memQuit;

    @Column(name = "mem_act")
    private String memAct;

}
