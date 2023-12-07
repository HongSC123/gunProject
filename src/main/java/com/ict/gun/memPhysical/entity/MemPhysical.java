package com.ict.gun.memPhysical.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ict.gun.member.entity.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="mem_physical")
@Entity
public class MemPhysical {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mem_physical_seq_generator")
    @SequenceGenerator(name = "mem_physical_seq_generator", sequenceName = "mem_physical_seq", allocationSize = 1)
    @Column(name = "MEM_PHYSICAL")
    private long memPhysical; // 번호

    @Size(max = 20)
    @NotNull
    @Column(name = "MEM_LOCATION", nullable = false, length = 20)
    private String memLocation; // 신체 측정 방향 (정면,측면)

    @Size(max = 1500)
    @Column(name = "MEM_POINT", length = 1500)
    private String memPoint; //신체 정보

    @Column(name = "MEM_COUNT")
    private Long memCount; // 신체 정보 카운트

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "MEM_INPUT_DATE")
    private LocalDate memInputDate; //입력 날짜

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "MEM_DELETE_DATE")
    private LocalDate memDeleteDate; //삭제 날짜

    @Size(max = 10)
    @Column(name = "MEM_PHYSICAL_ACT", length = 10)
    private String memPhysicalAct; //활성화 여부

    @Column(name = "MEM_PHYSICAL_SET")
    private Long memPhysicalSet; //정면-측면 페어

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "MEM_EMAIL")
    private Member memEmail; //회원 이메일

}
