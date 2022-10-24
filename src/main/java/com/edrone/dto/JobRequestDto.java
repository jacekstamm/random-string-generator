package com.edrone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JobRequestDto {

    private int minLength;
    private int maxLength;
    private String chars;
    private int results;
}
