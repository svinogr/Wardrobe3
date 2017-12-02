package info.upump.wardrobe3.adapter;

import android.graphics.Bitmap;

import info.upump.wardrobe3.model.SubItem;

/**
 * Created by explo on 02.12.2017.
 */

interface MaskCreator {


    Bitmap createMask(SubItem subItem);
}
