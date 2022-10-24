package com.edrone.mapper;

import com.edrone.dto.JobResultDto;
import com.edrone.entity.JobResults;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class JobResultMapper {

    public JobResults mapDtoToEntity(UUID id, JobResultDto jobResultDto) {
        return new JobResults(id, jobResultDto.getFileNameOnAws());
    }

    public JobResultDto mapEntityToDto(JobResults jobResults) {
        return new JobResultDto(jobResults.getFileNameOnAws());
    }
}
