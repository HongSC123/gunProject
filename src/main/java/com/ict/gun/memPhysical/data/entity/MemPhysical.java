package com.ict.gun.memPhysical.data.entity        ;

import com.ict.gun.memPhysical.data.dto.MemPhysicalDto;
import com.ict.gun.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

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
    private String memLocation; // 신체 측정 방향 (정면,측면)
    private String memPoint; //신체 정보
    private Long memCount; // 신체 정보 카운트
    private LocalDate memInputDate; //입력 날짜
    private LocalDate memDeleteDate; //삭제 날짜
    private String memPhysicalAct; //활성화 여부
    private Long memPhysicalSet; //정면-측면 페어
    @ManyToOne
    @JoinColumn(name = "MEM_EMAIL")
    private Member memEmail; //회원 이메일

    public MemPhysicalDto toDto() {
        return MemPhysicalDto.builder()
                .memLocation(this.memLocation)
                .memPoint(this.memPoint)
                .memCount(this.memCount)
                .memInputDate(this.memInputDate)
                .memDeleteDate(this.memDeleteDate)
                .memPhysicalAct(this.memPhysicalAct)
                .memPhysicalSet(this.memPhysicalSet)
                .build();
    }

}
