
package com.forsquare_android_vternovoi.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang.builder.ToStringBuilder;


public class Tip {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("createdAt")
    @Expose
    private Long createdAt;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("canonicalUrl")
    @Expose
    private String canonicalUrl;
    @SerializedName("likes")
    @Expose
    private Likes likes;
    @SerializedName("logView")
    @Expose
    private Boolean logView;

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
     * @return The createdAt
     */
    public Long getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt The createdAt
     */
    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return The text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text The text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return The canonicalUrl
     */
    public String getCanonicalUrl() {
        return canonicalUrl;
    }

    /**
     * @param canonicalUrl The canonicalUrl
     */
    public void setCanonicalUrl(String canonicalUrl) {
        this.canonicalUrl = canonicalUrl;
    }

    /**
     * @return The likes
     */
    public Likes getLikes() {
        return likes;
    }

    /**
     * @param likes The likes
     */
    public void setLikes(Likes likes) {
        this.likes = likes;
    }

    /**
     * @return The logView
     */
    public Boolean getLogView() {
        return logView;
    }

    /**
     * @param logView The logView
     */
    public void setLogView(Boolean logView) {
        this.logView = logView;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
