package com.ict.gun.ref.data.dto;

import com.ict.gun.ref.data.entity.Ref;
import com.ict.gun.ref.data.entity.RefEx;
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
    private String REF_CODE;
    private String REF_PHOTO;
    private Date REF_SAVE_DATE;
    private String MEM_EMAIL;
    private String REF_EXCODE;
    private String REF_EXNAME;
    private int REF_EXDATE;
    private int REF_QUAN;
    private Date REF_END_DATE;


    public Ref toEntity(RefDto refDto) {
        return Ref.builder()
                .REF_CODE(refDto.getREF_CODE())
                .REF_EXCODE(refDto.getREF_EXCODE())
                .REF_QUAN(refDto.getREF_QUAN())
                .REF_END_DATE(refDto.getREF_END_DATE())
                .build();
    }

    public RefEx toEntityEx(RefDto refDto) {
        return RefEx.builder()
                .REF_EXCODE(refDto.getREF_EXCODE())
                .REF_EXNAME(refDto.getREF_EXNAME())
                .REF_EXDATE(refDto.getREF_EXDATE())
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
