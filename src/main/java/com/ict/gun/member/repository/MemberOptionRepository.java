package com.ict.gun.member.repository;

import com.ict.gun.member.entity.MemberOptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberOptionRepository extends JpaRepository<MemberOptions, String> {
}
