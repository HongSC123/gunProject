package com.ict.gun.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class FileHandler {
    /**
     *  작성자 : 김정선
     *  작성 내용 :  파일 주소 설정 방법 application.properties 에
     *  upload-path-member: E:/gun_workspace/gun/src/main/resources/
     *  라고 작성 다른 주소가 필요 하다면
     *  upload-path-####: 절대 경로
     *  로 작성 해서 쓰면됨
     *  다른 파일도 여기서 만들어 서 쓸것!
     *  최초 게시일 : 23.11.30
     * */

    private final String pathBase = "E:/gun_workspace/gun/src/main/resources";
    private final String faceLogin = "E:/gun_workspace/gun/src/main/resources";

    public boolean handleFileUpload(MultipartFile file, String folderName,String memEmail) {
        boolean result = false;
        if (file != null && !file.isEmpty()) {
            try {
                String uploadFolderPath = createUploadFolderPath(folderName);
                log.info("uploadFolderPath : "+uploadFolderPath);
                String filePath = uploadFolderPath + File.separator + file.getOriginalFilename();
                log.info("file path : " + filePath);
                file.transferTo(new File(filePath));

                String destinationPath = faceLogin;
                File destinationFolder = new File(faceLogin);
                if (!destinationFolder.exists()) {
                    destinationFolder.mkdirs();
                }
                String destinationFilePath = destinationPath + File.separator + file.getOriginalFilename();
                FileUtils.copyFile(new File(filePath), new File(destinationFilePath));

                result = true;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return result;
    }

    private String createUploadFolderPath(String folderName) {
        String uploadFolderPath = pathBase + File.separator + folderName;
        log.info("pathBase : "+pathBase);
        log.info("folderName : "+folderName);
        log.info("uploadFolderPath : "+uploadFolderPath);
        File folder = new File(uploadFolderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return uploadFolderPath;
    }
}
