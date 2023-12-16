package com.ict.gun.ref.controller;

import com.ict.gun.ref.data.dto.RefDto;
import com.ict.gun.ref.data.repository.RefRepository;
import com.ict.gun.ref.service.RefService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/ref")
@RequiredArgsConstructor
public class RefController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RefService refService;
    private final RefRepository refRepository;

    @GetMapping(value = "/list/{memEmail}")
    public ResponseEntity<List<RefDto>> getRefList(@PathVariable("memEmail") String email) {
        log.info("email : {}", email);
        List<RefDto> refList = refService.findByMyRefList(email);
        return ResponseEntity.ok(refList);
    }

    @PostMapping("/insert")
    public void insertRef(@RequestBody List<RefDto> refDto) {
        logger.info("exname : {}", refDto.get(0).getREF_NAME());
        logger.info("enddate : {}", refDto.get(0).getREF_END_DATE());
        logger.info("email : {}", refDto.get(0).getMEM_EMAIL());
            refService.insert(refDto);
    }

    @PatchMapping("/update")
    public ResponseEntity<RefDto> updateRef(@RequestBody RefDto refDto) {
        try {
            RefDto updateRef = refService.update(refDto);
            return ResponseEntity.ok(updateRef);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/deleteRef/{REF_NUM}")
    public void deleteRef(@PathVariable("REF_NUM") Long refNum) {
            refService.delete(refNum);
    }

    @DeleteMapping("/deleteRefPhoto/{REF_CODE}")
    public void deleteRefPhoto(@PathVariable("REF_CODE") String refCode) {
        refService.deletePhoto(refCode);
    }
}
