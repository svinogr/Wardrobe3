package info.upump.wardrobe3.model;

import android.content.res.Resources;

import info.upump.wardrobe3.R;

/**
 * Created by explo on 02.12.2017.
 */

public enum EnumMask {
    TSHORT(R.drawable.mask_circle){

        int name = R.string.name_mask_tshort;

        @Override
       public int getVisibleName() {
            return name;
        }
    },
    PANTS(R.drawable.mask1){
        int name = R.string.name_mask_paints;
        @Override
        public int getVisibleName() {
            return name;
        }
    };
    
    private int resource;

    public abstract int getVisibleName();


    EnumMask(int resource) {
        this.resource = resource;
    }

    public int getResource() {
        return resource;
    }
}
