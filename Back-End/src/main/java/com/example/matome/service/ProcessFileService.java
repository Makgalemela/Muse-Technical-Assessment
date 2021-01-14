package com.example.matome.service;


import com.example.matome.configs.EnvironmentConfigImpl;
import com.example.matome.domain.Path;
import com.example.matome.domain.UploadedFiles;
import com.example.matome.repository.pathRepository;
import com.example.matome.repository.uploadFilesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProcessFileService {
    @Autowired
    EnvironmentConfigImpl environmentConfig;

    @Autowired
    uploadFilesRepository uploadFilesRepository;

    @Autowired
    pathRepository pathRepository;

    public File getFileByName(UploadedFiles uploadedFiles) throws FileNotFoundException {
        File file = new File(environmentConfig.getFileDirectory().concat(uploadedFiles.getFileName()));
        return  file;
    }

//    @Scheduled(fixedDelay = 5L)
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

                        Path path = new  Path();
                        path.setDestination(data[2]);
                        path.setOrigin(data[1]);
                        path.setTrafficDelay(data[3]);
                        paths.add(path);
                    }
                }
                pathRepository.saveAll(paths);
                pendingFile.setProcessStatus("processed");
                uploadFilesRepository.save(pendingFile);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }
}
