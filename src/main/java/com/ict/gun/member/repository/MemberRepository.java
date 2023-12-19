package com.ict.gun.member.repository;

import com.ict.gun.member.entity.Member;
import com.ict.gun.member.entity.MemberCommand;
import com.ict.gun.member.entity.UserRole;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {

    Optional<Member> findByMemEmail(String username);
    boolean existsByMemEmail(String username);

    List<Member> findByRoleNot(UserRole role);

    @Query("SELECT m FROM Member m LEFT JOIN MemberOptions n ON m.memEmail = n.memEmail WHERE m.memType <> 'ADMIN'")
    List<MemberCommand> findByRoleNotAdmin();


}
