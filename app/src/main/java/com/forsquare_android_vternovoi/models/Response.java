
package com.forsquare_android_vternovoi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

public class Response {

    @SerializedName("totalResults")
    @Expose
    private Long totalResults;
    @SerializedName("groups")
    @Expose
    private List<Group> groups = new ArrayList<Group>();


    /**
     * @return The totalResults
     */
    public Long getTotalResults() {
        return totalResults;
    }

    /**
     * @param totalResults The totalResults
     */
    public void setTotalResults(Long totalResults) {
        this.totalResults = totalResults;
    }

    /**
     * @return The groups
     */
    public List<Group> getGroups() {
        return groups;
    }

    /**
     * @param groups The groups
     */
    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
