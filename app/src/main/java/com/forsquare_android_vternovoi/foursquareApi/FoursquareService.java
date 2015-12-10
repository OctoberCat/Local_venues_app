package com.forsquare_android_vternovoi.foursquareApi;

import com.squareup.okhttp.OkHttpClient;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by valentin on 09.12.15.
 */
public class FoursquareService {
    public static String API_BASE = "https://api.foursquare.com";
    //moved into interface
    /*public static String CLIENT_ID = "client_id=KEFRSV01QF2O0R4WANEQKFADJ0DWMDIEPWPQDZWW5GS1YUZH";
    public static String CLIENT_SECRET = "client_secret=KJQ0PCVM4JK0Y5QCRIBSTVG3Z4B3JKK5PTMCFJJQE2PEUKQB";
    public static String VERSION = "v=20151210";
    public static String ENDPOINT = API_BASE + "?" + CLIENT_ID + "&" + CLIENT_SECRET + "&" + VERSION;*/

    private static OkHttpClient httpClient = new OkHttpClient();
    private static Retrofit.Builder builder =
            new Retrofit.Builder().baseUrl(API_BASE).addConverterFactory(GsonConverterFactory.create());


    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(httpClient).build();
        return retrofit.create(serviceClass);
    }
}
