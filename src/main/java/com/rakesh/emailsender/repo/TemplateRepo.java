package com.rakesh.emailsender.repo;

import com.rakesh.emailsender.entity.EmailType;
import com.rakesh.emailsender.entity.RecipientType;
import com.rakesh.emailsender.entity.Template;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TemplateRepo extends MongoRepository<Template, String> {
    Optional<Template> findByRecipientTypeAndEmailTypeAndVersion(RecipientType type, EmailType emailType, String version);
}
