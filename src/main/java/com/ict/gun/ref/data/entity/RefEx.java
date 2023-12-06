package com.ict.gun.ref.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Table(name = "REF_EX")
public class RefEx {
    @Id
    private String REF_EXCODE;
    private String REF_EXNAME;
    private int REF_EXDATE;
}