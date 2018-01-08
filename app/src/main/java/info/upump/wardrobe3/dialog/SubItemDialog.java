package info.upump.wardrobe3.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.util.List;

import info.upump.wardrobe3.FragmentController;
import info.upump.wardrobe3.R;
import info.upump.wardrobe3.SubFragment;
import info.upump.wardrobe3.ViewFragmentController;
import info.upump.wardrobe3.db.SubItemTableDao;
import info.upump.wardrobe3.model.SubItem;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.support.v4.content.FileProvider.getUriForFile;
import static info.upump.wardrobe3.SubFragment.CAMERA_RESULT;
import static info.upump.wardrobe3.SubFragment.CHOOSE_PHOTO_RESULT;

/**
 * Created by explo on 18.12.2017.
 */

public class SubItemDialog extends DialogFragment implements View.OnClickListener {
    private static final int PERMISSION_CAMERA_CODE = 0;
    public static final String TAG = "dialogSubDetail";

    private AlertDialog.Builder builder;
    private ViewFragmentController viewFragmentController;
    private AppCompatActivity activity;
    private EditText name;
    private EditText cost;
    private EditText description;
    private FloatingActionButton takePhotoFab;
    private FloatingActionButton choosePhotoFab;

    private Uri uri;
    private File file;
    private long idParent;
    private long id;
    private ImageView image;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println(111111);
        if (savedInstanceState != null) {
            uri = Uri.parse(savedInstanceState.getString("uri"));
            System.out.println(savedInstanceState.getString("uri"));
            //   image.setImageURI(uri);
            System.out.println("2" + uri.toString());
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        System.out.println(22222);
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
        takePhotoFab = inflate.findViewById(R.id.detail_btn_take_photo);
        choosePhotoFab = inflate.findViewById(R.id.detail_btn_choose_photo);

        image.setOnClickListener(this);
        takePhotoFab.setOnClickListener(this);
        choosePhotoFab.setOnClickListener(this);

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
        choosePhotoFab.setVisibility(View.INVISIBLE);
        takePhotoFab.setVisibility(View.INVISIBLE);

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
        System.out.println(333333);
        SubItemTableDao subItemTableDao = new SubItemTableDao(getContext());
        Cursor byId = subItemTableDao.getById(getArguments().getLong(Constants.ID));
        SubItem subItemFromBD = new SubItem();
        if (byId.moveToFirst()) {
            do {
                subItemFromBD.setId(byId.getInt(0));
                subItemFromBD.setName(byId.getString(1));
                if (uri == null) {
                    subItemFromBD.setImg((byId.getString(2)));
                } else subItemFromBD.setImg(uri.toString());
                subItemFromBD.setCost(byId.getFloat(3));
                subItemFromBD.setDescription(byId.getString(5));
                // TODO дату вписать
            }
            while (byId.moveToNext());
        }
        System.out.println("createSaveSubDialog");
        name.setText(subItemFromBD.getName());
        cost.setText(String.valueOf(subItemFromBD.getCost()));
        description.setText(subItemFromBD.getDescription());
        if (subItemFromBD.getImg() != null) {
            image.setImageURI(Uri.parse(subItemFromBD.getImg()));
        }

        id = subItemFromBD.getId();

        builder.setTitle(getResources().getString(R.string.title_dialog_update_sub_item) + " " + subItemFromBD.getName()).
                setPositiveButton(R.string.positiv_btn_dialog_new_main_item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (name.getText().toString().equals("")) {
                            return;
                        }

                        SubItem subItemToDB = new SubItem();
                        subItemToDB.setId(id);
                        subItemToDB.setIdMainItem(idParent);
                        subItemToDB.setName(name.getText().toString());
                        System.out.println(cost.getText().toString());

                        try {
                            subItemToDB.setCost(Float.parseFloat(cost.getText().toString()));
                        } catch (NumberFormatException e) {
                            subItemToDB.setCost(0);
                        }

                        subItemToDB.setDescription(description.getText().toString());
                        subItemToDB.setImg(uri.toString());

                        if (viewFragmentController != null) {
                            System.out.println(viewFragmentController);
                            viewFragmentController.updateItem(subItemToDB);
                        }
                        System.out.println("viewFragmentController: " + viewFragmentController);
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
        if (uri != null) {
            image.setImageURI(uri);
        }
        builder.setTitle(R.string.title_dialog_create_sub_item).
                setPositiveButton(R.string.positiv_btn_dialog_new_main_item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (name.getText().toString().equals("")) {
                            return;
                        }
                        SubItem subItemToDB = new SubItem();
                        subItemToDB.setIdMainItem(idParent);
                        subItemToDB.setName(name.getText().toString());
                        System.out.println(cost.getText().toString());
                        try {
                            subItemToDB.setCost(Float.parseFloat(cost.getText().toString()));

                        } catch (NumberFormatException e) {
                            subItemToDB.setCost(0);
                        }
                        subItemToDB.setDescription(description.getText().toString());
                        if (uri != null) {
                            subItemToDB.setImg(uri.toString());
                        }
                        if (viewFragmentController != null) {
                            viewFragmentController.addNewItem(subItemToDB);
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
                break;
            case R.id.detail_btn_take_photo:
                checkVersion();
                break;
            case R.id.detail_btn_choose_photo:
                Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                photoPickerIntent.setType("image/*");

                getActivity().startActivityForResult(photoPickerIntent, SubFragment.CHOOSE_PHOTO_RESULT);
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
        System.out.println(resultCode);
        System.out.println("result activity " + TAG + " " + requestCode + "" + resultCode);
        System.out.println(RESULT_OK + " " + RESULT_CANCELED);


        switch (requestCode) {
            case CAMERA_RESULT:
                if (resultCode == RESULT_OK) {
                    System.out.println("OK");
                    // TODO проверка на размер экрана
                    image.setImageURI(uri);
                    addPicToGallery();
                    System.out.println("ur " + uri.toString());
                    //  cameraOrChoosePhotoDialog.dismiss();

                }
                if (resultCode == RESULT_CANCELED) {
                    System.out.println("Canseld");
                    file.delete();

                }
                break;
            case CHOOSE_PHOTO_RESULT:
                if (resultCode == RESULT_OK) {
                    System.out.println(data.getData());
                    uri = data.getData();
                    image.setImageURI(uri);

                }
                if (resultCode == RESULT_CANCELED)
                    break;
        }

    }

    private void addPicToGallery() {

    }

    private void checkVersion() {

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.CAMERA}, PERMISSION_CAMERA_CODE);
            } else takePhoto();

        } else takePhoto();
    }

    private void takePhoto() {
        Uri uri = generateUriPhoto();
        if (uri != null) {

            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                    | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);


            List<ResolveInfo> resInfoList = getActivity().getPackageManager().queryIntentActivities(cameraIntent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                getActivity().grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                getActivity().startActivityForResult(cameraIntent, SubFragment.CAMERA_RESULT);
            }


        } else dismiss();
    }

    private Uri generateUriPhoto() {
        file = createNameFile();
        uri = null;
        if (file != null) {
            System.out.println("abs file " + file.getAbsolutePath());


            String aut = getActivity().getPackageName() + ".fileprovider";
            //   String aut = getActivity().getPackageName() ;
            uri = getUriForFile(getActivity(), aut, file);
            System.out.println("v dialogr uri " + uri);


        }
        return uri;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//проверяем ответ юзера
        switch (requestCode) {
            case PERMISSION_CAMERA_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    System.out.println("granted");
                    takePhoto();

                } else {
                    System.out.println("denied");
                }
        }


    }

    private File createNameFile() {
        File directory = new File(getActivity().getFilesDir(), "images");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String name = "photo_" + System.currentTimeMillis();
        System.out.println("имя " + name);

        File file = null;
        try {
            file = File.createTempFile(
                    name,  /* prefix */
                    ".jpg",         /* suffix */
                    directory      /* directory */
            );
        } catch (IOException e) {
            e.printStackTrace();
            dismiss();
        }
        return file;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        System.out.println("dialog onSaveInstanceState");
        if (uri != null) {
            outState.putString("uri", uri.toString());
        }
    }
}


