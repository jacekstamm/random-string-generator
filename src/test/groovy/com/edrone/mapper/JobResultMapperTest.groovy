package com.edrone.mapper

import com.edrone.dto.JobResultDto
import com.edrone.entity.JobResults
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class JobResultMapperTest extends Specification {

    @Autowired
    private JobResultMapper mapper

    private static final UUID JOB_ID = UUID.fromString("05719a58-5219-11ed-bdc3-0242ac120002")
    private static final String FILE_NAME = "TestFileName"

    def "MapDtoToEntity"() {
        given:
        JobResultDto jobResultDto = new JobResultDto(FILE_NAME)

        when:
        JobResults result = mapper.mapDtoToEntity(JOB_ID, jobResultDto)

        then:
        result.getId() == JOB_ID
        result.getFileNameOnAws() == FILE_NAME

    }

    def "MapEntityToDto"() {
        given:
        JobResults jobResults = new JobResults(JOB_ID, FILE_NAME)

        when:
        JobResultDto result = mapper.mapEntityToDto(jobResults)

        then:
        result.getFileNameOnAws() == FILE_NAME
    }
}
