package com.o4codes.copaste.models;
import javafx.beans.property.SimpleStringProperty;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class Clip {

    public SimpleStringProperty user = new SimpleStringProperty("None");
    public SimpleStringProperty content = new SimpleStringProperty("No Content");
    private String contentType;
    public SimpleStringProperty createdAt = new SimpleStringProperty("1234");

    public Clip(){
        this.createdAt.set(String.valueOf(new Date().getTime()));
    }

    public Clip(String user, String content, String contentType) {
        this.user.set(user);
        this.content.set(content);
        this.contentType = contentType;
        this.createdAt.set(LocalTime.now()
                .truncatedTo(ChronoUnit.SECONDS)
                .format(DateTimeFormatter.ISO_LOCAL_TIME));
    }

    public String getUser() {
        return this.user.get();
    }

    public String getContent() {
        return this.content.get();
    }

    public String getContentType() {
        return this.contentType;
    }

    public String getCreatedAt() {
        return createdAt.get();
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt.set(createdAt);
    }

    public void setUser(String user) {
        this.user.set(user);
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
        this.createdAt.set(LocalTime.now()
                .truncatedTo(ChronoUnit.SECONDS)
                .format(DateTimeFormatter.ISO_LOCAL_TIME));
    }

}

