package info.upump.wardrobe3.dialog;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import info.upump.wardrobe3.PermissionController;
import info.upump.wardrobe3.R;

/**
 * Created by explo on 04.01.2018.
 */

public class PermissionDialogFragment extends DialogFragment implements View.OnClickListener {
    public static final String TAG  = "permissionDialogFragment";
    private AlertDialog.Builder builder;
    public static final int PHASE_1 = 1;
    public static final int PHASE_2 = 2;
    public static final int PHASE_3 = 3;
    public static final int PERMISSION_PIC_CODE =1;
    private static final int REQUEST_PERMISSION_GALLERY = 10;
    public static final String OPERATION = "operation";
    private int operation;
    private TextView message;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("PermissionDialogFragment");
      /*  if (savedInstanceState != null) {
            uri = Uri.parse(savedInstanceState.getString("uri"));
            System.out.println(savedInstanceState.getString("uri"));
            //   image.setImageURI(uri);
            System.out.println("2" + uri.toString());
        }*/
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        System.out.println(22222);
        setCancelable(false);

        builder = new AlertDialog.Builder(getActivity());

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();

        View inflate = layoutInflater.inflate(R.layout.dialog_fragment_permisson, null);
        message = inflate.findViewById(R.id.dialog_permission_message);
        message.setText(getArguments().getString("message"));
        operation = getArguments().getInt(OPERATION);
        builder.setView(inflate);

        switch (operation){
            case PHASE_1:
               builder = createBuilderPhaseOne();
                break;
            case PHASE_2:
                builder = createBuilderPhaseTwo();
                break;
            case PHASE_3:
                builder = createBuilderPhaseThree();
                break;

        }

        return builder.create();

    }

    private AlertDialog.Builder createBuilderPhaseOne(){

        builder.setNegativeButton("Выйти", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finish();

                    }
                }).setPositiveButton("Ок", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSION_PIC_CODE);

            }
        });


        return builder;
    }
    private AlertDialog.Builder createBuilderPhaseTwo(){

        builder.setNegativeButton("Выйти", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();

            }
        }).setPositiveButton("Ок", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PermissionController  activity = (PermissionController) getActivity();
                activity.getPermission();

            }
        });
        return builder;
    }
    private AlertDialog.Builder createBuilderPhaseThree(){

        builder.setNegativeButton("Выйти", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();

            }
        }).setPositiveButton("Ок", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, REQUEST_PERMISSION_GALLERY);

            }
        });


        return builder;
    }

    @Override
    public void onClick(View v) {

    }
}
