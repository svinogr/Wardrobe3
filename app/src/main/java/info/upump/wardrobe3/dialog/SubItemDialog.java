package info.upump.wardrobe3.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;

import info.upump.wardrobe3.FragmentController;
import info.upump.wardrobe3.R;
import info.upump.wardrobe3.ViewFragmentController;
import info.upump.wardrobe3.db.SubItemTableDao;
import info.upump.wardrobe3.model.SubItem;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static info.upump.wardrobe3.SubFragment.CAMERA_RESULT;
import static info.upump.wardrobe3.SubFragment.CHOOSE_PHOTO_RESULT;

/**
 * Created by explo on 18.12.2017.
 */

public class SubItemDialog extends DialogFragment implements View.OnClickListener {
    private AlertDialog.Builder builder;
    public static final String TAG = "dialogSubDetail";
    private ViewFragmentController viewFragmentController;
    private AppCompatActivity activity;
    private EditText name;
    private EditText cost;
    private EditText description;
    private long idParent;
    private long id;
    private ImageView image;
    private File fileFromDialog;
    private Uri uriFromDialog;


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

        image.setOnClickListener(this);

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
        image.setOnClickListener(null);

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
        SubItemTableDao subItemTableDao = new SubItemTableDao(getContext());
        Cursor byId = subItemTableDao.getById(getArguments().getLong(Constants.ID));
        if (byId.moveToFirst()) {
            do {

                id = (byId.getInt(0));
                name.setText(byId.getString(1));
                image.setImageURI(Uri.parse((byId.getString(2))));
                cost.setText(String.valueOf((byId.getFloat(3))));
                // TODO дату вписать

                description.setText((byId.getString(5)));

            } while (byId.moveToNext());
        }
        // subItemTableDao.close();
        System.out.println("createSaveSubDialog");
      /*  name.setText(getArguments().getString(Constants.NAME));
        cost.setText(String.valueOf(getArguments().getFloat(Constants.COST)));
        description.setText(getArguments().getString(Constants.DESCRIPTION));
        image.setImageURI(Uri.parse(getArguments().getString(Constants.IMG)));
        id = getArguments().getLong(Constants.ID);
*/
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
                        System.out.println(cost.getText().toString());
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
    public void onClick(View v) {
        System.out.println(v.getId());
        switch (v.getId()) {
            case R.id.detail_img:
                System.out.println("картинка");
                CameraOrChoosePhotoDialog cameraOrChoosePhotoDialog = new CameraOrChoosePhotoDialog();
                //  FragmentManager supportFragmentManager = activity.getSupportFragmentManager();
                cameraOrChoosePhotoDialog.show(getActivity().getSupportFragmentManager(), CameraOrChoosePhotoDialog.TAG);
                break;
        }
    }
/*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       activity =  (AppCompatActivity)context;
    }*/


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
       // super.onActivityResult(requestCode, resultCode, data);
        CameraOrChoosePhotoDialog cameraOrChoosePhotoDialog = (CameraOrChoosePhotoDialog) getActivity().getSupportFragmentManager().findFragmentByTag(CameraOrChoosePhotoDialog.TAG);
        System.out.println(resultCode);
        System.out.println(cameraOrChoosePhotoDialog);
        System.out.println("main activity result");
        System.out.println("result activity "+TAG+" "+ requestCode+ ""+resultCode);
        System.out.println(RESULT_OK+" "+RESULT_CANCELED);


        switch (requestCode) {
            case CAMERA_RESULT:
                if (resultCode == RESULT_OK) {
                    fileFromDialog = cameraOrChoosePhotoDialog.getFile();
                    uriFromDialog = cameraOrChoosePhotoDialog.getUri();
                    // TODO проверка на размер экрана
                    image.setImageURI(uriFromDialog);
                    addPicToGallery();
                    System.out.println("ur " + uriFromDialog.toString());

                }
                if (resultCode == RESULT_CANCELED) {
                    fileFromDialog = cameraOrChoosePhotoDialog.getFile();
                    fileFromDialog.delete();

                }
                break;
            case CHOOSE_PHOTO_RESULT:
                if (resultCode == RESULT_OK) {
                    System.out.println(data.getData());
                    uriFromDialog = data.getData();
                    image.setImageURI(uriFromDialog);

                }
                if (resultCode == RESULT_CANCELED)
                    break;
        }
//        cameraOrChoosePhotoDialog.dismiss();

    }

    private void addPicToGallery() {

    }

}


