package com.edrone.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Set;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class FileCreatorService {

    public File createNewFile(UUID id, Set<String> jobResults) {
        try {
            File file = new File(id.toString() + ".txt");
            fillFileWithResults(jobResults, file);
            return file;
        } catch (IOException exception) {
            throw new RuntimeException("There was a problem to create new File with job id:" + id);
        }
    }

    private void fillFileWithResults(Set<String> jobResults, File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

        for (String result: jobResults) {
            bw.write(result);
            bw.newLine();
        }
        bw.close();
    }
}
