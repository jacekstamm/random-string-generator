package com.edrone.controller

import com.edrone.dto.JobRequestDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@SpringBootTest
@AutoConfigureMockMvc
class StringGeneratorControllerTest extends Specification {

    @Autowired
    private MockMvc mockMvc

    def 'should receive 400 status when jobRequest in invalid'() {
        given:
        JobRequestDto request = new JobRequestDto(0, 1, 'ABC', 5)

        expect:
        mockMvc.perform(post('/generator/runJob')
                .contentType(MediaType.APPLICATION_JSON)
                .content(request.toString()))
                .andExpect(status().isBadRequest())
    }
}
