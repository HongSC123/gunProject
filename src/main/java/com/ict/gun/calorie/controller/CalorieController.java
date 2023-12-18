package com.ict.gun.calorie.controller;

import com.ict.gun.calorie.data.dto.CalorieDto;
import com.ict.gun.calorie.data.entity.IngestDiet;
import com.ict.gun.calorie.service.CalorieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RestController
public class CalorieController {


    private final CalorieService calorieService;

/*    @GetMapping("/calorie/{mem_email, selection_date}")
    public ResponseEntity<List<CalorieDto>> getDetailDiet(@PathVariable("mem_email") String email,
                                                          @PathVariable("selection_date") Date SelectionDate){
        log.info("\n\n회원 이메일 : " + email);
        List<CalorieDto> calorieDto = calorieService.findDietByDietList(email, SelectionDate);
        return ResponseEntity.ok(calorieDto);
    }*/



//    const diet_num = 34;
//    const response = axios fetch("http://localhost:8888/calorie/" + diet_num);

/*    const dailyFood = [{
        mem_email: "준우@지메일",
        selection_date: "12"
    }]
    const date = 2023.12.02T06:00 D60423
    date = date.substring(0, 11);
    this.dailyFood.selection_date = date;*/
//    const diet_num = 34;
//    const response = axios fetch("http://localhost:8888/calorie/" + dailyFood); // 여러 시도 코드 주석

    //달력 - 원하는 달의 일별 총 칼로리 조회
    @GetMapping("/caloriedaylist/{mem_email}/{yyyy_mm}")
    public ResponseEntity<List<CalorieDto>> searchDayCalorieList(@PathVariable("mem_email") String mem_email,
                                                                 @PathVariable("yyyy_mm") String yyyy_mm){
        log.info("caloriedaylist 컨트롤러에요 " + mem_email + " " + yyyy_mm);
        List<CalorieDto> calorie = calorieService.searchByDayCalorieList(mem_email, yyyy_mm);
        log.info("caloriedaylist 컨트롤러에서 반환할 객체에요 " + calorie);
        return ResponseEntity.ok(calorie);
    }

    //선택한 날의 음식과 칼로리 정보 상세보기 페이지 출력
    @GetMapping("/caloriedetail/{mem_email}/{selection_date}")
    public ResponseEntity<List<CalorieDto>> searchDetailDietList(@PathVariable("mem_email") String mem_email,
                                                                 @PathVariable("selection_date") String selection_date) {
        log.info("caloriedetail 컨트롤러에요 " + mem_email + " " + selection_date);
        List<CalorieDto> calorie = calorieService.searchDetailDietList(mem_email, selection_date);
        log.info("caloriedetail 컨트롤러에서 반환할 객체에요 " + calorie);
        return ResponseEntity.ok(calorie);
    }

    //회원의 일일 섭취 권장 칼로리 조회
    @GetMapping("/caloriedaycalorie/{mem_email}")
    public int searchMemberDailyCalories(@PathVariable("mem_email") String mem_email){
        int memberOptions = calorieService.searchMemberDailyCalories(mem_email);
        return memberOptions;
    }

    //오늘 먹은 칼로리와 섭취된 영양정보 조회하기
    @GetMapping("/calorietoday/{mem_email}")
    public Optional<CalorieDto> searchTodayCalorie(@PathVariable("mem_email") String mem_email){
        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String formattedNow1 = now.format(formatter1);

        String now_date_time = formattedNow1.substring(2, 8);

        return calorieService.searchTodayCalorie(mem_email, now_date_time);

    }

    //새로운 음식 등록
    @PostMapping("/calorieinsert")
    public ResponseEntity<?> insertCalorie(@RequestBody CalorieDto calorieDto) {
//        LocalDateTime now = LocalDateTime.now();
//
//        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//
//        String formattedNow1 = now.format(formatter1);
//
//        String format1 = formattedNow1.substring(0, 10);
//        String format2 = formattedNow1.substring(11, 16);
//
//        log.info("음식 저장할때 년월일 형식 String : " + format1);
//
//        log.info("음식 저장할때 시간 형식 String : " + format2);
        log.info("controller : " + calorieDto.getFood_quan());
        try {
//            calorieDto.setRegistration_date_ymd(format1);
//            calorieDto.setRegistration_date_hm(format2);
            IngestDiet insertCalorie = calorieService.insertCalorie(calorieDto);
            return new ResponseEntity<>(insertCalorie, HttpStatus.CREATED);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    /*//칼로리 수정
    @PatchMapping("/calroieupdate")
    public int updateCalorie(@RequestBody IngestDiet ingestDiet) {

        return Integer                                                                                               updateResult = calorieService.updateCalorie(ingestDiet);
    }*/

}
