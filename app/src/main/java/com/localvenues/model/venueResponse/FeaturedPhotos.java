
package com.localvenues.model.venueResponse;

import java.util.ArrayList;
import java.util.List;

public class FeaturedPhotos {

    private Integer count;
    private List<Item___> items = new ArrayList<Item___>();

    /**
     * 
     * @return
     *     The count
     */
    public Integer getCount() {
        return count;
    }

    /**
     * 
     * @param count
     *     The count
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * 
     * @return
     *     The items
     */
    public List<Item___> getItems() {
        return items;
    }

    /**
     * 
     * @param items
     *     The items
     */
    public void setItems(List<Item___> items) {
        this.items = items;
    }

}
