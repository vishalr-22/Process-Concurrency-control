package com.example.forrmbackend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.forrmbackend.models.processRequest;
import com.example.forrmbackend.repositories.ProcessRequestRepository;

import org.springframework.data.domain.Pageable;

@RestController
public class ProcessRequestController {
    private final ProcessRequestRepository processRequestRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    public ProcessRequestController(ProcessRequestRepository processRequestRepository) {
        this.processRequestRepository = processRequestRepository;

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/fetchProcesses")
    public List<processRequest> findUser() {
        return processRequestRepository.findAll();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/addProcess")
    public processRequest addProcess(
            @RequestParam(required = true) String processId,
            @RequestParam(required = true) String processTime) {
        processRequest k = processRequestRepository
                .save(new processRequest(processId, processTime));
        return k;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/removeProcess")
    public void removeProcess(
            @RequestParam(required = true) String id) {
                processRequestRepository.deleteById(id);
    }
}
