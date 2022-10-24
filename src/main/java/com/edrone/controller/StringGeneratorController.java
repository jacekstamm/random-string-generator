package com.edrone.controller;

import com.edrone.dto.JobRequestDto;
import com.edrone.service.AwsStorageService;
import com.edrone.service.StringGeneratorService;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/generator")
@RequiredArgsConstructor
public class StringGeneratorController {

    private final StringGeneratorService service;
    private final AwsStorageService awsStorageService;

    @PostMapping("/runJob")
    public ResponseEntity<String> runJob(@RequestBody @NonNull JobRequestDto jobRequest) {
        UUID jobId = generateId();
        CompletableFuture.runAsync(() -> service.generateRandomStrings(jobId, jobRequest.getResults(), jobRequest.getMinLength(),
          jobRequest.getMaxLength(), jobRequest.getChars()));

        return ResponseEntity.status(HttpStatus.CREATED).body("Your Job ID: " + jobId);
    }

    @GetMapping("/jobThreadsRunning")
    public String checkJobRunning() {
        return service.checkJobRunning();
    }

    @GetMapping("/getJobResult/{id}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable UUID id) {
        byte[] data = awsStorageService.downloadFile(id);

        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity
          .ok()
          .contentLength(data.length)
          .header("Content-type", "application/octet-stream")
          .header("Content-disposition", "attachment; jobId=\"" + id + "\"")
          .body(resource);
    }

    private UUID generateId() {
        return UUID.randomUUID();
    }
}
