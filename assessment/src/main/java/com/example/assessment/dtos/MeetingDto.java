package com.example.assessment.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class MeetingDto {
    private String title;
    private Date date;
    private String resume;
    private String guests;
}
