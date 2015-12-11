
package com.forsquare_android_vternovoi.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang.builder.ToStringBuilder;

//ok
public class Contact {

    @SerializedName("formattedPhone")
    @Expose
    private String formattedPhone;

    /**
     * @return The formattedPhone
     */
    public String getFormattedPhone() {
        return formattedPhone;
    }

    /**
     * @param formattedPhone The formattedPhone
     */
    public void setFormattedPhone(String formattedPhone) {
        this.formattedPhone = formattedPhone;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
