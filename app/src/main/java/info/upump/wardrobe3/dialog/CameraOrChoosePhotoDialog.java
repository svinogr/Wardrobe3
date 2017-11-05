package info.upump.wardrobe3.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import info.upump.wardrobe3.FragmentController;
import info.upump.wardrobe3.R;
import info.upump.wardrobe3.SubItemDetail;
import info.upump.wardrobe3.ViewFragmentController;
import info.upump.wardrobe3.model.MainMenuItem;

/**
 * Created by explo on 05.11.2017.
 */

public class CameraOrChoosePhotoDialog extends DialogFragment implements View.OnClickListener {
    AlertDialog.Builder builder;
    TextView camera, choosePhoto;

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
                getActivity().startActivityForResult(cameraIntent, SubItemDetail.CAMERA_RESULT);
                break;
            case R.id.dialog_choose_photo:
                break;
        }
    }
}

