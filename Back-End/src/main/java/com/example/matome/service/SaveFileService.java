package com.example.matome.service;


import com.example.matome.configs.EnvironmentConfigImpl;
import com.example.matome.domain.PlanetName;
import com.example.matome.domain.UploadedFiles;
import com.example.matome.repository.PlanetNameRepository;
import com.example.matome.repository.uploadFilesRepository;
import com.example.matome.utils.ResponseHandler;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class SaveFileService {
    Logger logger = LoggerFactory.getLogger(SaveFileService.class);



    @Autowired
    EnvironmentConfigImpl environmentConfig;
    @Autowired
    uploadFilesRepository  repository;
    @Autowired
    PlanetNameRepository planetNameRepository;




    public void createFileDirectory(String path){
        String Path = environmentConfig.getFileDirectory().concat(path);
        File directory = new File(Path);
        if(!directory.exists()){
            directory.mkdirs();
        }
    }

    /**
     *
     * First we create directory called ma2me == Matome
     * The the file is saved in the created and directory and update the database
     * and put processed status as pending
     * @param file
     * @param fileName
     * @return
     * @throws IOException
     */
    public ResponseEntity<Object> saveFile(MultipartFile file, String fileName) throws IOException {
        byte[] bytes = file.getBytes();
        /**
         * Create directory if it does not exist
         */
        createFileDirectory("/ma2me/");
        String fileExt = FilenameUtils.getExtension(file.getOriginalFilename());
        if(fileExt.equals("xlsx")){
            Path path = Paths.get(environmentConfig.getFileDirectory().concat("/ma2me"), file.getOriginalFilename());
            Files.write(path, bytes);
            UploadedFiles  _file = new UploadedFiles();
            _file.setProcessStatus("pending");
            _file.setFileName(fileName);
            _file = repository.save(_file);
            logger.info("file uploaded :: "+ _file.getFileName() + " " +_file.getId());
            return ResponseHandler.generateResponse(HttpStatus.OK, true, "File Successfully uploaded pending processing."+ '\n'+"This will take a second.", fileName);
        }else{
            logger.info("File extension is wrong");
            return ResponseHandler.generateResponse(HttpStatus.OK, true, "File could not be processed, wrong file extension, file extenson must be .xlsx",fileName);
        }
    }

    /**
     *
     * Move processed files
     * @param file
     *
     */
    public void moveFile(File file) {
        /**
         * Create directory if it does not exist
         */
        createFileDirectory("/ma2me/processed/");

        Path sourcepath = Paths.get(file.getPath());
        Path destinationepath = Paths.get(environmentConfig.getFileDirectory().concat("/ma2me/processed"), file.getName());
        try {
            Files.move(sourcepath, destinationepath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ResponseEntity<Object> getSavedPaths() {
        List<PlanetName> paths = planetNameRepository.findAll();
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Available Paths", paths);
    }

}
