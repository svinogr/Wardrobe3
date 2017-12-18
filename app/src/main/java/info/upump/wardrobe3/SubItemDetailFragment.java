package info.upump.wardrobe3;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import info.upump.wardrobe3.db.SubItemTableDao;
import info.upump.wardrobe3.model.SubItem;

/**
 * Created by explo on 13.12.2017.
 */

public class SubItemDetailFragment extends Fragment implements View.OnClickListener {
    public static final String TAG = "subFragmentDetail";
    private long idSubItem;
    private SubItemTableDao dbDao;
    private SubItem subItem;
    private EditText name;
    private EditText cost;
    private EditText description;
    private ImageView image;
    private Button cancelBtn, okBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
      //  idSubItem = getArguments().getLong("idSubItem");
        View root = inflater.inflate(R.layout.dialog_fragment_sub_item_detail, container, false);
        name = (EditText) root.findViewById(R.id.detail_name);
        cost = (EditText)root.findViewById(R.id.detail_cost);
        description = (EditText) root.findViewById(R.id.detail_description);
        image = (ImageView) root.findViewById(R.id.detail_img);
        cancelBtn = (Button)root.findViewById(R.id.detail_btn_cancel);
        okBtn = (Button) root.findViewById(R.id.detail_btn_ok);

        image.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
        okBtn.setOnClickListener(this);
        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dbDao = new SubItemTableDao(context);
        Cursor cursor = dbDao.getById(getArguments().getLong("idSubItem"));
        if(cursor.moveToFirst()){
            do {
                subItem = new SubItem();
                subItem.setId(cursor.getInt(0));
                subItem.setName(cursor.getString(1));
                subItem.setImg(cursor.getString(2));
                subItem.setCost(cursor.getFloat(3));
                // TODO дату вписать
                subItem.setIdMainItem(cursor.getLong(4));
                subItem.setDescription(cursor.getString(5));
            }while (cursor.moveToNext());
        }

    }

    @Override
    public void onClick(View v) {

    }
}
