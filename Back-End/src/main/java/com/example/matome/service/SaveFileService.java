package com.example.matome.service;


import com.example.matome.configs.EnvironmentConfigImpl;
import com.example.matome.domain.UploadedFiles;
import com.example.matome.repository.uploadFilesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class SaveFileService {
    Logger logger = LoggerFactory.getLogger(SaveFileService.class);



    @Autowired
    EnvironmentConfigImpl environmentConfig;

    @Autowired
    uploadFilesRepository  repository;

    public ResponseEntity<Object> saveFile(MultipartFile file, String fileName) throws IOException {
        byte[] bytes = file.getBytes();
         Path path = Paths.get(environmentConfig.getFileDirectory().concat("/ma2me"), file.getOriginalFilename());
         Files.write(path, bytes);
        UploadedFiles  fileMETA = new UploadedFiles();
        fileMETA.setProcessStatus("pending");
        fileMETA.setFileName(fileName);
        fileMETA = repository.save(fileMETA);
        logger.info("file uploaded :: "+ fileMETA.getFileName() + " " +fileMETA.getId());

         return null;
    }

}
