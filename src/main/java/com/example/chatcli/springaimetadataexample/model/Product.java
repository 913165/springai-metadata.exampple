package com.example.chatcli.springaimetadataexample.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

public class Product {
    private int id;

    @JsonProperty("product_name")
    private String productName;

    private String description;

    private String brand;

    // Default Constructor
    public Product() {
    }

    // Parameterized Constructor
    public Product(int id, String productName, String description, String brand) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.brand = brand;
    }

    // Copy Constructor
    public Product(Product other) {
        this.id = other.id;
        this.productName = other.productName;
        this.description = other.description;
        this.brand = other.brand;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public String getDescription() {
        return description;
    }

    public String getBrand() {
        return brand;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    // toString method
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", description='" + description + '\'' +
                ", brand='" + brand + '\'' +
                '}';
    }

    // equals method
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id &&
                Objects.equals(productName, product.productName) &&
                Objects.equals(description, product.description) &&
                Objects.equals(brand, product.brand);
    }

    // hashCode method
    @Override
    public int hashCode() {
        return Objects.hash(id, productName, description, brand);
    }

    // Builder pattern method for fluent API (optional)
    public static ProductBuilder builder() {
        return new ProductBuilder();
    }

    // Inner Builder class (optional)
    public static class ProductBuilder {
        private final Product product;

        ProductBuilder() {
            product = new Product();
        }

        public ProductBuilder id(int id) {
            product.setId(id);
            return this;
        }

        public ProductBuilder productName(String productName) {
            product.setProductName(productName);
            return this;
        }

        public ProductBuilder description(String description) {
            product.setDescription(description);
            return this;
        }

        public ProductBuilder brand(String brand) {
            product.setBrand(brand);
            return this;
        }

        public Product build() {
            return new Product(product);
        }
    }
}