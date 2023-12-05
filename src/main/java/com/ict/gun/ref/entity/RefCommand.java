package com.ict.gun.ref.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefCommand {
    private String refCode;
    private String refName;
    private int refQuan;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String refEndDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String refSaveDate;
    private String memEmail;
    private String memName;
    private String memPhoto;
    private String mem_type;
    private String memAct;
}

