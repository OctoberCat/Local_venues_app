
package com.forsquare_android_vternovoi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;


public class FeaturedPhotos {

    @SerializedName("count")
    @Expose
    private Long count;
    @SerializedName("items")
    @Expose
    private List<Item___> items = new ArrayList<Item___>();

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
    public List<Item___> getItems() {
        return items;
    }

    /**
     * @param items The items
     */
    public void setItems(List<Item___> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
