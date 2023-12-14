package com.ict.gun.member.entity;

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

import java.sql.Date;

@Data
@Table(name="member_options")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberOptions {
    @Id
    @Column(name="mem_email")
    @Email
    @NotBlank
    private String memEmail;

    @Column(name="mem_gen")
    private String memGen;

    @Column(name="mem_weight")
    private float memWeight;

    @Column(name="mem_height")
    private float memHeight;

    @Column(name="mem_bir")
    private Date memBir;

    @Column(name="mem_act_level")
<<<<<<< HEAD
    private float memActLevel;

    @Column(name="mem_reco_daily_calories")
    private int  mem_reco_daily_calories;
=======
    private Float memActLevel;
>>>>>>> 0f35a6cefb7fbe512a3d68d1b38d5c21285cb01e
}
