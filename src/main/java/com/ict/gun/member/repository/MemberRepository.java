package com.ict.gun.member.repository;

import com.ict.gun.member.entity.Member;
import com.ict.gun.member.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {

    Optional<Member> findByMemEmail(String username);
    boolean existsByMemEmail(String username);

    List<Member> findByRoleNot(UserRole role);
}
