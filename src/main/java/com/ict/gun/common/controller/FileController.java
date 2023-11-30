package com.ict.gun.common.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@Slf4j
public class FileController {

    @Value("${upload-path-member}")
    private String uploadPath;

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestPart("file") MultipartFile file
    ,@RequestPart("email") String email) {
        try {
            log.info(String.valueOf(Paths.get("uploadPath", file.getOriginalFilename())));
            log.info("email : " + email);


            String[] parts  = email.split("@");
            String userFolder = parts[0];


            uploadPath = uploadPath + "/" + userFolder;
            log.info("uploadPath : " + uploadPath);
            Path filePath = Paths.get(uploadPath, file.getOriginalFilename());

            Path directoryPath = filePath.getParent();
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            log.info("filePath : " + filePath.toFile());
            log.info("filesize : " + file.getSize());
            file.transferTo(filePath.toFile());
            log.info("save......");
            return ResponseEntity.ok("File uploaded successfully");
        } catch (FileNotFoundException e) {
            log.error("File not found", e);
            return ResponseEntity.status(500).body("File not found");
        } catch (IOException e) {
            log.error("IOException during file transfer", e);
            return ResponseEntity.status(500).body("Failed to upload the file due to an IO exception");
        } catch (Exception e) {
            log.error("Unexpected error during file transfer", e);
            return ResponseEntity.status(500).body("Failed to upload the file due to an unexpected error");
        }
    }
}
