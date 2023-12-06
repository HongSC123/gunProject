package com.ict.gun.ref.data.repository;

import com.ict.gun.ref.data.entity.Ref;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefRepository extends JpaRepository<Ref, String>, RefRepositoryCustom {
}
