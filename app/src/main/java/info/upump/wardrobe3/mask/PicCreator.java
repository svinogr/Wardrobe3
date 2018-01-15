package info.upump.wardrobe3.mask;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import info.upump.wardrobe3.model.EnumMask;

/**
 * Created by explo on 15.01.2018.
 */

public class PicCreator {
    private Context context;
    private int width = 300;
    private int height = 300;

    public PicCreator(Context context) {
        this.context = context;
    }

    public Bitmap getSimpleMaskBitmap(EnumMask enumMask){
        Bitmap mask  = Bitmap.createScaledBitmap( Bitmap.createBitmap(
                BitmapFactory.decodeResource(context.getResources(), enumMask.getResource())),width,height, false);

        Bitmap background = Bitmap.createBitmap(300, 300,
                Bitmap.Config.ARGB_8888);
        background.eraseColor(Color.GREEN);

        Bitmap result = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas mCanvas = new Canvas(result);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

        mCanvas.drawBitmap(background, 0, 0, null);
        mCanvas.drawBitmap(mask, 0, 0, paint);
        return result;
    }
    public Bitmap getMaskWithImage(EnumMask enumMask){
        return null;
    }
}
