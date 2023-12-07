package com.ict.gun.ref.data.repository;

import com.ict.gun.ref.data.entity.RefEx;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefExRepository extends JpaRepository<RefEx, String>, RefRepositoryCustom {

}
