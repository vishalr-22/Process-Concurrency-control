package com.example.forrmbackend.controllers;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.forrmbackend.models.registeruser;
import com.example.forrmbackend.repositories.RegisterUserRepository;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@RestController
@Controller
public class RegisterUserController {

    private final RegisterUserRepository registerUserRepository;

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    public RegisterUserController(RegisterUserRepository registerUserRepository) {
        this.registerUserRepository = registerUserRepository;

    }

    @CrossOrigin(origins = {"http://localhost:4200","http://10.0.0.108:4200"})
    @GetMapping("/findById")
    public Optional<registeruser> findById(
            @RequestParam(required = true) String id
            ) {

        Optional<registeruser> k = registerUserRepository.findById(id);
        return k;
    }    

    @CrossOrigin(origins = {"http://localhost:4200","http://10.0.0.108:4200"})
    @GetMapping("/addUser")
    public registeruser saveUsers(
            @RequestParam(required = true) String process,
            @RequestParam(required = true) String inputFile,
            @RequestParam(required = true) String outputFile,
            @RequestParam(required = true) Boolean processing,
            @RequestParam(required = true) Boolean toBeProcessed,
            @RequestParam(required = true) Boolean processed,
            @RequestParam(required = true) String createdBy,
            @RequestParam(required = true) String createdAt,
            @RequestParam(required = true) String updatedAt,
            @RequestParam(required = true) String processStartedAt,
            @RequestParam(required = true) String processFinishedAt) {

        registeruser k = registerUserRepository
                .save(new registeruser(process, inputFile, outputFile, processing, toBeProcessed, processed,
                        createdBy, createdAt, updatedAt, processStartedAt, processFinishedAt));
        return k;
    }

    @CrossOrigin(origins = {"http://localhost:4200","http://10.0.0.108:4200"})
    @GetMapping("/fetchUsers")
    public Page<registeruser> fetchUsers(Pageable p) {
        return registerUserRepository.findAll(p);
    }

    @CrossOrigin(origins = {"http://localhost:4200","http://10.0.0.108:4200"})
    @GetMapping("/startProcess")
    public registeruser startProcess(
            @RequestParam(required = true) String id,
            @RequestParam(required = true) Boolean toBeProcessed,
            @RequestParam(required = true) String updatedAt) {
        // Query query = new Query().addCriteria(Criteria.where("_id").is(id));
        // Update updateDefination = new Update().set("process",process);
        // FindAndModifyOptions options = new
        // FindAndModifyOptions().returnNew(true).upsert(true);
        Query select = Query.query(Criteria.where("_id").is(id));
        Update update = new Update();
        update.set("toBeProcessed", toBeProcessed);
        update.set("updatedAt", updatedAt);
        registeruser updateResult = mongoTemplate.findAndModify(select, update, registeruser.class);
        return updateResult;
        // return MongoTemplate.findAndModify(query, updateDefination, options,
        // RegisterUserController.class);
    }

    @CrossOrigin(origins = {"http://localhost:4200","http://10.0.0.108:4200"})
    @GetMapping("/startActualProcess")
    public registeruser startActualProcess(
            @RequestParam(required = true) String id,
            @RequestParam(required = false) Boolean processing,
            @RequestParam(required = false) String toBeProcessed,
            @RequestParam(required = false) String processed,
            @RequestParam(required = false) String processStartedAt,
            @RequestParam(required = false) String processFinishedAt,
            @RequestParam(required = false) Boolean updatedAt) {
        Query select = Query.query(Criteria.where("_id").is(id));
        Update update = new Update();
        if (processing != null) {
            update.set("processing", processing);
        }
        if (toBeProcessed != null) {
            update.set("toBeProcessed", toBeProcessed);
        }
        if (processed != null) {
            update.set("processed", processed);
        }
        if (processStartedAt != null) {
            update.set("processStartedAt", processStartedAt);
        }
        if (processFinishedAt != null) {
            update.set("processFinishedAt", processFinishedAt);
        }
        if (updatedAt != null) {
            update.set("updatedAt", updatedAt);
        }
        registeruser updateResult = mongoTemplate.findAndModify(select, update, registeruser.class);

        return updateResult;
    }

    @CrossOrigin(origins = {"http://localhost:4200","http://10.0.0.108:4200"})
    @GetMapping("/runProcess")
    public registeruser runProcess(
            @RequestParam(required = true) String id,
            @RequestParam(required = false) Boolean processing,
            @RequestParam(required = false) String toBeProcessed,
            @RequestParam(required = false) String processed,
            @RequestParam(required = false) String processStartedAt,
            @RequestParam(required = false) String processFinishedAt,
            @RequestParam(required = false) Boolean updatedAt,
            @RequestParam(required = true) String sessionId) {
        // Query query = new Query().addCriteria(Criteria.where("_id").is(id));
        // Update updateDefination = new Update().set("process",process);
        // FindAndModifyOptions options = new
        // FindAndModifyOptions().returnNew(true).upsert(true);
        Query select = Query.query(Criteria.where("_id").is(id));
        Update update = new Update();
        if (processing != null) {
            update.set("processing", processing);
        }
        if (toBeProcessed != null) {
            update.set("toBeProcessed", toBeProcessed);
        }
        if (processed != null) {
            update.set("processed", processed);
        }
        if (processStartedAt != null) {
            update.set("processStartedAt", processStartedAt);
        }
        if (processFinishedAt != null) {
            update.set("processFinishedAt", processFinishedAt);
        }
        if (updatedAt != null) {
            update.set("updatedAt", updatedAt);
        }
        registeruser updateResult = mongoTemplate.findAndModify(select, update, registeruser.class);

        int percent = -20;

        if (processing == true){
            for (int i = 0; i < 6; i++) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                percent = percent + 20;
                requestFunction(String.valueOf(percent), sessionId);
            }
    
        }
        
        return updateResult;

    }

    public void requestFunction(String message, String sessionId) {
        String url = "/topic/greetings" + sessionId;
        this.template.convertAndSend(url, message);
        
    }

    @CrossOrigin(origins = {"http://localhost:4200","http://10.0.0.108:4200"})
    @GetMapping("/finishProcess")
    public registeruser finishProcess(
            @RequestParam(required = true) String id,
            @RequestParam(required = false) Boolean processing,
            @RequestParam(required = false) String processed,
            @RequestParam(required = false) String processStartedAt,
            @RequestParam(required = false) String processFinishedAt) {

        Query select = Query.query(Criteria.where("_id").is(id));
        Update update = new Update();
        if (processing != null) {
            update.set("processing", processing);
        }
        if (processed != null) {
            update.set("processed", processed);
        }
        if (processStartedAt != null) {
            update.set("processStartedAt", processStartedAt);
        }
        if (processFinishedAt != null) {
            update.set("processFinishedAt", processFinishedAt);
        }
        registeruser updateResult = mongoTemplate.findAndModify(select, update, registeruser.class);
        return updateResult;
    }

    @CrossOrigin(origins = {"http://localhost:4200","http://10.0.0.108:4200"})
    @MessageMapping("/hello")
    @SendTo("/topic/greetings/{uniqueId}")
    public String greeting(String message) throws Exception {
        // Thread.sleep(1000); // simulated delay
        System.out.println(message);
        return HtmlUtils.htmlEscape(message);
    }
}
