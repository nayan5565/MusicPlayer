package com.example.nayan.musicplayer;

import java.util.ArrayList;

/**
 * Created by Nayan on 12/1/2017.
 */

public class MMp3 {
    private int Id, InApp, View, NumberOfPhotos, CategoryId, UpdateAvailable, Status, TotalLike;
    private String Title, Photo, Thumb, MainSound, CategoryTitle, Keyword, Description, UpdateDateTime;
    private float price;
    private ArrayList<MSound> sounds;
    private ArrayList<MWallpaper2> wallpaper;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getInApp() {
        return InApp;
    }

    public void setInApp(int inApp) {
        InApp = inApp;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getView() {
        return View;
    }

    public void setView(int view) {
        View = view;
    }

    public int getNumberOfPhotos() {
        return NumberOfPhotos;
    }

    public void setNumberOfPhotos(int numberOfPhotos) {
        NumberOfPhotos = numberOfPhotos;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    public int getUpdateAvailable() {
        return UpdateAvailable;
    }

    public void setUpdateAvailable(int updateAvailable) {
        UpdateAvailable = updateAvailable;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public int getTotalLike() {
        return TotalLike;
    }

    public void setTotalLike(int totalLike) {
        TotalLike = totalLike;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getThumb() {
        return Thumb;
    }

    public void setThumb(String thumb) {
        Thumb = thumb;
    }

    public String getMainSound() {
        return MainSound;
    }

    public void setMainSound(String mainSound) {
        MainSound = mainSound;
    }

    public String getCategoryTitle() {
        return CategoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        CategoryTitle = categoryTitle;
    }

    public String getKeyword() {
        return Keyword;
    }

    public void setKeyword(String keyword) {
        Keyword = keyword;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getUpdateDateTime() {
        return UpdateDateTime;
    }

    public void setUpdateDateTime(String updateDateTime) {
        UpdateDateTime = updateDateTime;
    }

    public ArrayList<MSound> getSounds() {
        return sounds;
    }

    public void setSounds(ArrayList<MSound> sounds) {
        this.sounds = sounds;
    }

    public ArrayList<MWallpaper2> getWallpaper() {
        return wallpaper;
    }

    public void setWallpaper(ArrayList<MWallpaper2> wallpaper) {
        this.wallpaper = wallpaper;
    }
}
