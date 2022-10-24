package com.edrone.service;

import com.edrone.dto.JobResultDto;
import com.edrone.exception.BadJobRequestException;
import com.edrone.exception.TooMuchResultsWantedException;
import com.edrone.mapper.JobResultMapper;
import com.edrone.repository.JobResultRepository;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class StringGeneratorService {

    private final FileCreatorService fileCreatorService;
    private final JobResultRepository repository;
    private final AwsStorageService awsStorageService;
    private final JobResultMapper mapper;

    public void generateRandomStrings(UUID jobId, int wantedResultsNumber, int minLength, int maxLength, final String stringOfChars) {
        Thread newThread = new Thread(() -> {
            checkJobRequest(wantedResultsNumber, minLength, maxLength, stringOfChars);
            checkAllPossibleCombination(wantedResultsNumber, minLength, maxLength, stringOfChars);

            Set<String> uniqueResults = generateUniqueStrings(wantedResultsNumber, minLength, maxLength, stringOfChars);

            File file = fileCreatorService.createNewFile(jobId, uniqueResults);

            log.info("SAVE JOB RESULTS WITH ID " + jobId + " ON AWS");
            JobResultDto jobResultDto = awsStorageService.uploadFile(file);

            log.info("SAVE JOB RESULTS WITH ID " + jobId + " TO DB");
            repository.save(mapper.mapDtoToEntity(jobId, jobResultDto));
        });
        newThread.setName("JobThread:" + jobId);
        newThread.start();
    }

    public String checkJobRunning() {
        Set<Thread> threads = Thread.getAllStackTraces().keySet();
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-15s \t %-15s \n", "Name", "State"));
        for (Thread t : threads) {
            String s = String.format("%-15s \t %-15s \n", t.getName(), t.getState());
            if (s.contains("JobThread")) {
                sb.append(s);
            }
        }
        return sb.toString();
    }

    Set<String> generateUniqueStrings(int wantedResultsNumber, int minLength, int maxLength, String stringOfChars) {
        List<String> results = new ArrayList<>();
        for (int i = minLength; i <= maxLength; i++) {
            StringBuilder sb = new StringBuilder();
            convertStringToStringBuilder(stringOfChars, sb);
            generateAllPossibleStrings(results, "", sb.toString(), i);
        }
        return new HashSet<>(results.subList(0, wantedResultsNumber));
    }

    private void convertStringToStringBuilder(String stringOfChars, StringBuilder sb) {
        for (int i = 0; i < stringOfChars.length(); i++) {
            char c = stringOfChars.charAt(i);
            sb.append(c);
        }
    }

    void checkJobRequest(int resultsNumber, int minLength, int maxLength, String stringOfChars) {
        if (resultsNumber < 1 || minLength < 1 || maxLength < 1 || stringOfChars.length() < 1) {
            throw new BadJobRequestException(resultsNumber, minLength, maxLength, stringOfChars);
        }
    }

    void checkAllPossibleCombination(int resultsNumber, int minLength, int maxLength, String stringOfChars) {
        float sum = 0;
        for (int i = minLength; i <= maxLength; i++) {
            BigDecimal numberOfCases = BigDecimal.valueOf(stringOfChars.length()).pow(i);
            sum = sum + numberOfCases.floatValue();
        }
        if (sum - resultsNumber < 0) {
            throw new TooMuchResultsWantedException(stringOfChars, maxLength, resultsNumber);
        }
    }

    private void generateAllPossibleStrings(@NonNull List<String> results, @NonNull String prefix,
                                           @NonNull String charset, int length) {
        if (prefix.length() >= length) {
            results.add(prefix);
            return;
        }
        for (int i = 0; i < charset.length(); i++) {
            generateAllPossibleStrings(results, prefix + charset.charAt(i), charset, length);
        }
    }
}
