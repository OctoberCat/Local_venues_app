package com.localvenues.restClient;

import com.localvenues.model.tipResponse.TipsResponse;
import com.localvenues.model.venueResponse.ExploreResponse;

import java.util.Map;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.QueryMap;

public interface ApiRequests {
    String CLIENT_ID = "KZ0IATCNQRWPHXGXPIQQJ3F0QWW3B1CHOGHWH22BQMVTYZDI";
    String CLIENT_SECRET = "WHAEHHOIJ0A2MARVSA3MEY13EVY1ZRWBJK3FWTAEKVD43FTH";
    String VERSION = "20160215";
    String PREDEFINED_PARAMETERS = "?client_secret=" + CLIENT_SECRET + "&client_id=" + CLIENT_ID + "&v=" + VERSION;
    String PREDEFINED_PARAMETERS_VENUES = PREDEFINED_PARAMETERS + "&venuePhotos=1";
    String PREDEFINED_PARAMETERS_TIPS = PREDEFINED_PARAMETERS + "&sort=recent";

    @GET("/explore" + PREDEFINED_PARAMETERS_VENUES)
    Call<ExploreResponse> exploreVenues(@QueryMap Map<String, String> parameters);

    //todo the good idea is to pass limit also -> implement endless scroll in venue details
    @GET("/{venueid}/tips" + PREDEFINED_PARAMETERS_TIPS)
    Call<TipsResponse> fetchVenueTips(@Path("venueid") String venueId);
}
