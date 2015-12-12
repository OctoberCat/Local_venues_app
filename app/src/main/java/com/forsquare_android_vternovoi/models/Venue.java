
package com.forsquare_android_vternovoi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang.builder.ToStringBuilder;


public class Venue {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("contact")
    @Expose
    private Contact contact;
    @SerializedName("location")
    @Expose
    private Location location;

    @SerializedName("stats")
    @Expose
    private Stats stats;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("rating")
    @Expose
    private Double rating;
    @SerializedName("ratingColor")
    @Expose
    private String ratingColor;
    /*    @SerializedName("hours")
        @Expose
        private Hours hours;*/
    @SerializedName("photos")
    @Expose
    private Photos photos;
    @SerializedName("featuredPhotos")
    @Expose
    private FeaturedPhotos featuredPhotos;

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The contact
     */
    public Contact getContact() {
        return contact;
    }

    /**
     * @param contact The contact
     */
    public void setContact(Contact contact) {
        this.contact = contact;
    }

    /**
     * @return The location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @param location The location
     */
    public void setLocation(Location location) {
        this.location = location;
    }


    /**
     * @return The stats
     */
    public Stats getStats() {
        return stats;
    }

    /**
     * @param stats The stats
     */
    public void setStats(Stats stats) {
        this.stats = stats;
    }

    /**
     * @return The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return The rating
     */
    public Double getRating() {
        return rating;
    }

    /**
     * @param rating The rating
     */
    public void setRating(Double rating) {
        this.rating = rating;
    }

    /**
     * @return The ratingColor
     */
    public String getRatingColor() {
        return ratingColor;
    }

    /**
     * @param ratingColor The ratingColor
     */
    public void setRatingColor(String ratingColor) {
        this.ratingColor = ratingColor;
    }

   /* *//**
     * @return The hours
     *//*
    public Hours getHours() {
        return hours;
    }

    *//**
     * @param hours The hours
     *//*
    public void setHours(Hours hours) {
        this.hours = hours;
    }
*/

    /**
     * @return The photos
     */
    public Photos getPhotos() {
        return photos;
    }

    /**
     * @param photos The photos
     */
    public void setPhotos(Photos photos) {
        this.photos = photos;
    }


    /**
     * @return The featuredPhotos
     */
    public FeaturedPhotos getFeaturedPhotos() {
        return featuredPhotos;
    }

    /**
     * @param featuredPhotos The featuredPhotos
     */
    public void setFeaturedPhotos(FeaturedPhotos featuredPhotos) {
        this.featuredPhotos = featuredPhotos;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
