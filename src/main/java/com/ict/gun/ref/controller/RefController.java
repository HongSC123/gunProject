package com.ict.gun.ref.controller;

import com.ict.gun.ref.data.dto.RefDto;
import com.ict.gun.ref.data.entity.Ref;
import com.ict.gun.ref.data.entity.RefEx;
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

//    @PostMapping(value = "/insert")
//    public List<RefDto> searchRefEx(@RequestBody List<RefDto> refDto) {
//        return refService.searchRefEx(refDto);
//    }

}
