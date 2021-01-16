package com.example.matome.configs;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentConfigImpl implements  EnvironmentConfig {

    @Value("${dataimport.fileupload.dir}")
    private String FileDirectory;

    @Override
    public String getFileDirectory() {
        return FileDirectory;
    }

}
