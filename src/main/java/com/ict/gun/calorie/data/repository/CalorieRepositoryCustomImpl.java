
package com.ict.gun.calorie.data.repository;

import com.ict.gun.calorie.data.dto.CalorieDto;
import com.ict.gun.calorie.data.entity.QIngestDiet;
import com.ict.gun.calorie.data.entity.QNutritionFact;
import com.ict.gun.member.entity.QMember;
import com.ict.gun.member.entity.QMemberOptions;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class CalorieRepositoryCustomImpl implements CalorieRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    QIngestDiet id = QIngestDiet.ingestDiet;
    QNutritionFact nf = QNutritionFact.nutritionFact;
    QMember m = QMember.member;
    QMemberOptions mo = QMemberOptions.memberOptions;

    String formattedDate = "YYYY-MM-DD";
    /*@Override
    public List<CalorieDto> searchByDayCalorieList(Timestamp startdate, Timestamp enddate, String mem_email) {
        log.info("칼로리 레포지토리Impl이에요 회원 이메일 : " + mem_email + "  시작날 " + startdate + " 끝나는날 " + enddate);
        String formattedDate = "YYYY-MM-DD HH";
        JPAQuery<Tuple> query = queryFactory
                .select(Expressions.stringTemplate("TO_DATE(TO_CHAR({0}, {1}), {2})", id.registration_date, formattedDate, formattedDate),

                        id.ingest_calorie.sum()
                )
                .from(id)
                .where(Expressions.stringTemplate("TO_DATE(TO_CHAR({0}, {1}), {2})", id.registration_date, formattedDate, formattedDate)
                        .between(Expressions.stringTemplate("TO_DATE(TO_CHAR({0}, {1}), {2})", startdate, formattedDate, formattedDate),
                                Expressions.stringTemplate("TO_DATE(TO_CHAR({0}, {1}), {2})", enddate, formattedDate, formattedDate))
                        .and(id.mem_email.eq(mem_email)))
                .groupBy(id.mem_email, id.registration_date);
        log.info("칼로리 레포지토리Impl이에요 회원 이메일 : " + mem_email + "  시작날 " + startdate + " 끝나는날 " + enddate);
        return query.fetch().stream().map(tupple -> CalorieDto.builder()
                .registration_date(tupple.get(id.registration_date))
                .ingest_calorie(tupple.get(id.ingest_calorie.sum()))
                .build()).toList();
    }*/ //여러 시도 코드 주석

    //달력 - 원하는 달의 일별 총 칼로리 조회
    @Override
    public List<CalorieDto> searchByDayCalorieList(String yyyymm, String mem_email) {

        log.info("칼로리 레포지토리Impl이에요 회원 이메일 : " + mem_email + "  yyyymm : " + yyyymm);
        JPAQuery<Tuple> query = queryFactory
                .select(id.registration_date_ymd,
                        id.ingest_calorie.sum()
                )
                .from(id)
                .where(id.mem_email.eq(mem_email).and(id.registration_date_ymd.like("%" + yyyymm + "%")))
                .groupBy(id.registration_date_ymd);

        return query.fetch().stream().map(tupple -> CalorieDto.builder()
                .registration_date_ymd(tupple.get(id.registration_date_ymd))
                .ingest_calorie(tupple.get(id.ingest_calorie.sum()))
                .build()).toList();
        /*// Timestamp를 LocalDateTime으로 변환
        LocalDateTime dateTime1 = startdate.toLocalDateTime();
        LocalDateTime dateTime2 = enddate.toLocalDateTime();

        // LocalDateTime을 yyyy-MM-dd 형식으로 포맷12
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDateTime1 = dateTime1.format(formatter);
        String formattedDateTime2 = dateTime2.format(formatter);

        // 포맷된 문자열을 LocalDate로 파싱
        LocalDate parsedDate1 = LocalDate.parse(formattedDateTime1, formatter);
        LocalDate parsedDate2 = LocalDate.parse(formattedDateTime2, formatter);

        // LocalDate를 LocalDateTime으로 변환 (시간은 자정 00:00으로 설정)
        LocalDateTime parsedDateTime1 = parsedDate1.atStartOfDay();
        LocalDateTime parsedDateTime2 = parsedDate2.atStartOfDay();

        // LocalDateTime을 다시 Timestamp로 변환
        Timestamp startday = Timestamp.valueOf(parsedDateTime1);
        Timestamp endday = Timestamp.valueOf(parsedDateTime2);*/
// Get the Hibernate session from the EntityManager
       /* Session session = entityManager.unwrap(Session.class);

        // Create a HibernateQueryFactory
        HibernateQueryFactory queryFactory = new HibernateQueryFactory(session);

        // Register the TRUNC function
        queryFactory.registerSQLFunction("trunc", new SQLFunctionTemplate(StandardBasicTypes.DATE, "trunc(?1)"));*/ //여러 시도 코드 주석

    }

    //선택한 날의 음식과 칼로리 정보 상세보기 페이지 출력
    @Override
    public List<CalorieDto> searchDetailDietList(String mem_email, String selected_date){

        JPAQuery<Tuple> query = queryFactory
                .select(
                        id.diet_num,
                        id.food_image,
                        id.registration_date_ymd,
                        id.registration_date_hm,
                        id.ingest_calorie,
                        id.mem_email,
                        id.nutrition_num,
                        nf.nutrition_num,
                        nf.food_name,
                        nf.weight,
                        nf.kcal,
                        nf.carbohydrate,
                        nf.sugar,
                        nf.fat,
                        nf.protein,
                        nf.calcium,
                        nf.phosphorus,
                        nf.sodium,
                        nf.potassium,
                        nf.magnesium,
                        nf.iron,
                        nf.zinc,
                        nf.cholesterol,
                        nf.trans_fat)
                .from(id)
                .leftJoin(nf).on(id.nutrition_num.eq(nf.nutrition_num))
                .leftJoin(m).on(id.mem_email.eq(m.memEmail))
                .where(id.mem_email.eq(mem_email).and(id.registration_date_ymd.eq(selected_date)));

        return query.fetchJoin().fetch().stream().map(tupple -> CalorieDto.builder()
                .diet_num(tupple.get(id.diet_num))
                .food_image(tupple.get(id.food_image))
                .registration_date_ymd(tupple.get(id.registration_date_ymd))
                .registration_date_hm(tupple.get(id.registration_date_hm))
                .ingest_calorie(tupple.get(id.ingest_calorie))
                .mem_email(tupple.get(id.mem_email))
                .nutrition_num(tupple.get(id.nutrition_num))
                .nutrition_num(tupple.get(nf.nutrition_num))
                .food_name(tupple.get(nf.food_name))
                .weight(tupple.get(nf.weight))
                .kcal(tupple.get(nf.kcal))
                .carbohydrate(tupple.get(nf.carbohydrate))
                .sugar(tupple.get(nf.sugar))
                .fat(tupple.get(nf.fat))
                .protein(tupple.get(nf.protein))
                .calcium(tupple.get(nf.calcium))
                .phosphorus(tupple.get(nf.phosphorus))
                .sodium(tupple.get(nf.sodium))
                .potassium(tupple.get(nf.potassium))
                .magnesium(tupple.get(nf.magnesium))
                .iron(tupple.get(nf.iron))
                .zinc(tupple.get(nf.zinc))
                .cholesterol(tupple.get(nf.cholesterol))
                .trans_fat(tupple.get(nf.trans_fat))
                .build()).toList();

    }

    //회원의 일일 권장 칼로리 조회
    @Override
    public int searchMemberDailyCalories(String mem_email){
        Integer query = queryFactory
                .select(mo.mem_reco_daily_calories)
                .from(m)
                .leftJoin(mo).on(m.memEmail.eq(mo.memEmail))
                .where(m.memEmail.eq(mem_email))
                .fetchFirst();
        return query;
    }

    //오늘 먹은 칼로리와 섭취된 영양정보 조회하기
    @Override
    public Optional<CalorieDto> searchTodayCalorie(String mem_email, String now_date_time) {
        log.info("IMPL로 오는 String 날짜 값 : " + now_date_time);
        now_date_time = "23/12/05";
        Tuple tupple = queryFactory
                .select(
                        id.ingest_calorie.sum(), nf.carbohydrate.sum(), nf.protein.sum(), nf.fat.sum()
                )
                .from(id)
                .leftJoin(nf).on(id.nutrition_num.eq(nf.nutrition_num))
                .leftJoin(m).on(id.mem_email.eq(m.memEmail))
                .where(id.registration_date_ymd.eq(now_date_time).and(id.mem_email.eq(mem_email)))
                .fetchOne();

        if (tupple != null){
            return Optional.of(CalorieDto.builder()
                    .ingest_calorie(tupple.get(id.ingest_calorie.sum()))
                    .carbohydrate(tupple.get(nf.carbohydrate.sum()))
                    .protein(tupple.get(nf.protein.sum()))
                    .fat(tupple.get(nf.fat.sum()))
                    .build());
        }else {
            return Optional.empty();
        }
    }
}
