package com.ict.gun.calorie.data.repository;

import com.ict.gun.calorie.data.dto.CalorieDto;
import com.ict.gun.member.entity.MemberOptions;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public interface CalorieRepositoryCustom {

    List<CalorieDto> searchByDayCalorieList(String yyyymm, String mem_email);
    List<CalorieDto> searchDetailDietList(String mem_email, String selected_date);
    int searchMemberDailyCalories(String mem_email);
    Optional<CalorieDto> searchTodayCalorie(String mem_email, String now_date_time);
}