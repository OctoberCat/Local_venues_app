package com.forsquare_android_vternovoi.foursquareApi;

import com.squareup.okhttp.OkHttpClient;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by valentin on 09.12.15.
 */
public class FoursquareService {
    public static String API_BASE = "https://api.foursquare.com/v2/";

    private static OkHttpClient httpClient = new OkHttpClient();
    private static Retrofit.Builder builder =
            new Retrofit.Builder().baseUrl(API_BASE).addConverterFactory(GsonConverterFactory.create());


    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(httpClient).build();
        return retrofit.create(serviceClass);
    }
}
