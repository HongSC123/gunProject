package com.ict.gun.ref.service;

import com.ict.gun.ref.data.dto.RefDto;
import com.ict.gun.ref.data.entity.Ref;
import com.ict.gun.ref.data.entity.RefEx;
import com.ict.gun.ref.data.entity.RefPhoto;
import com.ict.gun.ref.data.repository.RefExRepository;
import com.ict.gun.ref.data.repository.RefPhotoRepository;
import com.ict.gun.ref.data.repository.RefRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RefService {
    private final RefRepository refRepository;
    private final RefExRepository refExRepository;
    private final RefPhotoRepository refPhotoRepository;
    Logger logger = LoggerFactory.getLogger(this.getClass());

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
        refPhoto.setREF_CODE("REF_SEQUENCE.nextval");
        logger.info("refPhoto : {}", refPhoto);
        System.out.println("photo 저장결과 : "+refPhotoRepository.save(refPhoto));
        for (RefDto r : refDto) {
            Ref ref = r.toEntity(r);
            refRepository.save(ref);
        }
    }
}
