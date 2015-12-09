
package com.forsquare_android_vternovoi.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Stats {

    @SerializedName("checkinsCount")
    @Expose
    private Long checkinsCount;
    @SerializedName("usersCount")
    @Expose
    private Long usersCount;
    @SerializedName("tipCount")
    @Expose
    private Long tipCount;

    /**
     * @return The checkinsCount
     */
    public Long getCheckinsCount() {
        return checkinsCount;
    }

    /**
     * @param checkinsCount The checkinsCount
     */
    public void setCheckinsCount(Long checkinsCount) {
        this.checkinsCount = checkinsCount;
    }

    /**
     * @return The usersCount
     */
    public Long getUsersCount() {
        return usersCount;
    }

    /**
     * @param usersCount The usersCount
     */
    public void setUsersCount(Long usersCount) {
        this.usersCount = usersCount;
    }

    /**
     * @return The tipCount
     */
    public Long getTipCount() {
        return tipCount;
    }

    /**
     * @param tipCount The tipCount
     */
    public void setTipCount(Long tipCount) {
        this.tipCount = tipCount;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
