package com.example.matome.configs;


import io.lettuce.core.dynamic.annotation.Value;
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
