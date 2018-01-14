package info.upump.wardrobe3.model;

import android.graphics.Bitmap;

/**
 * Created by explo on 14.01.2018.
 */

public class Mask {
    private Bitmap bitmap;
    private String name;

    public Mask(Bitmap bitmap, String name) {
        this.bitmap = bitmap;
        this.name = name;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}