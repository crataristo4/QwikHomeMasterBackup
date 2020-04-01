package com.example.handyman.models;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class StylesItemModel extends BaseObservable {

    public int price;
    public String styleItem;
    public String image;
    public double rating;


    public StylesItemModel() {
    }

    public StylesItemModel(int price, String styleItem, String image) {
        this.price = price;
        this.styleItem = styleItem;
        this.image = image;
    }

    public StylesItemModel(int price, String styleItem, String image, double rating) {
        this.price = price;
        this.styleItem = styleItem;
        this.image = image;
        this.rating = rating;
    }

    @Bindable
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Bindable
    public String getStyleItem() {
        return styleItem;
    }

    public void setStyleItem(String styleItem) {
        this.styleItem = styleItem;
    }

    @Bindable
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Bindable
    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
