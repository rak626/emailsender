package com.rakesh.emailsender.repo;

import com.rakesh.emailsender.entity.TemplateComponent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TemplateComponentRepo extends MongoRepository<TemplateComponent, String> {
}

