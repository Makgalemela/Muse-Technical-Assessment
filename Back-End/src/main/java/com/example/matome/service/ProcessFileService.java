package com.example.matome.service;


import com.example.matome.configs.EnvironmentConfigImpl;
import com.example.matome.domain.Path;
import com.example.matome.domain.PlanetName;
import com.example.matome.domain.UploadedFiles;
import com.example.matome.repository.PlanetNameRepository;
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

/**
 *
 *
 * The class is used to process files once they have uploaded.
 * The files are not moved to a new directory once processed and
 *
 */

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
    PlanetNameRepository planetNameRepository;


    @Autowired
    SaveFileService saveFileService;


    public File getFileByName(UploadedFiles uploadedFiles) throws FileNotFoundException {
        File file = new File(environmentConfig.getFileDirectory().concat("/ma2me/"+uploadedFiles.getFileName()));
        return  file;
    }


    /**
     * The scheduled task is responsible for getting files from the file directory and processing them;
     * processing means persisting the file data to the database
     */
    @Scheduled(fixedDelay = 5L)
    public void processFile(){
        uploadFilesRepository.findByProcessStatus("pending").forEach(pendingFile->{
            try {
                File file = getFileByName(pendingFile);

                InputStream input = new FileInputStream(file);
                XSSFWorkbook workbook = new XSSFWorkbook(input);
                processPlanetNames(workbook.getSheetAt(0));
                ProcessRoutes(workbook.getSheetAt(1),workbook.getSheetAt(2));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            pendingFile.setProcessStatus("processed");
            uploadFilesRepository.save(pendingFile);
            logger.info("Data Processed saved");
            try {
                saveFileService.moveFile(getFileByName(pendingFile));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }


    /**
     *  While designing this class the assumption is that the files are formatted in the correct
     *  way, the function below is responsible for ensuring that while we process the files
     *  we do no not duplicate persisted data.
     * @param row
     * @return
     */
    private  boolean checkIfPathIsSaveExcel(XSSFRow row){
        PlanetName planetName1 = planetNameRepository.findByNodeName(row.getCell(1).getStringCellValue());
        PlanetName planetName2 = planetNameRepository.findByNodeName(row.getCell(2).getStringCellValue());
        if(Objects.isNull(planetName1) || Objects.isNull(planetName2)){
            return  false;
        }
        Path path = pathRepository.findByOriginAndDestination(planetName1.getIndex(), planetName2.getIndex());
        if(Objects.nonNull(path)){
            return  false;
        }
        return  true;

    }


    /**
     *
     * The function process the initial table of the excel files and this is kept as a reference.
     * @param worksheet
     */

    private void processPlanetNames(XSSFSheet worksheet){
        List<PlanetName> NodeNameIndices = new ArrayList<>();
        for(int itr=1;itr<worksheet.getPhysicalNumberOfRows() ;itr++) {
            PlanetName planetName = new PlanetName();
            XSSFRow row = worksheet.getRow(itr);
            PlanetName src = planetNameRepository.findByNodeName(row.getCell(0).getStringCellValue());
            if(Objects.isNull(src)) {
                planetName.setNodeName(row.getCell(0).getStringCellValue());
                planetName.setPlanetName(row.getCell(1).getStringCellValue());
                NodeNameIndices.add(planetName);
            }
        }
        if(NodeNameIndices.size() >0){
            planetNameRepository.saveAll(NodeNameIndices);
        }
    }

    /**
     *
     * The function is responsible for processing 2nd and 3rd tables.
     * The path are stored as integer e.g path from A to B is stored as 1 to 2
     * 1 is reference to A in the plane name table and 2 is reference to b, and
     * so on. The reason for this is because it is easy to actually traverse numbers
     * when dealing with graphs.
     * @param worksheet
     * @param worksheet2
     */

    private void ProcessRoutes(XSSFSheet worksheet,XSSFSheet worksheet2 ){
        List<Path> paths = new ArrayList<>();
        for(int itr=1;itr<worksheet.getPhysicalNumberOfRows() ;itr++) {
            Path path = new Path();
            XSSFRow row = worksheet.getRow(itr);
            XSSFRow row2 = worksheet2.getRow(itr);

            if(checkIfPathIsSaveExcel(row)){

                String origin = row.getCell(1).getStringCellValue();
                String destination = row.getCell(2).getStringCellValue();
                    PlanetName src = planetNameRepository.findByNodeName(origin);
                    PlanetName des = planetNameRepository.findByNodeName(destination);
                     path.setOrigin(src.getIndex());
                     path.setDestination(des.getIndex());
                    path.setDistance(row.getCell(3).getNumericCellValue());
                    path.setTrafficDelay(row2.getCell(3).getNumericCellValue());
                    paths.add(path);
            }

        }
        pathRepository.saveAll(paths);
    }
}
