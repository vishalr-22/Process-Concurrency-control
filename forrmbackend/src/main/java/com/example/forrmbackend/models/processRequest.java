package com.example.forrmbackend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

public class processRequest {
    @Id
    private String id;

    @Field
    private String processId;

    @Field
    private String processTime;

    public processRequest() {
    }

    public processRequest(String processId, String processTime) {
        this.processId = processId;
        this.processTime = processTime;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getprocessId() {
        return this.processId;
    }

    public void setprocessId(String processId) {
        this.processId = processId;
    }

    public String getprocessTime() {
        return this.processTime;
    }

    public void setprocessTime(String processTime) {
        this.processTime = processTime;
    }

    public String toString() {
        return String.format("User[id='%s' processId='%s',processTime='%s']",
                id, processId, processTime);
    }
}
