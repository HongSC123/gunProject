package com.ict.gun.member.dto;

import com.ict.gun.member.entity.Member;
import com.ict.gun.member.entity.UserRole;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class JoinRequest {
    @NotBlank(message = "로그인 아이디가 비어있습니다.")
    private String memEmail;

    @NotBlank(message = "비밀번호가 비어있습니다.")
    private String password;
    private String passwordCheck;

    private String nickname;

    // 비밀번호 암호화 X
    public Member toEntity() {
        return Member.builder()
                .memEmail(this.memEmail)
                .memPw(this.password)
                .memAka(this.nickname)
                .role(UserRole.USER)
                .build();
    }

    // 비밀번호 암호화
    public Member toEntity(String encodedPassword) {
        return Member.builder()
                .memEmail(this.memEmail)
                .memPw(encodedPassword)
                .memAka(this.nickname)
                .role(UserRole.USER)
                .build();
    }
}
