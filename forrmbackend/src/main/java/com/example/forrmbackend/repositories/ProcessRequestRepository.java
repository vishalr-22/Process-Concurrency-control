package com.example.forrmbackend.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.forrmbackend.models.processRequest;

public interface ProcessRequestRepository extends MongoRepository<processRequest, String> {
    
}
