package com.ict.gun.ref.service;

import com.ict.gun.ref.data.dto.RefDto;
import com.ict.gun.ref.data.entity.Ref;
import com.ict.gun.ref.data.entity.RefPhoto;
import com.ict.gun.ref.data.repository.RefPhotoRepository;
import com.ict.gun.ref.data.repository.RefRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefService {
    private final RefRepository refRepository;
    private final RefPhotoRepository refPhotoRepository;

    public List<RefDto> findByMyRefList(String email) {
        log.info("email : {}", email);
        List<RefDto> list = refRepository.findByMyRefList(email);
        for (RefDto r : list) {
                try {
                    // 이미지 불러오기
                    BufferedImage originalImage = ImageIO.read(new File(r.getREF_PHOTO()));

                    // 이미지 리사이징
                    Image tmp = originalImage.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                    BufferedImage resizedImage = new BufferedImage(40, 40, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g2d = resizedImage.createGraphics();
                    g2d.drawImage(tmp, 0, 0, null);
                    g2d.dispose();

                    // 이미지를 Base64로 인코딩
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(resizedImage, "png", baos);
                    byte[] bytes = baos.toByteArray();
                    String base64Image = Base64.getEncoder().encodeToString(bytes);

                    // Base64 인코딩된 이미지를 저장
                    r.setREF_PHOTO(base64Image);
                    log.info("이미지 저장 : {}",r.getREF_CODE());
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return list;
    }

    public void insert(List<RefDto> refDto) {
        long randomLong = new Random().nextLong();
        refDto.get(0).setREF_CODE(randomLong);
        refDto.get(0).setREF_SAVE_DATE(new Date());
        String encodedString = refDto.get(0).getREF_PHOTO();
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        String fileName = "E:/gun_workspace/gun/src/main/resources/ref/" + randomLong + ".jpg";
        log.info("fileName : {}", fileName);
        try {
            Files.write(Paths.get(fileName), decodedBytes);
            refDto.get(0).setREF_PHOTO(fileName);
        } catch (IOException e) {
            log.error("파일 쓰기 에러러러러", e);
        }

        RefPhoto refPhoto = refDto.get(0).toEntityPhoto(refDto.get(0));
        System.out.println("photo 저장결과 : "+refPhotoRepository.save(refPhoto));
        for (RefDto r : refDto) {
            r.setREF_CODE(refPhoto.getREF_CODE());
            r.setREF_SAVE_DATE(new Date());
            log.info("ranLong : {}",randomLong);
            log.info("findRefCode : {}", r.getREF_CODE());
        }

        for (RefDto r : refDto) {
            Ref ref = r.toEntity(r);
            log.info("ref : {}", ref);
            ref.setREF_NUM(new Random().nextLong());
            log.info("refNum : {}", ref.getREF_NUM());
            Ref saveref = refRepository.save(ref);
            log.info("ref저장 : {}", saveref);
        }
    }

    public RefDto update(RefDto refDto) {
        Ref ref = refDto.toEntity(refDto);
        RefPhoto refPhoto = refDto.toEntityPhoto(refDto);
        refPhotoRepository.save(refPhoto);
        refRepository.save(ref);
        return refDto;
    }

    public void delete(Long refNum) {
        Ref ref = new Ref();
        ref.setREF_NUM(refNum);
        refRepository.delete(ref);
    }

    public void deletePhoto(String refCode) {
        RefPhoto refPhoto = new RefPhoto();
        if (refRepository.findByRefCode(Long.valueOf(refCode)) > 0) {
            refRepository.deleteAllByREF_CODE(Long.valueOf(refCode));
            refPhoto.setREF_CODE(Long.valueOf(refCode));
            refPhotoRepository.delete(refPhoto);
        }else if(refRepository.findByRefCode(Long.valueOf(refCode)) == 0){
            refPhoto.setREF_CODE(Long.valueOf(refCode));
            refPhotoRepository.delete(refPhoto);
        }else if(refPhotoRepository.findPhotoByRefCode(Long.valueOf(refCode)) == 0){
            throw new IllegalArgumentException("삭제할 정보가 없습니다.");
        }
    }
}
