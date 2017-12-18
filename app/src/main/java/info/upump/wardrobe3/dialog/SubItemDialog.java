package info.upump.wardrobe3.dialog;

import android.app.AlertDialog;
import android.app.Dialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import info.upump.wardrobe3.FragmentController;
import info.upump.wardrobe3.R;
import info.upump.wardrobe3.ViewFragmentController;
import info.upump.wardrobe3.model.EnumMask;
import info.upump.wardrobe3.model.MainMenuItem;
import info.upump.wardrobe3.model.SubItem;

/**
 * Created by explo on 18.12.2017.
 */

public class SubItemDialog extends DialogFragment {
    private AlertDialog.Builder builder;
    public static final String TAG = "dialogMain";
    private ViewFragmentController viewFragmentController;
    private EditText name;
    private EditText cost;
    private EditText description;
    private long idParent;
    private long id;
    private ImageView image;



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getActivity() instanceof FragmentController) {
            viewFragmentController = (ViewFragmentController) ((FragmentController) getActivity()).getCurrentFragment();
        }
        System.out.println(viewFragmentController);
        setCancelable(false);
        int operation = getArguments().getInt(OperationAsync.OPERATION);
        idParent = getArguments().getLong(Constants.ID_PARENT);

        builder = new AlertDialog.Builder(getActivity());

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();

        View inflate = layoutInflater.inflate(R.layout.dialog_fragment_sub_item_detail, null);
        name = inflate.findViewById(R.id.detail_name);
        cost = inflate.findViewById(R.id.detail_cost);
        description = inflate.findViewById(R.id.detail_description);
        image = inflate.findViewById(R.id.detail_img);

        builder.setView(inflate);

        switch (operation) {
            case MainItemOperationAsync.SAVE:
                builder = createSaveDialog();
                break;
            case MainItemOperationAsync.UPDATE:
                builder = createUpdateDialog();
                break;
            case MainItemOperationAsync.OPEN:
                builder = createOpenDetailDialog();
                break;
        }

        return builder.create();

    }

    private AlertDialog.Builder createOpenDetailDialog() {
        name.setText(getArguments().getString(Constants.NAME));
        cost.setText(String.valueOf(getArguments().getFloat(Constants.COST)));
        description.setText(getArguments().getString(Constants.DESCRIPTION));
        image.setImageURI(Uri.parse(getArguments().getString(Constants.IMG)));

        builder.setTitle(R.string.title_dialog_sub_item_open_detail).
                setNegativeButton(R.string.negative_btn_dialog_sub_item_open_detail, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("No", "No");
                        dismiss();

                    }
                });
        return builder;

    }

    private AlertDialog.Builder createUpdateDialog() {
        System.out.println("createSaveSubDialog");
        name.setText(getArguments().getString(Constants.NAME));
        cost.setText(String.valueOf(getArguments().getFloat(Constants.COST)));
        description.setText(getArguments().getString(Constants.DESCRIPTION));
        image.setImageURI(Uri.parse(getArguments().getString(Constants.IMG)));
        id = getArguments().getLong(Constants.ID);

        builder.setTitle(R.string.title_dialog_update_sub_item).
                setPositiveButton(R.string.positiv_btn_dialog_new_main_item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (name.getText().toString().equals("")) {
                            return;
                        }
                        SubItem subItem = new SubItem();
                        subItem.setId(id);
                        subItem.setIdMainItem(idParent);
                        subItem.setName(name.getText().toString());
                        try {

                            subItem.setCost(Float.parseFloat(cost.getText().toString()));
                        } catch (NumberFormatException e) {
                            subItem.setCost(0);
                        }
                        subItem.setDescription(description.getText().toString());
                        subItem.setImg("");

                        if (viewFragmentController != null) {
                            System.out.println(viewFragmentController);
                            viewFragmentController.updateItem(subItem);
                        }
                        System.out.println(viewFragmentController);
                    }
                })
                .setNegativeButton(R.string.negative_btn_dialog_new_main_item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("No", "No");

                        viewFragmentController.cancelUpdate();
                    }
                });

        return builder;


    }

    private AlertDialog.Builder createSaveDialog() {
        builder.setTitle(R.string.title_dialog_create_sub_item).
                setPositiveButton(R.string.positiv_btn_dialog_new_main_item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (name.getText().toString().equals("")) {
                            return;
                        }
                        SubItem subItem = new SubItem();
                        subItem.setIdMainItem(idParent);
                        subItem.setName(name.getText().toString());
                        System.out.println(cost.getText().toString());
                        try {
                            subItem.setCost(Float.parseFloat(cost.getText().toString()));

                        } catch (NumberFormatException e) {
                            subItem.setCost(0);
                        }
                        subItem.setDescription(description.getText().toString());
                        subItem.setImg("");

                        if (viewFragmentController != null) {
                            viewFragmentController.addNewItem(subItem);
                        }
                    }
                })
                .setNegativeButton(R.string.negative_btn_dialog_new_main_item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("No", "No");

                    }
                });

        return builder;
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }
}
