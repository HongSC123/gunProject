package com.ict.gun.memPhysical.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ict.gun.common.FileHandler;
import com.ict.gun.memPhysical.data.entity.MemPhysical;
import com.ict.gun.memPhysical.service.MemPhysicalService;
import com.ict.gun.member.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;

@Slf4j
@CrossOrigin("http://localhost:8888")
@RestController
public class MemPhysicalController {
    private final MemberRepository memberRepository;
    private final MemPhysicalService memPhysicalService;
    private MultipartFile memPhysical;


    public MemPhysicalController(MemPhysicalService memPhysicalService,
                                 MemberRepository memberRepository) {
        this.memPhysicalService = memPhysicalService;
        this.memberRepository = memberRepository;
    }

    @PostMapping("/memPhysical/insert") //측정 정보 저장
    public ResponseEntity<String> memPhysicalInsert (@RequestParam("memPhysical") MultipartFile memPhysical
    , HttpServletRequest request) {
        this.memPhysical = memPhysical;
        String memEmail = request.getHeader("memEmail");
        MemPhysical mem_physical = new MemPhysical();
        mem_physical.setMemLocation("정면");
        mem_physical.setMemInputDate(new Date(System.currentTimeMillis()));
        mem_physical.setMemEmail(memEmail);

        FileHandler handler = new FileHandler();
        String phyPhotoDir = "/physical";
        if(handler.handleFileUpload(memPhysical,phyPhotoDir)){
            try {
                String[] command = {
                        "E:/gun_workspace/gun/src/main/resources/keypoints/keypoints.exe",
                        "e:/gun_workspace/gun/src/main/resources/physical/" +  memPhysical.getOriginalFilename(),
                        "E:/gun_workspace/gun/src/main/resources/physical/jsonfile"
                };
                //ProcessBuilder를 사용하여 외부 프로세스를 실행을 위해 명령어 및 파라미터들을 리스트 형태로 설정
                List<String> commandList = Arrays.asList(command);
                //외부 exe 파일 사용을 위해 ProcessBuilder 클래스를 사용
                ProcessBuilder processBuilder = new ProcessBuilder(commandList);
                processBuilder.redirectErrorStream(true); // 에러 스트림을 표준 출력 스트림으로 병합

                Process process = processBuilder.start();

                // 외부 프로세스의 종료를 기다림
                int exitCode = process.waitFor();
                log.info("External process exited with code: " + exitCode);

                //json file 읽기
                //경로
                String jsonFilePath = "E:/gun_workspace/gun/src/main/resources/physical/jsonfile/keypoints.json";

                //Jackson Object Mapper 인스턴스 생성
                ObjectMapper objectMapper = new ObjectMapper();

                //JSON 파일 읽어오기
                JsonNode jsonNode = objectMapper.readTree(new File(jsonFilePath));
                mem_physical.setMemPoint(jsonNode.toString());

                System.out.println("JSON 데이터:\n" + jsonNode.toString());

                // 프로세스의 출력을 읽어 로깅하거나 다른 작업을 수행할 수 있음
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        log.info(line);
                    }
                }

                } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        try {
            memPhysicalService.save(mem_physical);
            log.info("memPhysical db 저장");
            String result = "저장 성공";
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            String result = "저장 실패";
            return ResponseEntity.status(500).body(result);
        }
    }

    @GetMapping("/detail/physical") //상세보기
    public ResponseEntity<MemPhysical> memPhysicalDetail(@RequestParam("mem_email") String memEmail) {
        log.info("memPhysical detail");
        MemPhysical memPhysical = memPhysicalService.findByMemEmail(memEmail);
        // 만약 해당 이메일로 찾은 정보가 없을 경우 NOT_FOUND 상태 코드를 반환할 수 있습니다.
        if (memPhysical == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(memPhysical, HttpStatus.OK);
    }

//    @DeleteMapping("/delete/{MEM_EMAIL}") //측정 정보 삭제하기
//    public ResponseEntity<String> memPhysicalDelete(@PathVariable("MEM_EMAIL") String email) {
//        try {
//            memPhysicalService.memPhysicalDelete(email);
//            String result = "측정 정보 삭제";
//            return ResponseEntity.ok(result);
//        } catch (Exception e) {
//            String result = "측정 정보 삭제 실패";
//            return ResponseEntity.status(500).body(result);
//        }
//
//    }

}
