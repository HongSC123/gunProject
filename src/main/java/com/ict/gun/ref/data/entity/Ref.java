package com.ict.gun.ref.data.entity;


import com.ict.gun.ref.data.dto.RefDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Table(name = "REF")
public class Ref {

    @Id
    @NotNull
    private Long REF_NUM;
    private Long REF_CODE;
    private String REF_NAME;
    private int REF_QUAN;
    private Date REF_END_DATE;

    public RefDto toDto() {
        return RefDto.builder()
                .REF_CODE(this.REF_CODE)
                .REF_NAME(this.REF_NAME)
                .REF_QUAN(this.REF_QUAN)
                .REF_END_DATE(this.REF_END_DATE)
                .REF_NUM(this.REF_NUM)
                .build();
    }
}
