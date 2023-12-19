package com.ict.gun.calorie.service;

import com.ict.gun.calorie.data.dto.CalorieDto;
import com.ict.gun.calorie.data.entity.IngestDiet;
import com.ict.gun.calorie.data.repository.CalorieRepository;
import com.ict.gun.calorie.data.repository.CalorieRepositoryCustomImpl;
import com.ict.gun.ref.data.dto.RefDto;
import com.ict.gun.ref.data.entity.RefPhoto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
    public IngestDiet insertCalorie(CalorieDto calorieDto){

        long randomLong = new Random().nextLong();
//        calorieDto.get(0).setREF_CODE(randomLong);
//        calorieDto.get(0).setRegistration_date_hm(new Date());
        String encodedString = calorieDto.getFood_image();
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        String fileName = "E:/gun_workspace/gun/src/main/resources/food/" + randomLong + ".jpg";
        log.info("fileName : {}", fileName);
        try {
            Files.write(Paths.get(fileName), decodedBytes);
            calorieDto.setFood_image(fileName);
        } catch (IOException e) {
            log.error("파일 쓰기 에러러러러", e);
        }

        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String formattedNow1 = now.format(formatter1);

        String format1 = formattedNow1.substring(0, 10);
        String format2 = formattedNow1.substring(11, 16);

        calorieDto.setRegistration_date_ymd(format1);
        calorieDto.setRegistration_date_hm(format2);


        if(calorieDto.getFood_name().equals("Black_bean_sauce_noodles")){
            calorieDto.setNutrition_num("3");
            float result = calorieRepositoryCustomImpl.searchFoodCalorie("3");
            long myLong = Math.round(result);
            int su = calorieDto.getFood_quan();
            calorieDto.setIngest_calorie(myLong * su);
        } else if (calorieDto.getFood_name().equals("Steamed_rice")) {
            calorieDto.setNutrition_num("1");
            float result = calorieRepositoryCustomImpl.searchFoodCalorie("1");
            long myLong = Math.round(result);
            int su = calorieDto.getFood_quan();
            calorieDto.setIngest_calorie(myLong * su);
        } else if (calorieDto.getFood_name().equals("Beef_radish_soup")) {
            calorieDto.setNutrition_num("5");
            float result = calorieRepositoryCustomImpl.searchFoodCalorie("5");
            long myLong = Math.round(result);
            int su = calorieDto.getFood_quan();
            calorieDto.setIngest_calorie(myLong * su);
        } else if (calorieDto.getFood_name().equals("Curry_Rice")) {
            calorieDto.setNutrition_num("2");
            float result = calorieRepositoryCustomImpl.searchFoodCalorie("2");
            long myLong = Math.round(result);
            int su = calorieDto.getFood_quan();
            calorieDto.setIngest_calorie(myLong * su);
        } else if (calorieDto.getFood_name().equals("Chicken_porridge")) {
            calorieDto.setNutrition_num("4");
            float result = calorieRepositoryCustomImpl.searchFoodCalorie("4");
            long myLong = Math.round(result);
            int su = calorieDto.getFood_quan();
            calorieDto.setIngest_calorie(myLong * su);
        } else if (calorieDto.getFood_name().equals("Seasoned_spinach")) {
            calorieDto.setNutrition_num("8");
            float result = calorieRepositoryCustomImpl.searchFoodCalorie("8");
            long myLong = Math.round(result);
            int su = calorieDto.getFood_quan();
            calorieDto.setIngest_calorie(myLong * su);
        } else if (calorieDto.getFood_name().equals("Stir-fried_Rice_Cake")) {
            calorieDto.setNutrition_num("6");
            float result = calorieRepositoryCustomImpl.searchFoodCalorie("6");
            long myLong = Math.round(result);
            int su = calorieDto.getFood_quan();
            calorieDto.setIngest_calorie(myLong * su);
        } else if (calorieDto.getFood_name().equals("Pork_cutlet")) {
            calorieDto.setNutrition_num("7");
            float result = calorieRepositoryCustomImpl.searchFoodCalorie("7");
            long myLong = Math.round(result);
            int su = calorieDto.getFood_quan();
            calorieDto.setIngest_calorie(myLong * su);
        } else if (calorieDto.getFood_name().equals("Marinated_seasoning_crab")) {
            calorieDto.setNutrition_num("9");
            float result = calorieRepositoryCustomImpl.searchFoodCalorie("9");
            long myLong = Math.round(result);
            int su = calorieDto.getFood_quan();
            calorieDto.setIngest_calorie(myLong * su);
        }else {
            calorieDto.setNutrition_num("10");
            float result = calorieRepositoryCustomImpl.searchFoodCalorie("10");
            long myLong = Math.round(result);
            int su = calorieDto.getFood_quan();
            calorieDto.setIngest_calorie(myLong * su);
        }
//        RefPhoto refPhoto = calorieDto.get(0).toEntityPhoto(calorieDto.get(0));
//        System.out.println("photo 저장결과 : " + CalorieRepository.save(calorieDto));


        log.info("service의 insert 메소드");
        IngestDiet calorieEntity;
        calorieEntity = IngestDiet.builder()
                .diet_num(calorieDto.getDiet_num())
                .food_image(calorieDto.getFood_image())
                .food_quan(calorieDto.getFood_quan())
                .registration_date_ymd(calorieDto.getRegistration_date_ymd())
                .registration_date_hm(calorieDto.getRegistration_date_hm())
                .ingest_calorie(calorieDto.getIngest_calorie())
                .nutrition_num(calorieDto.getNutrition_num())
                .mem_email(calorieDto.getMem_email())
                .build();
        return calorieRepository.save(calorieEntity);
    }
}
