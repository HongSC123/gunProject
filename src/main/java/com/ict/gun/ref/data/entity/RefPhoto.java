package com.ict.gun.ref.data.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ict.gun.ref.data.dto.RefDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Builder
//@SequenceGenerator(name = "sequence_generator", sequenceName = "REF_SEQUENCE", allocationSize = 1)
@Table(name = "REF_PHOTO")
public class RefPhoto {

//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_generator")
    @Id
    private String REF_CODE;
    private String REF_PHOTO;
    private Date REF_SAVE_DATE;
    private String MEM_EMAIL;

    public RefDto toDto() {
        return RefDto.builder()
                .REF_CODE(this.REF_CODE)
                .REF_PHOTO(this.REF_PHOTO)
                .REF_SAVE_DATE(this.REF_SAVE_DATE)
                .MEM_EMAIL(this.MEM_EMAIL)
                .build();
    }
}
