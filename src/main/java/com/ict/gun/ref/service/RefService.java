package com.ict.gun.ref.service;

import com.ict.gun.ref.data.dto.RefDto;
import com.ict.gun.ref.data.entity.Ref;
import com.ict.gun.ref.data.entity.RefEx;
import com.ict.gun.ref.data.entity.RefPhoto;
import com.ict.gun.ref.data.repository.RefExRepository;
import com.ict.gun.ref.data.repository.RefPhotoRepository;
import com.ict.gun.ref.data.repository.RefRepository;
import com.ict.gun.ref.data.repository.RefRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefService {
    private final RefRepository refRepository;
    private final RefExRepository refExRepository;
    private final RefPhotoRepository refPhotoRepository;

    public List<RefDto> findByMyRefList(String email) {
        return refRepository.findByMyRefList(email);
    }

    public List<RefDto> searchRefEx(List<RefDto> refDto) {
        for (RefDto r : refDto) {
            LocalDate endDate = LocalDate.now().plusDays(refExRepository.findByRefExCode(r.getREF_EXCODE()));
            r.setREF_END_DATE(Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        }
        return refDto;
    }

    public void insert(List<RefDto> refDto) {
        RefPhoto refPhoto = refDto.get(0).toEntityPhoto(refDto.get(0));
        log.info("refPhoto : {}", refPhoto);
        System.out.println("photo 저장결과 : "+refPhotoRepository.save(refPhoto));
        for (RefDto r : refDto) {
            r.setREF_CODE(refPhotoRepository.findRefCode());
            log.info("findRefCode : {}", r.getREF_CODE());
        }
        log.info("for문 돌기 전 refDto : {}", refDto);
        for (RefDto r : refDto) {
            Ref ref = r.toEntity(r);
            Random random = new Random();
            ref.setREF_NUM(random.nextLong());
            Ref saveref = refRepository.save(ref);
            log.info("ref저장 : {}", saveref);
        }
    }

    public RefDto update(RefDto refDto) {
        Ref ref = refDto.toEntity(refDto);
        RefEx refEx = refDto.toEntityEx(refDto);
        RefPhoto refPhoto = refDto.toEntityPhoto(refDto);
        refPhotoRepository.save(refPhoto);
        refRepository.save(ref);
        refExRepository.save(refEx);
        return refDto;
    }

    public void delete(Long refNum) {
        Ref ref = new Ref();
        ref.setREF_NUM(refNum);
        refRepository.delete(ref);
    }

    public void deletePhoto(String refCode) {
        RefPhoto refPhoto = new RefPhoto();
        if (refRepository.findByRefCode(refCode) > 0) {
            refRepository.deleteAllByREF_CODE(refCode);
            refPhoto.setREF_CODE(refCode);
            refPhotoRepository.delete(refPhoto);
        }else if(refRepository.findByRefCode(refCode) == 0){
            refPhoto.setREF_CODE(refCode);
            refPhotoRepository.delete(refPhoto);
        }else if(refPhotoRepository.findPhotoByRefCode(refCode) == 0){
            throw new IllegalArgumentException("삭제할 정보가 없습니다.");
        }
    }
}
