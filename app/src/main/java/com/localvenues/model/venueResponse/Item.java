
package com.localvenues.model.venueResponse;

import java.util.ArrayList;
import java.util.List;

public class Item {

    private Venue venue;
    private List<Tip> tips = new ArrayList<>();
    private String referralId;


    /**
     * 
     * @return
     *     The venue
     */
    public Venue getVenue() {
        return venue;
    }

    /**
     * 
     * @param venue
     *     The venue
     */
    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    /**
     * 
     * @return
     *     The tips
     */
    public List<Tip> getTips() {
        return tips;
    }

    /**
     * 
     * @param tips
     *     The tips
     */
    public void setTips(List<Tip> tips) {
        this.tips = tips;
    }

    /**
     * 
     * @return
     *     The referralId
     */
    public String getReferralId() {
        return referralId;
    }

    /**
     * 
     * @param referralId
     *     The referralId
     */
    public void setReferralId(String referralId) {
        this.referralId = referralId;
    }

}
