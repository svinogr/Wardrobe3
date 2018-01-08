/*
package info.upump.wardrobe3.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.List;

import info.upump.wardrobe3.R;
import info.upump.wardrobe3.SubFragment;

import static android.support.v4.content.FileProvider.getUriForFile;


public class CameraOrChoosePhotoDialog extends DialogFragment implements View.OnClickListener {
    public final static String TAG = "CameraOrChoosePhotoDialog";
    private AlertDialog.Builder builder;
    private TextView camera, choosePhoto;
    private File file;
    private Uri uri;
    private File directory;
    private static final int PERMISION_CAMERA_CODE = 0;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        System.out.println("CameraOrChoosePhotoDialog");
        setRetainInstance(true);
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
                checkVersion();
                break;
            case R.id.dialog_choose_photo:
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image*/
/*");
                startActivityForResult(photoPickerIntent, SubFragment.CHOOSE_PHOTO_RESULT);
                break;
        }
    }

    private void checkVersion() {

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.CAMERA}, PERMISION_CAMERA_CODE);
            } else takePhoto();

        } else takePhoto();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//проверяем ответ юзера
        switch (requestCode) {
            case PERMISION_CAMERA_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    System.out.println("granted");
                    takePhoto();

                } else {
                    System.out.println("denied");
                    dismiss();
                }
        }


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

    public File getFile() {
        return file;
    }

    public Uri getUri() {
        return uri;
    }

    private File createNameFile() {
        directory = new File(getActivity().getFilesDir(), "images");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        String name = "photo_" + System.currentTimeMillis();
        System.out.println("имя " + name);
        File file = null;
        try {
            file = File.createTempFile(
                    name,  */
/* prefix *//*

                    ".jpg",         */
/* suffix *//*

                    directory      */
/* directory *//*

            );
        } catch (IOException e) {
            e.printStackTrace();
            dismiss();
        }
        return file;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        System.out.println(resultCode);
        System.out.println("result activity camera " + requestCode + " " + resultCode);
    }
}

*/
