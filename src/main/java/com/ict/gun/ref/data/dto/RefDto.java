package com.ict.gun.ref.data.dto;

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
    private int REF_QUAN;
    private Date REF_SAVE_DATE;
    private String MEM_EMAIL;
    private String REF_EXCODE;
    private String REF_EXNAME;
    private int REF_EXDATE;
}
