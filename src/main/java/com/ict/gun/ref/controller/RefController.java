package com.ict.gun.ref.controller;

import com.ict.gun.ref.data.dto.RefDto;
import com.ict.gun.ref.service.RefService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ref")
@RequiredArgsConstructor
public class RefController {

    private final RefService refService;

    @GetMapping(value = "/list/{MEM_EMAIL}")
    public ResponseEntity<List<RefDto>> getRefList(@PathVariable("MEM_EMAIL") String email) {
        List<RefDto> refList = refService.findByMyRefList(email);
        return ResponseEntity.ok(refList);
    }

//    @PutMapping(value = "/insert")
//    public ResponseEntity<RefDto> insertRef(@RequestBody RefDto refDto) {
//        RefDto insertedRefDto = refService.insertRef(refDto);
//        return ResponseEntity.ok(insertedRefDto);
//    }

}
