package com.eMurasu.model;

import com.eMurasu.enums.NewsState;
import com.eMurasu.enums.Priority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class News {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable = false, unique = true,updatable = false)
    private int id;
    @Column(length = 100, nullable = false)
    private String Title;
    @Column(length = 250)
    private String description;
    private Timestamp publishedAt;
    @Column(nullable = false)
    private int category;
    @Column(length = 50000, nullable = false)
    private String content;
    @Column(nullable = false)
    private int createdBy;
    private Timestamp createdAt;
    private int approvedBy;
    private Timestamp approvedAt;
    @Enumerated(EnumType.STRING)
    @Column(length = 25, nullable = false)
    private Priority priority;
    @Enumerated(EnumType.STRING)
    @Column(length = 25, nullable = false)
    private NewsState status;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Timestamp publishedAt) {
        this.publishedAt = publishedAt;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public int getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(int approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Timestamp getApprovedAt() {
        return approvedAt;
    }

    public void setApprovedAt(Timestamp approvedAt) {
        this.approvedAt = approvedAt;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public NewsState getStatus() {
        return status;
    }

    public void setStatus(NewsState status) {
        this.status = status;
    }
}
