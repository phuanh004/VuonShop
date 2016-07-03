package com.example.anhpham.vuonshop.Model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Anh Pham on 19/06/2016.
 */

public class ProductInCart extends RealmObject {
    @PrimaryKey
    private int position;
    private int id;
    private String name, image, description;
    private float regularPrice, salePrice;

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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
