
package com.localvenues.model.venueResponse;

import java.util.ArrayList;
import java.util.List;

public class Item {

    private Reasons reasons;
    private Venue venue;
    private List<Tip> tips = new ArrayList<Tip>();
    private String referralId;

    /**
     * 
     * @return
     *     The reasons
     */
    public Reasons getReasons() {
        return reasons;
    }

    /**
     * 
     * @param reasons
     *     The reasons
     */
    public void setReasons(Reasons reasons) {
        this.reasons = reasons;
    }

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
