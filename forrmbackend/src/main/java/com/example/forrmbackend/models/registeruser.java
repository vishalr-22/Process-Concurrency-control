package com.example.forrmbackend.models;


// packoutputFile com.example.forrmbackend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class registeruser {
    @Id
    private String id;

    @Field
    private String process;

    @Field
    private String inputFile;

    @Field
    private String outputFile;

    @Field
    private Boolean processing;

    @Field
    private Boolean toBeProcessed;

    @Field
    private Boolean processed;

    @Field
    private String createdBy;

    @Field
    private String createdAt;

    @Field
    private String updatedAt;

    @Field
    private String processStartedAt;

    @Field
    private String processFinishedAt;


    public registeruser() {
    }

    public registeruser(String process, String inputFile, String outputFile, Boolean processing, Boolean toBeProcessed, Boolean processed, String createdBy, String createdAt, String updatedAt, String processStartedAt, String processFinishedAt) {
        this.process = process;
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        this.processing = processing;
        this.toBeProcessed = toBeProcessed;
        this.processed = processed;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.processStartedAt = processStartedAt;
        this.processFinishedAt = processFinishedAt;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getprocess() {
        return this.process;
    }

    public void setprocess(String process) {
        this.process = process;
    }

    public String getinputFile() {
        return this.inputFile;
    }

    public void setinputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public String getoutputFile() {
        return this.outputFile;
    }

    public void setoutputFile(String outputFile) {
        this.outputFile = outputFile;
    }

    public Boolean getprocessing() {
        return this.processing;
    }

    public void setoutputFile(Boolean processing) {
        this.processing = processing;
    }

    public Boolean gettoBeProcessed() {
        return this.toBeProcessed;
    }

    public void settoBeProcessed(Boolean toBeProcessed) {
        this.toBeProcessed = toBeProcessed;
    }

    public Boolean getprocessed() {
        return this.processed;
    }

    public void setprocessed(Boolean processed) {
        this.processed = processed;
    }

    public String getcreatedBy() {
        return this.createdBy;
    }

    public void setcreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getcreatedAt() {
        return this.createdAt;
    }

    public void setcreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getupdatedAt() {
        return this.updatedAt;
    }

    public void setupdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getprocessStartedAt() {
        return this.processStartedAt;
    }

    public void setprocessStartedAt(String processStartedAt) {
        this.processStartedAt = processStartedAt;
    }

    public String getprocessFinishedAt() {
        return this.processFinishedAt;
    }

    public void setprocessFinishedAt(String processFinishedAt) {
        this.processFinishedAt = processFinishedAt;
    }

    
    public String toString() {
        return String.format("User[id='%s' process='%s',inputFile='%s',outputFile='%d',createdBy='%s',createdAt='%s',updatedAt='%s']",
                id, process, inputFile, outputFile, createdBy, createdAt, updatedAt);
    }
}
