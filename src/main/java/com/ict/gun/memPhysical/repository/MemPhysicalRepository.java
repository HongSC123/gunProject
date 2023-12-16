package com.ict.gun.memPhysical.repository;

import com.ict.gun.memPhysical.data.entity.MemPhysical;
import com.ict.gun.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemPhysicalRepository extends JpaRepository<MemPhysical, Long> {

    List<MemPhysical> findByMemEmail(@Param("memEmail") String memEmail);



    @Query(value = "SELECT * FROM (SELECT * FROM mem_physical " +
            "WHERE mem_email = :memEmail " +
            "ORDER BY mem_physical DESC) " +
            "WHERE ROWNUM = 1", nativeQuery = true)
    MemPhysical findTopByMemEmailOrderByMemPhysicalDesc(@Param("memEmail") String memEmail);




    void deleteByMemPhysical(Long memPhysical);


    List<MemPhysical> findAllByMemEmail(String memEmail);
}
