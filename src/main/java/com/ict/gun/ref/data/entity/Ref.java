package com.ict.gun.ref.data.entity;


import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_generator")
    @SequenceGenerator(name = "sequence_generator", sequenceName = "REF_SEQUENCE", allocationSize = 1)
    private String REF_CODE;
    private int REF_QUAN;
    private Date REF_SAVE_DATE;
    private String MEM_EMAIL;
    private String REF_EXCODE;

    @PrePersist
    public void addRefPrefix() {
        this.REF_CODE = "REF" + this.REF_CODE;
    }
}
