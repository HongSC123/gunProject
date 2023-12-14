package com.ict.gun.chat.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class FridgeInfoRequest {
    public String mem_email;
    public String REF_EXNAME;
    public Date REF_END_DATE;
    public String REF_CODE;

}
