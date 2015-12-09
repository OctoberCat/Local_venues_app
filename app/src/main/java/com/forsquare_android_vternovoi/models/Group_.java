
package com.forsquare_android_vternovoi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;


public class Group_ {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("count")
    @Expose
    private Long count;
    @SerializedName("items")
    @Expose
    private List<Item__> items = new ArrayList<Item__>();

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
     * @return The count
     */
    public Long getCount() {
        return count;
    }

    /**
     * @param count The count
     */
    public void setCount(Long count) {
        this.count = count;
    }

    /**
     * @return The items
     */
    public List<Item__> getItems() {
        return items;
    }

    /**
     * @param items The items
     */
    public void setItems(List<Item__> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
