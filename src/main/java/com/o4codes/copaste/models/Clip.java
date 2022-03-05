package com.o4codes.copaste.models;
import javafx.beans.property.SimpleStringProperty;

import java.util.Date;

public class Clip {

    private String user;
    public SimpleStringProperty content = new SimpleStringProperty("No Content");
    private String contentType;
    public long createdAt;

    public Clip(){
        this.createdAt = new Date().getTime();
    }

    public Clip(String user, String content, String contentType) {
        this.user = user;
        this.content.set(content);
        this.contentType = contentType;
        this.createdAt = new Date().getTime();
    }

    public String getUser() {
        return this.user;
    }

    public String getContent() {
        return this.content.get();
    }

    public String getContentType() {
        return this.contentType;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setContent(String content) {
        this.content.set(content);
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void copyProperties(Clip obj) {
        this.setUser(obj.getUser());
        this.setContent(obj.getContent());
        this.setContentType(obj.getContentType());

    }

}

