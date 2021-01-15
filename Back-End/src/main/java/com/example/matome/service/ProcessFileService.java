package com.example.matome.service;


import com.example.matome.configs.EnvironmentConfigImpl;
import com.example.matome.domain.Path;
import com.example.matome.domain.SourceIndex;
import com.example.matome.domain.UploadedFiles;
import com.example.matome.repository.SourceIndexRepository;
import com.example.matome.repository.pathRepository;
import com.example.matome.repository.uploadFilesRepository;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ProcessFileService {


    Logger logger = LoggerFactory.getLogger(ProcessFileService.class);

    @Autowired
    EnvironmentConfigImpl environmentConfig;

    @Autowired
    uploadFilesRepository uploadFilesRepository;

    @Autowired
    pathRepository pathRepository;

    @Autowired
    SourceIndexRepository sourceIndexRepository;


    public File getFileByName(UploadedFiles uploadedFiles) throws FileNotFoundException {
        File file = new File(environmentConfig.getFileDirectory().concat("/ma2me/"+uploadedFiles.getFileName()));
        return  file;
    }

//    @Scheduled(fixedDelay = 5L)
//    public void processFile(){
//        uploadFilesRepository.findByProcessStatus("pending").forEach(pendingFile->{
//            try {
//                File pending = getFileByName(pendingFile);
//                BufferedReader reader = new BufferedReader(new FileReader(pending));
//                String line = null;
//                int count = 0;
//                List<Path> paths = new ArrayList<>();
//                while ((line = reader.readLine()) != null) {
//                    if (count != 0) {
//                        String[] data = line.split(";");
//                        if(checkIfPathExist(data)){
//                            Path path = new  Path();
//
//                            SourceIndex sourceIndex = sourceIndexRepository.findBySource(data[1]);
//                            SourceIndex index = new SourceIndex();
//
//                            if(Objects.isNull(sourceIndex)){
//                                index.setSource(data[1]);
//                                sourceIndex = sourceIndexRepository.save(index);
//                            }
//                            path.setOrigin(sourceIndex.getIndex());
//                            sourceIndex = sourceIndexRepository.findBySource(data[2]);
//                            index = new SourceIndex();
//                            if(Objects.isNull(sourceIndex)){
//                                index.setSource(data[2]);
//                                sourceIndex = sourceIndexRepository.save(index);
//                            }
//                            path.setDestination(sourceIndex.getIndex());
//                            String trafficDelay = data[3].replace(",", ".");
//                            path.setTrafficDelay(Double.valueOf(trafficDelay ));
//                            paths.add(path);
//                        }
//                    }
//                    count++;
//                }
//                pendingFile.setProcessStatus("processed");
//                uploadFilesRepository.save(pendingFile);
//                logger.info("Data Successfully saved");
//
//
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        });
//    }

//    List<SourceIndex> tempStudentList = new ArrayList<>();
//    XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
//    XSSFSheet worksheet = workbook.getSheetAt(0);

    public void processFile(){
        uploadFilesRepository.findByProcessStatus("pending").forEach(pendingFile->{
            try {
                File file = getFileByName(pendingFile);
                InputStream input = new FileInputStream(file);
                XSSFWorkbook workbook = new XSSFWorkbook(input);
                processPlanetNames(workbook.getSheetAt(0));
                ProcessRoutes(workbook.getSheetAt(1),workbook.getSheetAt(2) );
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            pendingFile.setProcessStatus("processed");
            uploadFilesRepository.save(pendingFile);
            logger.info("Data Successfully saved");
        });
    }
//    private  boolean checkIfPathExist(String[] data){
//        SourceIndex sourceIndex1 = sourceIndexRepository.findBySource(data[1]);
//        SourceIndex sourceIndex2 = sourceIndexRepository.findBySource(data[2]);
//        if(Objects.isNull(sourceIndex1) || Objects.isNull(sourceIndex2)){
//            return  true;
//        }
//        Path path = pathRepository.findByOriginAndDestination(sourceIndex1.getIndex(),sourceIndex2.getIndex());
//         if(Objects.nonNull(path)){
//            return  false;
//        }
//       return  true;
//    }

    private  boolean checkIfPathIsSaveExcel(XSSFRow row){
        SourceIndex sourceIndex1 = sourceIndexRepository.findBySource(row.getCell(1).getStringCellValue());
        SourceIndex sourceIndex2 = sourceIndexRepository.findBySource(row.getCell(2).getStringCellValue());
        if(Objects.isNull(sourceIndex1) || Objects.isNull(sourceIndex2)){
            return  true;
        }
        Path path = pathRepository.findByOriginAndDestination(sourceIndex1.getIndex(),sourceIndex2.getIndex());
        if(Objects.nonNull(path)){
            return  false;
        }
        return  true;

    }



    private void processPlanetNames(XSSFSheet worksheet){
        List<SourceIndex> sourceIndices = new ArrayList<>();
        for(int itr=1;itr<worksheet.getPhysicalNumberOfRows() ;itr++) {
            SourceIndex sourceIndex = new SourceIndex();
            XSSFRow row = worksheet.getRow(itr);

            sourceIndex.setSource(row.getCell(0).getStringCellValue());
            sourceIndex.setCountryName(row.getCell(1).getStringCellValue());
            sourceIndices.add(sourceIndex);
        }
        sourceIndexRepository.saveAll(sourceIndices);
    }

    private void ProcessRoutes(XSSFSheet worksheet,XSSFSheet worksheet2 ){
        List<Path> paths = new ArrayList<>();
        for(int itr=1;itr<worksheet.getPhysicalNumberOfRows() ;itr++) {
            Path path = new Path();
            XSSFRow row = worksheet.getRow(itr);
            XSSFRow row2 = worksheet.getRow(itr);

            if(checkIfPathIsSaveExcel(row)) {
                Integer src = sourceIndexRepository.findBySource(row.getCell(1).getStringCellValue()).getIndex();
                Integer des = sourceIndexRepository.findBySource(row.getCell(2).getStringCellValue()).getIndex();
                String distance = (row.getCell(3).getErrorCellString()).replace(",", ".");
                String traffic = (row2.getCell(3).getErrorCellString()).replace(",", ".");

                path.setOrigin(src);
                path.setDestination(des);
                path.setDistance(Double.valueOf(distance));
                path.setTrafficDelay(Double.valueOf(traffic));
            }
            paths.add(path);
        }
        pathRepository.saveAll(paths);
    }
}
