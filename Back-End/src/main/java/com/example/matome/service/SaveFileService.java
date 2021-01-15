package com.example.matome.service;


import com.example.matome.configs.EnvironmentConfigImpl;
import com.example.matome.domain.SourceIndex;
import com.example.matome.domain.UploadedFiles;
import com.example.matome.repository.SourceIndexRepository;
import com.example.matome.repository.uploadFilesRepository;
import com.example.matome.utils.ResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class SaveFileService {
    Logger logger = LoggerFactory.getLogger(SaveFileService.class);



    @Autowired
    EnvironmentConfigImpl environmentConfig;
    @Autowired
    uploadFilesRepository  repository;
    @Autowired
    SourceIndexRepository sourceIndexRepository;

    public ResponseEntity<Object> saveFile(MultipartFile file, String fileName) throws IOException {
        byte[] bytes = file.getBytes();
         Path path = Paths.get(environmentConfig.getFileDirectory().concat("/ma2me"), file.getOriginalFilename());
         Files.write(path, bytes);
        UploadedFiles  fileMETA = new UploadedFiles();
        fileMETA.setProcessStatus("pending");
        fileMETA.setFileName(fileName);
        fileMETA = repository.save(fileMETA);
        logger.info("file uploaded :: "+ fileMETA.getFileName() + " " +fileMETA.getId());
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "File Successfully uploaded pending processing", fileName);
    }


    public ResponseEntity<Object> getSavedPaths() {
        List<SourceIndex> paths = sourceIndexRepository.findAll();
        logger.info(""+paths);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Available Paths", paths);
    }

}
