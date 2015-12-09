
package com.forsquare_android_vternovoi.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang.builder.ToStringBuilder;


public class Model {


    @SerializedName("response")
    @Expose
    private Response response;

    /**
     * @return The response
     */
    public Response getResponse() {
        return response;
    }

    /**
     * @param response The response
     */
    public void setResponse(Response response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
