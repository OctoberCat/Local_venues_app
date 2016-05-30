package com.localvenues.restClient;

import com.squareup.okhttp.OkHttpClient;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by valentyn on 26.05.16.
 */
public class RESTClient {

    private static ApiRequests apiRequests;
    public static final String API_ENDPOINT = "https://api.foursquare.com/v2/venues/";
    private static OkHttpClient okHttpClient = new OkHttpClient();

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(API_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create());

    public static ApiRequests createRetrofitClient(Class<ApiRequests> requestsClass) {
        if (apiRequests == null) {
            Retrofit retrofit = builder.client(okHttpClient).build();
            return apiRequests = retrofit.create(requestsClass);
        } else {
            return apiRequests;
        }
    }
}
