package com.ict.gun.calorie.service;

import com.ict.gun.calorie.data.dto.CalorieDto;
import com.ict.gun.calorie.data.entity.IngestDiet;
import com.ict.gun.calorie.data.repository.CalorieRepository;
import com.ict.gun.calorie.data.repository.CalorieRepositoryCustomImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CalorieService {

    private final CalorieRepository calorieRepository;
    private final CalorieRepositoryCustomImpl calorieRepositoryCustomImpl;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    //달력 - 원하는 달의 일별 총 칼로리 조회
    @Transactional
    public List<CalorieDto> searchByDayCalorieList(String mem_email, String yyyymm) {

/*        log.info("칼로리 서비스에요 회원 이메일 : " + mem_email + " yyyymmdd " + yyyymmdd);

        // Timestamp를 String으로 변환
        String strStartDate = dateFormat.format(yyyymmdd);

        String yyyymm = strStartDate.substring(0, 5);

        log.info("timestamp 형식을 변환한 String : " + yyyymm);*/

        log.info("칼로리 서비스에요 회원 이메일 : " + mem_email + "  yyyymmdd : " + yyyymm);
        return calorieRepositoryCustomImpl.searchByDayCalorieList(yyyymm, mem_email);

    }

    //선택한 날의 음식과 칼로리 정보 상세보기 페이지 출력
    @Transactional
    public List<CalorieDto> searchDetailDietList(String mem_email, String selection_date){

//        // Timestamp를 String으로 변환
//        String strStartDate = dateFormat.format(selection_date);
//
//        String selected_Date = strStartDate.substring(0, 10);

        log.info("timestamp형식을 변환한 String : " + selection_date);

        return calorieRepositoryCustomImpl.searchDetailDietList(mem_email, selection_date);
    }

    //회원의 일일 권장 칼로리 조회
    @Transactional
    public int searchMemberDailyCalories(String mem_email){
        return calorieRepositoryCustomImpl.searchMemberDailyCalories(mem_email);
    }

    //오늘 먹은 칼로리와 섭취된 영양정보 조회하기
    @Transactional
    public Optional<CalorieDto> searchTodayCalorie(String mem_email, String now_date_time){
        return calorieRepositoryCustomImpl.searchTodayCalorie(mem_email, now_date_time);
    }

    //새로운 음식 등록
    @Transactional
    public IngestDiet insertCalorie(IngestDiet ingestDiet){
        log.info("service의 insert 메소드");
        IngestDiet calorieEntity;
        calorieEntity = IngestDiet.builder()
                .diet_num(ingestDiet.getDiet_num())
                .food_image(ingestDiet.getFood_image())
                .registration_date_ymd(ingestDiet.getRegistration_date_ymd())
                .registration_date_hm(ingestDiet.getRegistration_date_hm())
                .ingest_calorie(ingestDiet.getIngest_calorie())
                .nutrition_num(ingestDiet.getNutrition_num())
                .mem_email(ingestDiet.getMem_email())
                .build();
        return calorieRepository.save(calorieEntity);
    }
}
