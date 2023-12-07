package com.ict.gun.memPhysical.controller;

import com.ict.gun.memPhysical.entity.MemPhysical;
import com.ict.gun.memPhysical.service.MemPhysicalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RestController
public class MemPhysicalController {
    private final MemPhysicalService memPhysicalService;

    @PostMapping("/memPhysical-insert") //측정 정보 저장
    public ResponseEntity<String> memPhysicalInsert(@RequestBody MemPhysical memPhysical) {
        try {
            memPhysicalService.memPhysicalInsert(memPhysical);
            String result = "저장 성공";
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            String result = "저장 실패";
            return ResponseEntity.status(500).body(result);
        }
    }

    @GetMapping("/memPhysical-detail/{MEM_EMAIL}") //상세보기
    public ResponseEntity<MemPhysical> memPhysicalDetail(@PathVariable("MEM_EMAIL") String email) {
        MemPhysical memPhysical = memPhysicalService.findByMemEmail(email);

        // 만약 해당 이메일로 찾은 정보가 없을 경우 NOT_FOUND 상태 코드를 반환할 수 있습니다.
        if (memPhysical == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(memPhysical, HttpStatus.OK);
    }

    @DeleteMapping("/memPhysical-delete/{MEM_EMAIL}") //측정 정보 삭제하기
    public ResponseEntity<String> memPhysicalDelete(@PathVariable("MEM_EMAIL") String email) {
        try {
            memPhysicalService.memPhysicalDelete(email);
            String result = "측정 정보 삭제";
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            String result = "측정 정보 삭제 실패";
            return ResponseEntity.status(500).body(result);
        }

    }

}
