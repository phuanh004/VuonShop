package com.example.anhpham.vuonshop.Model;

/**
 * Created by Anh Pham on 18/06/2016.
 */

public class Product {
    private int id;
    private String name, image, description;
    private float regularPrice, salePrice;

    public Product() {
    }

    public Product(int id, String name, String image, String description, float regularPrice, float salePrice) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
        this.regularPrice = regularPrice;
        this.salePrice = salePrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getRegularPrice() {
        return regularPrice;
    }

    public void setRegularPrice(float regularPrice) {
        this.regularPrice = regularPrice;
    }

    public float getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(float salePrice) {
        this.salePrice = salePrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
