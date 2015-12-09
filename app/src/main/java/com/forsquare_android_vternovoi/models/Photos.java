
package com.forsquare_android_vternovoi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;


public class Photos {

    @SerializedName("count")
    @Expose
    private Long count;
    @SerializedName("groups")
    @Expose
    private List<Group_> groups = new ArrayList<Group_>();

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
    public List<Group_> getGroups() {
        return groups;
    }

    /**
     * @param groups The groups
     */
    public void setGroups(List<Group_> groups) {
        this.groups = groups;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
