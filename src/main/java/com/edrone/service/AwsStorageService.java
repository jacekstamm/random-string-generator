package com.edrone.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.edrone.dto.JobResultDto;
import com.edrone.mapper.JobResultMapper;
import com.edrone.repository.JobResultRepository;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AwsStorageService {

    private final JobResultRepository repository;
    private final JobResultMapper mapper;

    @Value("${application.bucket.name}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3Client;

    public JobResultDto uploadFile(File resultFile) {
        String fileName = resultFile.getName();
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, resultFile));
        deleteFIle(resultFile, fileName);
        return new JobResultDto(fileName);
    }

    private void deleteFIle(File resultFile, String fileName) {
        if (resultFile.delete()) {
            log.debug("FIle with name:" + fileName + " is deleted.");
        }
    }


    @Transactional
    public byte[] downloadFile(UUID id) {
        JobResultDto result = mapper.mapEntityToDto(repository.getReferenceById(id));
        S3Object s3Object = s3Client.getObject(bucketName, String.valueOf(result.getFileNameOnAws()));
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            return IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
