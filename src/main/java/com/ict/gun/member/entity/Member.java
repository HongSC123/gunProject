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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.util.Collection;
import java.util.Collections;

@Data
@Table(name = "member")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicInsert
public class Member implements UserDetails {
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

    @Column(name="mem_aka")
    private String memAka;

    @Column(name="role")
    private UserRole role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return memPw;
    }

    @Override
    public String getUsername() {
        return memEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        // 사용자 계정 만료 여부 로직 구현
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // 사용자 계정 잠김 여부 로직 구현
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // 사용자 자격 증명(비밀번호) 만료 여부 로직 구현
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 사용자 계정 활성화 여부 로직 구현
        return "Y".equals(memAct);
    }
}
