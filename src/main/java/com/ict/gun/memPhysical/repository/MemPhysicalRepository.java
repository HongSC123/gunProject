package com.ict.gun.memPhysical.repository;

import com.ict.gun.memPhysical.data.entity.MemPhysical;
import com.ict.gun.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemPhysicalRepository extends JpaRepository<MemPhysical, Long> {

    MemPhysical findByMemEmail(Member memEmail);

    void deleteByMemEmail(Member member);
}
