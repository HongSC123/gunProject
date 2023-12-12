package com.ict.gun.calorie.data.entity;

import com.ict.gun.calorie.data.dto.CalorieDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Formula;

import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "INGEST_DIET")
@Builder
@SequenceGenerator(name = "DIET_SEQ", sequenceName = "diet_seq", allocationSize = 1)
public class IngestDiet {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIET_SEQ")
    @Column(name = "DIET_NUM")
    private Long diet_num;

    @Column(name = "FOOD_IMAGE")
    private String food_image;

    @Column(name = "REGISTRATION_DATE_YMD")
    private String registration_date_ymd;

    @Column(name = "REGISTRATION_DATE_HM")
    private String registration_date_hm;

    @Column(name = "INGEST_CALORIE")
    private Long ingest_calorie;

    @Column(name = "MEM_EMAIL")
    private String mem_email;

    @Column(name = "NUTRITION_NUM")
    private String nutrition_num;

    public CalorieDto toDto() {
        return CalorieDto.builder()
                .diet_num(this.diet_num)
                .food_image(this.food_image)
                .registration_date_ymd(this.registration_date_ymd)
                .registration_date_hm(this.registration_date_hm)
                .ingest_calorie(this.ingest_calorie)
                .mem_email(this.mem_email)
                .nutrition_num(this.nutrition_num)
                .build();
    }
}
