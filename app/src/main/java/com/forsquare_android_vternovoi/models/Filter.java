
package com.forsquare_android_vternovoi.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang.builder.ToStringBuilder;


public class Filter {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("key")
    @Expose
    private String key;

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
     * @return The key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key The key
     */
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
