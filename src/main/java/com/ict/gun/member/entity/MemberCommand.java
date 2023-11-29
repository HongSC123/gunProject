package com.ict.gun.member.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class MemberCommand extends Member {
    private String memEmail;
    private String memGen;
    private float memWeight;
    private float memHeight;
    private Date memBir;
    private String memActLevel;
}
