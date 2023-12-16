package com.ict.gun.ref.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ict.gun.ref.data.entity.Ref;
import com.ict.gun.ref.data.entity.RefPhoto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefDto {

    private Long REF_CODE;

    @JsonProperty("refPhoto")
    private String REF_PHOTO;

    private Date REF_SAVE_DATE;

    @JsonProperty("memEmail")
    private String MEM_EMAIL;

    @JsonProperty("refName")
    private String REF_NAME;

    @JsonProperty("refQuan")
    private int REF_QUAN;

    @JsonProperty("refEndDate")
    private Date REF_END_DATE;

    private Long REF_NUM;


    public Ref toEntity(RefDto refDto) {
        return Ref.builder()
                .REF_CODE(refDto.getREF_CODE())
                .REF_NAME(refDto.getREF_NAME())
                .REF_QUAN(refDto.getREF_QUAN())
                .REF_END_DATE(refDto.getREF_END_DATE())
                .REF_NUM(refDto.getREF_NUM())
                .build();
    }

    public RefPhoto toEntityPhoto(RefDto refDto) {
        return RefPhoto.builder()
                .REF_CODE(refDto.getREF_CODE())
                .REF_PHOTO(refDto.getREF_PHOTO())
                .REF_SAVE_DATE(refDto.getREF_SAVE_DATE())
                .MEM_EMAIL(refDto.getMEM_EMAIL())
                .build();
    }
}
