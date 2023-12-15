package com.ict.gun.calorie.data.repository;

import com.ict.gun.calorie.data.dto.CalorieDto;
import com.ict.gun.calorie.data.entity.IngestDiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CalorieRepository extends JpaRepository<IngestDiet, String>, CalorieRepositoryCustom {
}
