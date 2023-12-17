package com.ict.gun.calorie.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ict.gun.calorie.data.entity.IngestDiet;
import com.ict.gun.calorie.data.entity.NutritionFact;
import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CalorieDto {

    private Long diet_num;
    @JsonProperty("foodPhoto")
    private String food_image;
    @JsonProperty("foodQuan")
    private int food_quan;
    private String registration_date_ymd;
    private String registration_date_hm;
    private Long ingest_calorie;
    @JsonProperty("mem_email")
    private String mem_email;
    private String nutrition_num;
    @JsonProperty("foodName")
    private String food_name;
    private Float weight;
    private Float kcal;
    private Float carbohydrate;
    private Float sugar;
    private Float fat;
    private Float protein;
    private Float calcium;
    private Float phosphorus;
    private Float sodium;
    private Float potassium;
    private Float magnesium;
    private Float iron;
    private Float zinc;
    private Float cholesterol;
    private Float trans_fat;

    public IngestDiet toEntityIngest(IngestDiet ingestDiet) {
        return IngestDiet.builder()
                .diet_num(ingestDiet.getDiet_num())
                .food_image(ingestDiet.getFood_image())
                .food_quan(ingestDiet.getFood_quan())
                .registration_date_ymd(ingestDiet.getRegistration_date_ymd())
                .registration_date_hm(ingestDiet.getRegistration_date_hm())
                .ingest_calorie(ingestDiet.getIngest_calorie())
                .nutrition_num(ingestDiet.getNutrition_num())
                .mem_email(ingestDiet.getMem_email())
                .build();
    }

    public NutritionFact toEntityNutrition(NutritionFact nutritionFact) {
        return NutritionFact.builder()
                .nutrition_num(nutritionFact.getNutrition_num())
                .food_name(nutritionFact.getFood_name())
                .weight(nutritionFact.getWeight())
                .kcal(nutritionFact.getKcal())
                .carbohydrate(nutritionFact.getCarbohydrate())
                .sugar(nutritionFact.getSugar())
                .fat(nutritionFact.getFat())
                .protein(nutritionFact.getProtein())
                .calcium(nutritionFact.getCalcium())
                .phosphorus(nutritionFact.getPhosphorus())
                .sodium(nutritionFact.getSodium())
                .potassium(nutritionFact.getPotassium())
                .magnesium(nutritionFact.getMagnesium())
                .iron(nutritionFact.getIron())
                .zinc(nutritionFact.getZinc())
                .cholesterol(nutritionFact.getCholesterol())
                .trans_fat(nutritionFact.getTrans_fat())
                .build();
    }
}
