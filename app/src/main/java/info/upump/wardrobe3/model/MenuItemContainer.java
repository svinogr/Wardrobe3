package info.upump.wardrobe3.model;

import android.graphics.Bitmap;

/**
 * Created by explo on 15.01.2018.
 */

public class MenuItemContainer {
    private IItem itemMenu;
    private Bitmap itemMenuImage;

    public MenuItemContainer(IItem itemMenu, Bitmap itemMenuImage) {
        this.itemMenu = itemMenu;
        this.itemMenuImage = itemMenuImage;
    }

    public IItem getItemMenu() {
        return itemMenu;
    }

    public Bitmap getItemMenuImage() {
        return itemMenuImage;
    }

    public void setItemMenuImage(Bitmap itemMenuImage) {
        this.itemMenuImage = itemMenuImage;
    }
}
