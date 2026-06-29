package com.fashionstore.model;

import java.sql.Timestamp;

public class Category {

    private int categoryId;
    private String categoryName;
    private String description;
    private Timestamp createdAt;

    // 🔹 Default Constructor
    public Category() {
    }

    // 🔹 Parameterized Constructor
    public Category(int categoryId, String categoryName, String description, Timestamp createdAt) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.description = description;
        this.createdAt = createdAt;
    }

    // 🔹 Getters & Setters

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
