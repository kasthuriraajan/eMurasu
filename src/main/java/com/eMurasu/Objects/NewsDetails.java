package com.eMurasu.Objects;

import com.eMurasu.enums.Priority;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class NewsDetails {
    private String Title;
    private String description;
    private int category;
    private String content;
    @Enumerated(EnumType.STRING)
    private Priority priority;

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

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}
