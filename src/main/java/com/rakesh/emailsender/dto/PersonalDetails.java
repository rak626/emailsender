package com.rakesh.emailsender.dto;

import lombok.Data;

import java.util.List;

@Data
public class PersonalDetails {
    private String name;
    private String email;
    private String mobile;
    private String github;
    private String linkedIn;
    private List<WorkExperience> workExperience;
}
