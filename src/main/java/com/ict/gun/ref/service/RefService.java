package com.ict.gun.ref.service;

import com.ict.gun.ref.data.dto.RefDto;
import com.ict.gun.ref.data.entity.Ref;
import com.ict.gun.ref.data.entity.RefEx;
import com.ict.gun.ref.data.repository.RefExRepository;
import com.ict.gun.ref.data.repository.RefRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RefService {
    private final RefRepository refRepository;
    private final RefExRepository refExRepository;

    public List<RefDto> findByMyRefList(String email) {
        return refRepository.findByMyRefList(email);
    }

//    public List<RefDto> searchRefEx(List<RefDto> refDto) {
//        for (RefDto r : refDto) {
//            Calendar calendar = Calendar.getInstance();
//            calendar.add(Calendar.DATE, refRepository.findByRefExDate(r.getREF_EXCODE()));
//            Date exDate = calendar.getTime();
//            r.setREF_EXDATE(exDate);
//            return null;
//        }


//    public Ref insert(RefDto refDto) {
//        Ref entity = null;
//
//        return null;
//    }

//    public RefEx insertRefEx(RefDto refDto) {
//        RefEx entity = RefEx.builder()
//        .REF_EXCODE(refDto.getREF_EXCODE())
//        .REF_EXNAME(refDto.getREF_EXNAME())
//        .REF_EXDATE(refDto.getREF_EXDATE())
//        .build();
//        return refExRepository.save(entity);
//    }

}
