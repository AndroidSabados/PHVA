package com.empresa.phva.db;

import androidx.annotation.NonNull;

public class Document {
    private int id;
    private String type;
    private String description;
    private String justification;
    private String severity;
    private String date;

    public Document() {
    }

    public Document(String type, String description, String justification, String severity) {
        //this.id = id;
        this.type = type;
        this.description = description;
        this.justification = justification;
        this.severity = severity;
        //this.date = date;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @NonNull
    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", descriptio='" + description + '\'' +
                ", justification='" + justification + '\'' +
                ", severity='" + severity + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}

