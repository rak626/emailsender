package com.rakesh.emailsender.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "templates")
@CompoundIndex(def = "{'recipientType': 1, 'emailType': 1, 'version': 1}", unique = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Template {

    @Id
    private String id;

    private RecipientType recipientType;
    private EmailType emailType;
    private String version;
    private String subject;
    private String body;

    private String mainContentId;
    private String headerId;
    private String footerId;
    private String styleId;

    @Transient
    private TemplateComponent mainContent;

    @Transient
    private TemplateComponent header;

    @Transient
    private TemplateComponent footer;

    @Transient
    private TemplateComponent style;
}
