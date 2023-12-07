package com.ict.gun.ref.controller;

import com.ict.gun.ref.data.dto.RefDto;
import com.ict.gun.ref.data.entity.Ref;
import com.ict.gun.ref.data.entity.RefEx;
import com.ict.gun.ref.service.RefService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/ref")
@RequiredArgsConstructor
public class RefController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RefService refService;

    @GetMapping(value = "/list/{MEM_EMAIL}")
    public ResponseEntity<List<RefDto>> getRefList(@PathVariable("MEM_EMAIL") String email) {
        List<RefDto> refList = refService.findByMyRefList(email);
        return ResponseEntity.ok(refList);
    }

    @PostMapping(value = "/exList")
    public List<RefDto> searchRefEx(@RequestBody List<RefDto> refDto) {
        return refService.searchRefEx(refDto);
    }

    @PostMapping("/insert")
    public ResponseEntity<String> insertRef(@RequestBody List<RefDto> refDto) {
        logger.info("refDto : {}", refDto);
        try {
            refService.insert(refDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("등록에 실패했습니다.");
        }
        return ResponseEntity.ok("등록 성공.");
    }


}
