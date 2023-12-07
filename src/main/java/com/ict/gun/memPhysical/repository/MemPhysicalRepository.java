package com.ict.gun.memPhysical.repository;

import com.ict.gun.memPhysical.entity.MemPhysical;
import com.ict.gun.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemPhysicalRepository extends JpaRepository<MemPhysical, Long> {

    MemPhysical findByMemEmail(Member memEmail);

    void deleteByMemEmail(Member member);
}
