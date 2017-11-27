package info.upump.wardrobe3.mask;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * Created by explo on 18.11.2017.
 */

public abstract class MaskedDrawable extends Drawable {
    public abstract void setPictureBitmap(Bitmap pictureBitmap);

    public abstract void setMaskBitmap(Bitmap maskBitmap);
    public abstract Bitmap setBitmapSize(Bitmap bitmap);

    public interface MaskedDrawableFactory {
        MaskedDrawable createMaskedDrawable();
    }
}