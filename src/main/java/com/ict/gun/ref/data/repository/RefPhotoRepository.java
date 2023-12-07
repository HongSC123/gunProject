package com.ict.gun.ref.data.repository;

import com.ict.gun.ref.data.entity.RefPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefPhotoRepository extends JpaRepository<RefPhoto, String>, RefRepositoryCustom {
}
