package com.ict.gun.memPhysical.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ict.gun.common.FileHandler;
import com.ict.gun.memPhysical.data.entity.MemPhysical;
import com.ict.gun.memPhysical.repository.MemPhysicalRepository;
import com.ict.gun.memPhysical.service.MemPhysicalService;
import com.ict.gun.member.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.InputStreamReader;
import java.sql.Date;
import java.util.*;

@Slf4j

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
                                                     , @RequestParam("memEmail") String memEmail
            , HttpServletRequest request) {
        this.memPhysical = memPhysical;
        MemPhysical mem_physical = new MemPhysical();
        mem_physical.setMemLocation("정면");
        mem_physical.setMemInputDate(new Date(System.currentTimeMillis()));
        mem_physical.setMemEmail(memEmail);

        memPhysicalService.save(mem_physical);
        FileHandler handler = new FileHandler();
        String phyPhotoDir = "/physical";
        if(handler.handleFileUpload(memPhysical,phyPhotoDir,memEmail)){
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

    @PostMapping("/detail/physical") //상세보기
    public ResponseEntity<Map<String, Object>> memPhysicalDetail(HttpServletRequest request) {
        String memEmail = request.getHeader("memEmail");
        Map<String, Object> result = new HashMap<>();
        // log.info("memPhysical detail");
        log.info("memEmail : " + memEmail);
        MemPhysical memPhysical = memPhysicalService.findTopByMemEmailOrderByMemPhysicalDesc(memEmail);
        log.info("memPhysical : " + memPhysical);
        result.put("physicalNum",memPhysical.getMemPhysical());
        result.put("Location",memPhysical.getMemLocation());
        result.put("InputDate",memPhysical.getMemInputDate().toString());

        // JSON 문자열
        String jsonString = memPhysical.getMemPoint();
        // Jackson ObjectMapper 생성
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> pointMap = new HashMap<>();

        try {
            // JSON 문자열을 MyDataObject 클래스의 내부 클래스로 변환
            OuterClass.PointData myDataObject = objectMapper.readValue(jsonString, OuterClass.PointData.class);

            // 변환된 객체 출력
            log.info("ear: " + myDataObject.getEar());
            pointMap.put("ear",myDataObject.getEar());
            log.info("shoulder: " + myDataObject.getShoulder());
            pointMap.put("shoulder",myDataObject.getShoulder());
            log.info("hip: " + myDataObject.getHip());
            pointMap.put("hip",myDataObject.getHip());
            log.info("knee: " + myDataObject.getKnee());
            pointMap.put("knee",myDataObject.getKnee());
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.put("Point",pointMap);
        // 만약 해당 이메일로 찾은 정보가 없을 경우 NOT_FOUND 상태 코드를 반환할 수 있습니다.
        if (memPhysical == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.info("*************result : " + result);
        return ResponseEntity.ok(result);
    }

    class OuterClass {

        @Getter
        @Setter
        public static class PointData {

            private double ear;
            private double shoulder;
            private double hip;
            private double knee;

        }
    }

@DeleteMapping("/delete/physical/{MEM_PHYSICAL}") //측정 정보 삭제하기
    public void memPhysicalDelete(@PathVariable("MEM_PHYSICAL") Long memPhysical) {

        log.info("memPhysical : 삭제" + memPhysical);
        memPhysicalService.memPhysicalDelete(memPhysical);
    }

    @PostMapping("/list/physical")
    public ResponseEntity<List<Map<String, Object>>> memPhysicalList(HttpServletRequest request) {
        String memEmail = request.getHeader("memEmail");

        log.info("**측정 목록 불러오기 memEmail : " + memEmail);

        // 이메일로 데이터베이스를 조회하여 측정 정보를 가져옵니다.
        List<MemPhysical> memPhysicalList = memPhysicalService.findAllByMemEmail(memEmail);
        log.info("**측정 목록 불러오기 memPhysicalList : " + memPhysicalList);
        // 만약 해당 이메일로 찾은 정보가 없을 경우 NOT_FOUND 상태 코드를 반환합니다.
        if (memPhysicalList == null || memPhysicalList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Map<String, Object>> resultList = new ArrayList<>();

        for (MemPhysical memPhysical : memPhysicalList) {
            Map<String, Object> result = new HashMap<>();

            result.put("physicalNum", memPhysical.getMemPhysical());
            result.put("Location", memPhysical.getMemLocation());
            result.put("InputDate", memPhysical.getMemInputDate().toString());

            // JSON 문자열
            String jsonString = memPhysical.getMemPoint();

            // Jackson ObjectMapper 생성
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> pointMap = new HashMap<>();

            try {
                // JSON 문자열을 MyDataObject 클래스의 내부 클래스로 변환
                OuterClass.PointData myDataObject = objectMapper.readValue(jsonString, OuterClass.PointData.class);

                // 변환된 객체 출력
                pointMap.put("ear", myDataObject.getEar());
                pointMap.put("shoulder", myDataObject.getShoulder());
                pointMap.put("hip", myDataObject.getHip());
                pointMap.put("knee", myDataObject.getKnee());
            } catch (Exception e) {
                e.printStackTrace();
            }

            result.put("Point", pointMap);

            resultList.add(result);
        }

        log.info("************* List result : " + resultList);

        return ResponseEntity.ok(resultList);
    }




}
