package com.rakesh.emailsender.repo;

import com.rakesh.emailsender.entity.Application;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ApplicationRepo extends MongoRepository<Application, String> {

}
