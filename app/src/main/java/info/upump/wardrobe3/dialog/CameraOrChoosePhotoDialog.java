package info.upump.wardrobe3.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.io.File;

import info.upump.wardrobe3.R;
import info.upump.wardrobe3.SubItemDetail;

import static android.support.v4.content.FileProvider.getUriForFile;

/**
 * Created by explo on 05.11.2017.
 */

public class CameraOrChoosePhotoDialog extends DialogFragment implements View.OnClickListener {
    private AlertDialog.Builder builder;
    private TextView camera, choosePhoto;
    private File file;
    private String nameFile;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        System.out.println("CameraOrChoosePhotoDialog");
        builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();

        View view = layoutInflater.inflate(R.layout.dialog_fragment_camera_or_choose_photo, null);

        camera = view.findViewById(R.id.dialog_camera);
        camera.setOnClickListener(this);
        choosePhoto = view.findViewById(R.id.dialog_choose_photo);
        choosePhoto.setOnClickListener(this);

        builder.setView(view).
                setTitle(R.string.dialog_camera_or_chosse_title).
                setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });


        return builder.create();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_camera:
                System.out.println("выбор картинки");
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Uri uri = generateUriPhoto();

                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                getActivity().startActivityForResult(cameraIntent, SubItemDetail.CAMERA_RESULT);
                break;
            case R.id.dialog_choose_photo:
                break;
        }
    }

    private Uri generateUriPhoto() {
        File directory = new File(getActivity().getFilesDir(), "images");

        if (!directory.exists()) {
            directory.mkdirs();
        }

        System.out.println(directory);
        String name = "/photo_" + System.currentTimeMillis() + ".jpg";
        System.out.println(name);

        file = new File(directory.getPath(), name);
        Uri uri = getUriForFile(getActivity(), "info.upump.wardrobe3.fileprovider", file);
        getActivity().grantUriPermission("info.upump.wardrobe3", uri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        return uri;

    }

    public File getFile() {
        return file;
    }
}

