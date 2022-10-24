package com.edrone.service

import com.edrone.exception.BadJobRequestException
import com.edrone.exception.TooMuchResultsWantedException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification
import spock.lang.Unroll

@SpringBootTest
class StringGeneratorServiceTest extends Specification {

    @Autowired
    private StringGeneratorService service

    @Unroll
    def 'should generate all possible result from given chars'() {
        when:
        Set<String> result = service.generateUniqueStrings(wantedResults, minLength, maxLength, stringOfChars)

        then:
        result.size() == wantedResults

        where:
        wantedResults | minLength | maxLength | stringOfChars
        11            | 1         | 2         | 'ZyW'
        36            | 2         | 3         | 'AbC'

    }

    @Unroll
    def 'should throw BadJobRequestException when jobRequest in invalid'() {
        when:
        service.checkJobRequest(resultsNumber, minLength, maxLength, stringOfChars)

        then:
        thrown(BadJobRequestException)

        where:
        resultsNumber | minLength | maxLength | stringOfChars
        0             | 1         | 2         | 'abc'
        2             | 0         | 4         | 'jtj5'
        10            | 2         | 0         | 'KT4D'
        3             | 3         | 4         | ''
    }

    @Unroll
    def 'should throw TooMuchResultsWantedException when resultsNumber exceeds the possible combinations'() {
        when:
        service.checkAllPossibleCombination(resultsNumber, minLength, maxLength, stringOfChars)

        then:
        thrown(TooMuchResultsWantedException)

        where:
        resultsNumber | minLength | maxLength | stringOfChars
        37            | 2         | 3         | 'ABC'
        100000        | 3         | 4         | 'AbCd'
    }
}
