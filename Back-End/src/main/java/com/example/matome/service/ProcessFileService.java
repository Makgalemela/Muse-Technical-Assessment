package com.example.matome.service;


import com.example.matome.configs.EnvironmentConfigImpl;
import com.example.matome.domain.Path;
import com.example.matome.domain.SourceIndex;
import com.example.matome.domain.UploadedFiles;
import com.example.matome.repository.SourceIndexRepository;
import com.example.matome.repository.pathRepository;
import com.example.matome.repository.uploadFilesRepository;
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

    @Scheduled(fixedDelay = 5L)
    public void processFile(){
        uploadFilesRepository.findByProcessStatus("pending").forEach(pendingFile->{
            try {
                File pending = getFileByName(pendingFile);
                BufferedReader reader = new BufferedReader(new FileReader(pending));
                String line = null;
                int count = 0;
                List<Path> paths = new ArrayList<>();
                while ((line = reader.readLine()) != null) {
                    if (count != 0) {
                        String[] data = line.split(";");
                        if(checkIfPathExist(data)){
                            Path path = new  Path();

                            SourceIndex sourceIndex = sourceIndexRepository.findBySource(data[1]);
                            SourceIndex index = new SourceIndex();

                            if(Objects.isNull(sourceIndex)){
                                index.setSource(data[1]);
                                sourceIndex = sourceIndexRepository.save(index);
                            }
                            path.setOrigin(sourceIndex.getIndex());
                            sourceIndex = sourceIndexRepository.findBySource(data[2]);
                            index = new SourceIndex();
                            if(Objects.isNull(sourceIndex)){
                                index.setSource(data[2]);
                                sourceIndex = sourceIndexRepository.save(index);
                            }
                            path.setDestination(sourceIndex.getIndex());
                            String trafficDelay = data[3].replace(",", ".");
                            path.setTrafficDelay(Double.valueOf(trafficDelay ));
                            paths.add(path);
                        }
                    }
                    count++;
                }
                pathRepository.saveAll(paths);
                pendingFile.setProcessStatus("processed");
                uploadFilesRepository.save(pendingFile);
                logger.info("Data Successfully saved");


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }

    private  boolean checkIfPathExist(String[] data){
        SourceIndex sourceIndex1 = sourceIndexRepository.findBySource(data[1]);
        SourceIndex sourceIndex2 = sourceIndexRepository.findBySource(data[2]);
        if(Objects.isNull(sourceIndex1) || Objects.isNull(sourceIndex2)){
            return  true;
        }
        Path path = pathRepository.findByOriginAndDestination(sourceIndex1.getIndex(),sourceIndex2.getIndex());
         if(Objects.nonNull(path)){
            return  false;
        }
       return  true;
    }
}
