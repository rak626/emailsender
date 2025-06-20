package com.rakesh.emailsender.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "templateComponents")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemplateComponent {
    @Id
    private String id;
    private String name;
    private String content;
    private ContentType type;
}

