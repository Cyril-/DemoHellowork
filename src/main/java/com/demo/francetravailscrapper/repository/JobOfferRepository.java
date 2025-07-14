package com.demo.francetravailscrapper.repository;

import com.demo.francetravailscrapper.repository.models.JobOfferDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JobOfferRepository extends MongoRepository<JobOfferDocument, String> {
}
