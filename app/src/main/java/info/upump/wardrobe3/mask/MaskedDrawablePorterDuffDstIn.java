package info.upump.wardrobe3.mask;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;

/**
 * Created by explo on 18.11.2017.
 */

public class MaskedDrawablePorterDuffDstIn extends MaskedDrawable {

    private Bitmap bitmapMask;
    private Bitmap bitmapPhoto;

    private final static int WIGHT = 300;
    private final static int HEIGHT = 300;

    private Paint bitmapMaskPaint = new Paint();
    private Paint bitmapPhotoPaint = new Paint();

    private static Bitmap mBufferBitmap;
    private static Canvas canvasBuffer;

    public static MaskedDrawableFactory getFactory() {
        return new MaskedDrawableFactory() {
            @Override
            public MaskedDrawable createMaskedDrawable() {
                return new MaskedDrawablePorterDuffDstIn();
            }
        };
    }

    public MaskedDrawablePorterDuffDstIn() {
        bitmapMaskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
    }

    @Override
    public void setPictureBitmap(Bitmap pictureBitmap) {
        if (pictureBitmap != null) {
            bitmapPhoto = setBitmapSize(pictureBitmap);
        } else {
            bitmapPhoto = null;
        }
    }

    @Override
    public void setMaskBitmap(Bitmap maskBitmap) {
        if (maskBitmap != null) bitmapMask = setBitmapSize(maskBitmap);
    }

    @Override
    public Bitmap setBitmapSize(Bitmap bitmap) {
        return Bitmap.createScaledBitmap(bitmap, WIGHT, HEIGHT, false);
    }


    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);

        mBufferBitmap = Bitmap.createBitmap(WIGHT, HEIGHT, Bitmap.Config.ARGB_8888);
        canvasBuffer = new Canvas(mBufferBitmap);
    }

    @Override
    public void draw(Canvas canvas) {
        if (bitmapPhoto == null) {
            bitmapPhoto = Bitmap.createBitmap(WIGHT, HEIGHT,
                    Bitmap.Config.ARGB_8888);
            bitmapPhoto.eraseColor(Color.GREEN);
        }

        canvasBuffer.drawBitmap(bitmapPhoto, 0, 0, null);
        canvasBuffer.drawBitmap(bitmapMask, 0, 0, bitmapMaskPaint);
        canvas.drawBitmap(mBufferBitmap, 0, 0, null);
    }

    @Override
    public void setAlpha(int alpha) {
        bitmapPhotoPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        //Not implemented
    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }

    @Override
    public int getIntrinsicWidth() {
        return bitmapMask != null ? bitmapMask.getWidth() : super.getIntrinsicWidth();
    }

    @Override
    public int getIntrinsicHeight() {
        return bitmapMask != null ? bitmapMask.getHeight() : super.getIntrinsicHeight();
    }

}
