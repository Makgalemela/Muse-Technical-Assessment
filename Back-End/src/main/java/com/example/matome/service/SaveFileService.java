package com.example.matome.service;


import com.example.matome.configs.EnvironmentConfigImpl;
import com.example.matome.domain.UploadedFiles;
import com.example.matome.repository.uploadFilesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class SaveFileService {


    @Autowired
    EnvironmentConfigImpl environmentConfig;

    @Autowired
    uploadFilesRepository  repository;

    public ResponseEntity<Object> saveFile(MultipartFile file, String fileName) throws IOException {
        byte[] bytes = file.getBytes();
        Path path = Paths.get(environmentConfig.getFileDirectory());
        Files.write(path, bytes);

        UploadedFiles  fileMETA = new UploadedFiles();
        fileMETA.setProcessStatus("pending");
        fileMETA.setFileName(fileName);
        return null;
    }

}
