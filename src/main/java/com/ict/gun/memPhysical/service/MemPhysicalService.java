package com.ict.gun.memPhysical.service;

import com.ict.gun.common.FileHandler;
import com.ict.gun.memPhysical.data.entity.MemPhysical;
import com.ict.gun.memPhysical.repository.MemPhysicalRepository;
import com.ict.gun.member.entity.Member;
import com.ict.gun.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Slf4j

@Service
@Transactional
public class MemPhysicalService {
    private final MemPhysicalRepository memPhysicalRepository;
    private final MemberRepository memberRepository;
    private final String phyPhotoDir;

    //생성자 주입을 이용, 프로퍼티 값 주입
    public MemPhysicalService(MemPhysicalRepository memPhysicalRepository, MemberRepository memberRepository, @Value("${upload-path-physical}") String phyPhotoDir) {
        this.memPhysicalRepository = memPhysicalRepository;
        this.memberRepository = memberRepository;
        this.phyPhotoDir = phyPhotoDir;
    }

    //측정 정보 저장
    public void memPhysicalInsert(MultipartFile file, MemPhysical memPhysical) throws Exception {
        try {
            String phyPhotoDir = "E:/gun_workspace/gun/src/main/resources/physical";
            // 파일명 변경 로직 추가
            String newFileName = generateNewFileName(memPhysical.getSeq());

            // 사진 저장 부분
            FileHandler handler = new FileHandler();

            if (handler.handleFileUpload(file, phyPhotoDir)) {
                // 파일 저장 성공 시 keypoints.exe 실행
                String[] command = {
                        "E:/3rdproject/keypoints/keypoints.exe",
                        "e:/gun_workspace/gun/src/main/resources/physical/" + newFileName,
                        "E:/gun_workspace/gun/src/main/resources/physical/jsonfile"
                };
                ProcessBuilder processBuilder = new ProcessBuilder(command);

                // 프로세스의 에러 스트림과 표준 출력을 병합
                processBuilder.redirectErrorStream(true);

                // 프로세스 빌더 실행
                Process process = processBuilder.start();

                // 프로세스 출력(에러 스트림) 읽어오기
                readProcessOutput(process);

                // 프로세스의 실행이 완료될 때까지 대기
                int exitCode = process.waitFor();

                // 저장 성공 시 로그
                if (exitCode == 0) {
                    MemPhysical savedMemPhysical = memPhysicalRepository.save(memPhysical);
                    log.info("측정정보 저장 성공 : {}", savedMemPhysical.getMemPhysical());
                } else {
                    log.error("프로세스 실행 중 오류 발생. 종료 코드: {}", exitCode);
                }
            } else {
                // 파일 저장 실패 시 로그
                log.error("사진 저장 실패");
            }
        } catch (Exception e) {
            // 예외 발생 시 로그
            log.error("측정사진 저장 또는 처리 중 오류 발생: {}", e.getMessage());
            throw e; // 예외를 상위 메서드로 전파
        }
    }


            private static void readProcessOutput (Process process) throws IOException {
                //프로세스 출력 스트림 얻기
                InputStream inputStream = process.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                //출력 읽어오기
                String line;
                System.out.println("프로세스 출력 : ");
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
                //닫기
                reader.close();
            }


            //1개 조회
            public MemPhysical findByMemEmail (String email){
                Member member = memberRepository.findByMemEmail(email);
                return memPhysicalRepository.findByMemEmail(member);
            }
            //측정 정보 삭제
            public void memPhysicalDelete (String email){
                Member member = memberRepository.findByMemEmail(email);
                memPhysicalRepository.deleteByMemEmail(member);
            }

    //public MemPhysical create(MemPhysicalRepository memPhysicalRepository) {
    //    MemPhysical entity = MemPhysical.builder()
    //            .memPhysical(memPhysicalRepository.getMemPhysical())
    //            .memLocation(memPhysicalRepository.getMemLocation())
    //            .memPoint(memPhysicalRepository.getMemPoint())
    //            .memCount(memPhysicalRepository.getMemCount())
    //            .memInputDate(memPhysicalRepository.getMemInputDate())
    //            .build();
    //    return memPhysicalRepository.save(entity);
    //}
    }

