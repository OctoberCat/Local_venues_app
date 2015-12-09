
package com.forsquare_android_vternovoi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;


public class Likes {

    @SerializedName("count")
    @Expose
    private Long count;
    @SerializedName("groups")
    @Expose
    private List<Object> groups = new ArrayList<Object>();
    @SerializedName("summary")
    @Expose
    private String summary;

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
     * @return The groups
     */
    public List<Object> getGroups() {
        return groups;
    }

    /**
     * @param groups The groups
     */
    public void setGroups(List<Object> groups) {
        this.groups = groups;
    }

    /**
     * @return The summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * @param summary The summary
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
