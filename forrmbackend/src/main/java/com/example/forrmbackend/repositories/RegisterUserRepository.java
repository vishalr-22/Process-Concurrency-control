package com.example.forrmbackend.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.forrmbackend.models.registeruser;

public interface RegisterUserRepository extends MongoRepository<registeruser, String> {

    

    @Query("{ firstName : { $regex : ?0, $options: 'i' } }")
     List<registeruser> getUserByFirstNameRegEx(String firstName);

    
}
