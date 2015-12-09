
package com.forsquare_android_vternovoi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang.builder.ToStringBuilder;


public class Item___ {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("prefix")
    @Expose
    private String prefix;
    @SerializedName("suffix")
    @Expose
    private String suffix;
    @SerializedName("width")
    @Expose
    private Long width;
    @SerializedName("height")
    @Expose
    private Long height;
    @SerializedName("user")
    @Expose
    private User_ user;
    @SerializedName("visibility")
    @Expose
    private String visibility;

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
     * @return The prefix
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * @param prefix The prefix
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * @return The suffix
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * @param suffix The suffix
     */
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    /**
     * @return The width
     */
    public Long getWidth() {
        return width;
    }

    /**
     * @param width The width
     */
    public void setWidth(Long width) {
        this.width = width;
    }

    /**
     * @return The height
     */
    public Long getHeight() {
        return height;
    }

    /**
     * @param height The height
     */
    public void setHeight(Long height) {
        this.height = height;
    }

    /**
     * @return The user
     */
    public User_ getUser() {
        return user;
    }

    /**
     * @param user The user
     */
    public void setUser(User_ user) {
        this.user = user;
    }

    /**
     * @return The visibility
     */
    public String getVisibility() {
        return visibility;
    }

    /**
     * @param visibility The visibility
     */
    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
