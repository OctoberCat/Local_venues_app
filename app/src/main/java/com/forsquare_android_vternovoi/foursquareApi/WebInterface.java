package com.forsquare_android_vternovoi.foursquareApi;

import com.forsquare_android_vternovoi.models.VenueResponse;

import java.util.Map;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.QueryMap;

/**
 * Created by valentin on 10.12.15.
 */
public interface WebInterface {

    String LL_PARAMETER = "ll";
    String RADIUS_PARAMETER = "radius";
    String LIMIT_PARAMETER = "limit";
    String OFFSET_PARAMETER = "offset";
    String VENUEPHOTOS_PARAMETER = "venuePhotos";
    String CLIENT_ID = "KEFRSV01QF2O0R4WANEQKFADJ0DWMDIEPWPQDZWW5GS1YUZH";
    String CLIENT_SECRET = "KJQ0PCVM4JK0Y5QCRIBSTVG3Z4B3JKK5PTMCFJJQE2PEUKQB";
    String VERSION = "20151210";

    //parameters ll, radius, limit(10), offset, venuePhotos
    @GET("/v2/venues/explore")
    Call<VenueResponse> exploreVenues(@QueryMap Map<String, String> parameters);
}
