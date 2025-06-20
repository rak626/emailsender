package com.rakesh.emailsender.dto;

import lombok.Data;

import java.util.List;

@Data
public class RecipientDescription {
    private String name;
    private String position;
    private List<String> emails;
    private String linkedin;
}
