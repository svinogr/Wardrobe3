package info.upump.wardrobe3.callback;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import info.upump.wardrobe3.R;
import info.upump.wardrobe3.ViewFragmentController;
import info.upump.wardrobe3.model.MainMenuItem;

/**
 * Created by explo on 02.11.2017.
 */

public class SwipeCallback extends ItemTouchHelper.SimpleCallback {
    private ViewFragmentController<MainMenuItem> viewFragmentController;

    public SwipeCallback(ViewFragmentController viewFragmentController) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.viewFragmentController = viewFragmentController;
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        Bitmap icon;
        Paint p = new Paint();
        if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){

            View itemView = viewHolder.itemView;
            float height = (float) itemView.getBottom() - (float) itemView.getTop();
            float width = height / 3;

            if(dX > 0){
                p.setColor(recyclerView.getResources().getColor(R.color.colorBottomEdit));
                RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
                c.drawRect(background,p);
                icon = BitmapFactory.decodeResource( recyclerView.getResources(), android.R.drawable.ic_menu_edit);
                RectF icon_dest = new RectF((float) itemView.getLeft() + width ,(float) itemView.getTop() + width,(float) itemView.getLeft()+ 2*width,(float)itemView.getBottom() - width);
                c.drawBitmap(icon,null,icon_dest,p);
            } else {
                p.setColor(recyclerView.getResources().getColor(R.color.colorBottomDelete));
                RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                c.drawRect(background,p);
                icon = BitmapFactory.decodeResource( recyclerView.getResources(),  android.R.drawable.ic_menu_delete);
                RectF icon_dest = new RectF((float) itemView.getRight() - 2*width ,(float) itemView.getTop() + width,(float) itemView.getRight() - width,(float)itemView.getBottom() - width);
                c.drawBitmap(icon,null,icon_dest,p);
            }
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int temPositionMainItem = viewHolder.getAdapterPosition();
        switch (direction) {
            case 8:
                viewFragmentController.editItem(temPositionMainItem);
                break;
            case 4:
                viewFragmentController.deleteItem(temPositionMainItem);
                break;
        }

    }
}
