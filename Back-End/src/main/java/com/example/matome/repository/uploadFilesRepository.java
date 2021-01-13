package com.example.matome.repository;
import com.example.matome.domain.UploadedFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface uploadFilesRepository  extends JpaRepository<UploadedFiles, Long> {

    List<UploadedFiles> findByProcessStatus(String status);
}
