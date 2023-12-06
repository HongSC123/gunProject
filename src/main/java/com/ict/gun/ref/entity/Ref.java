package com.ict.gun.ref.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "ref")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Ref {
    @Id
    @NotBlank
    @Column(name="ref_code")
    private String refCode;

    @Column(name="ref_name")
    private String refName;

    @Column(name="ref_quan")
    private int refQuan;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name="ref_end_date")
    private String refEndDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name="ref_save_date")
    private String refSaveDate;

    @Column(name="mem_email")
    private String memEmail;

}