package com.ict.gun.memPhysical.data.entity        ;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.ict.gun.member.entity.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.sql.Date;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Table(name = "MEM_PHYSICAL")
public class MemPhysical {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mem_physical_seq_generator")
    @SequenceGenerator(name = "mem_physical_seq_generator", sequenceName = "mem_physical_seq", allocationSize = 1)
    @Column(name = "MEM_PHYSICAL")
    private long memPhysical; // 번호

    @Column(name = "MEM_LOCATION", length = 20)
    private String memLocation; // 신체 측정 방향 (정면,측면)

    @Column(name = "MEM_POINT", length = 1500)
    private String memPoint; //신체 정보

    @Column(name = "MEM_COUNT")
    private Long memCount; // 신체 정보 카운트

    @Column(name = "MEM_INPUT_DATE")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date memInputDate; //입력 날짜

    @Column(name = "MEM_DELETE_DATE")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date memDeleteDate; //삭제 날짜

    @Column(name = "MEM_PHYSICAL_ACT", length = 10)
    @Size(max = 10)
    private String memPhysicalAct; //활성화 여부

    @Column(name = "MEM_PHYSICAL_SET")
    private Long memPhysicalSet; //정면-측면 페어


    @Column(name = "MEM_EMAIL")
    private String memEmail; //회원 이메일


}
