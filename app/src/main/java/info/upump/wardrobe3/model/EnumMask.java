package info.upump.wardrobe3.model;

import info.upump.wardrobe3.R;

/**
 * Created by explo on 02.12.2017.
 */

public enum EnumMask {
    TSHORT(R.drawable.mask_circle),
    PANTS(R.drawable.mask1);
    private int resource;

    EnumMask(int resource) {
        this.resource = resource;
    }

    public int getResource() {
        return resource;
    }
}
