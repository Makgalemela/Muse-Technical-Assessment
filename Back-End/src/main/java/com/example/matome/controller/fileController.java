package com.example.matome.controller;


import com.example.matome.domain.SourceIndex;
import com.example.matome.service.ProcessFileService;
import com.example.matome.service.SaveFileService;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static com.example.matome.configs.UrlConstant.*;


@Controller
@CrossOrigin("*")
public class fileController {

    Logger logger = LoggerFactory.getLogger(fileController.class);


    @Autowired
    SaveFileService saveFileService;


    @PostMapping(value= UPLOAD_FILE ,consumes = MediaType.MULTIPART_FORM_DATA_VALUE ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file ,  @RequestParam("fileName") String fileName) throws Exception {
        return saveFileService.saveFile(file,fileName);
    }

    @GetMapping(value= GET_ALL_PATHS)
    public ResponseEntity<Object> shortestPath() {
        return saveFileService.getSavedPaths();
    }
}
