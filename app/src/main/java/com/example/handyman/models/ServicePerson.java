package com.example.handyman.models;

import android.content.Context;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import de.hdodenhof.circleimageview.CircleImageView;

public class ServicePerson extends BaseObservable {

    private float rating;
    private String userId;
    private String name;
    private String email;
    private String reason;
    private double price;
    private String styleItem;
    private double latitude;
    private double longitude;
    private String occupation;
    private String response;
    private String location;
    private String date;
    private String about;
    private String number;
    private String accountType;
    private String image;
    private String distanceBetween;
    private String senderPhoto;
    private String senderName;
    private String handyManName;
    private String handyManPhoto;



    public ServicePerson() {
    }

    public ServicePerson(String userId, String name, String email, String accountType) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.accountType = accountType;
    }

    public ServicePerson(double price, String styleItem, String image) {
        this.price = price;
        this.styleItem = styleItem;
        this.image = image;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Bindable
    public float getRating() {
        return rating;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Bindable
    public String getReason() {
        return reason;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Bindable
    public String getLocation() {
        return location;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Bindable
    public String getDate() {
        return date;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    @Bindable
    public String getAbout() {
        return about;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Bindable
    public String getNumber() {
        return number;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    @Bindable
    public String getAccountType() {
        return accountType;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDistanceBetween() {
        return distanceBetween;
    }

    public void setDistanceBetween(String distanceBetween) {
        this.distanceBetween = distanceBetween;
    }

    public String getSenderPhoto() {
        return senderPhoto;
    }

    public void setSenderPhoto(String senderPhoto) {
        this.senderPhoto = senderPhoto;
    }

    @Bindable
    public String getImage() {
        return image;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    @Bindable
    public String getSenderName() {
        return senderName;
    }

    public void setHandyManName(String handyManName) {
        this.handyManName = handyManName;
    }

    public String getHandyManPhoto() {
        return handyManPhoto;
    }

    public void setHandyManPhoto(String handyManPhoto) {
        this.handyManPhoto = handyManPhoto;
    }

    @Bindable
    public String getHandyManName() {
        return handyManName;
    }

    @Bindable
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Bindable
    public String getStyleItem() {
        return styleItem;
    }

    public void setStyleItem(String styleItem) {
        this.styleItem = styleItem;
    }

    @BindingAdapter("imageUrl")
    public static void loadImages(CircleImageView imageView, String imageUrl) {
        Context context = imageView.getContext();
        Glide.with(context)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }
}
