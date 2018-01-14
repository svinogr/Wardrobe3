package info.upump.wardrobe3.model;

import info.upump.wardrobe3.R;

/**
 * Created by explo on 02.12.2017.
 */

public enum EnumMask {
    TSHORT(R.drawable.mask_circle){
        String name = "футболки";

        @Override
       public String getVisibleName() {
            return name;
        }
    },
    PANTS(R.drawable.mask1){
        String name = "штаны";
        @Override
        public String getVisibleName() {
            return name;
        }
    };
    private int resource;
    public abstract String getVisibleName();


    EnumMask(int resource) {
        this.resource = resource;
    }

    public int getResource() {
        return resource;
    }
}
