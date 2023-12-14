package com.ict.gun.calorie.data.entity;

import com.ict.gun.calorie.data.dto.CalorieDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Table(name = "NUTRITION_FACT")
public class NutritionFact {
    @Id
    @Column(name = "NUTRITION_NUM")
    private String nutrition_num;

    @Column(name = "FOOD_NAME")
    private String food_name;

    @Column(name = "WEIGHT")
    private Float weight;

    @Column(name = "KCAL")
    private Float kcal;

    @Column(name = "CARBOHYDRATE")
    private Float carbohydrate;

    @Column(name = "SUGAR")
    private Float sugar;

    @Column(name = "FAT")
    private Float fat;

    @Column(name = "PROTEIN")
    private Float protein;

    @Column(name = "CALCIUM")
    private Float calcium;

    @Column(name = "PHOSPHORUS")
    private Float phosphorus;

    @Column(name = "SODIUM")
    private Float sodium;

    @Column(name = "POTASSIUM")
    private Float potassium;

    @Column(name = "MAGNESIUM")
    private Float magnesium;

    @Column(name = "IRON")
    private Float iron;

    @Column(name = "ZINC")
    private Float zinc;

    @Column(name = "CHOLESTEROL")
    private Float cholesterol;

    @Column(name = "TRANS_FAT")
    private Float trans_fat;

    public CalorieDto toDto() {
        return CalorieDto.builder()
                .nutrition_num(this.nutrition_num)
                .food_name(this.food_name)
                .weight(this.weight)
                .kcal(this.kcal)
                .carbohydrate(this.carbohydrate)
                .sugar(this.sugar)
                .fat(this.fat)
                .protein(this.protein)
                .calcium(this.calcium)
                .phosphorus(this.phosphorus)
                .sodium(this.sodium)
                .potassium(this.potassium)
                .magnesium(this.magnesium)
                .iron(this.iron)
                .zinc(this.zinc)
                .cholesterol(this.cholesterol)
                .trans_fat(this.trans_fat)
                .build();
    }
}
