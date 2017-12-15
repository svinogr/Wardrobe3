package info.upump.wardrobe3.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import info.upump.wardrobe3.FragmentController;
import info.upump.wardrobe3.R;
import info.upump.wardrobe3.ViewFragmentController;
import info.upump.wardrobe3.model.EnumMask;
import info.upump.wardrobe3.model.MainMenuItem;

/**
 * Created by explo on 29.10.2017.
 */

public class MainItemDialog extends DialogFragment {
    public EditText textNameItem;
    public static final String TAG = "dialogMain";
    private AlertDialog.Builder builder;
    private Spinner spiner;

    private ViewFragmentController viewFragmentController;



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getActivity() instanceof FragmentController) {
            viewFragmentController = (ViewFragmentController) ((FragmentController) getActivity()).getCurrentFragment();
        }
        System.out.println("onCreateDialog");

        int operation = getArguments().getInt("operation");

        builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();

        View inflate = layoutInflater.inflate(R.layout.dailog_fragment_main_add_item, null);
        textNameItem = inflate.findViewById(R.id.editTextForMainItemDialog);

        spiner = inflate.findViewById(R.id.spinnerForMainItemDialog);
        EnumMask[] values = EnumMask.values();
        String[] spinnerData = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            spinnerData[i] = values[i].toString();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, spinnerData);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiner.setAdapter(adapter);


        builder.setView(inflate);

        switch (operation) {
            case MainItemOperationAsync.SAVE:
                builder = createSaveDialog();
                break;
            case MainItemOperationAsync.UPDATE:
                builder = createUpdateDialog();
                break;
        }

        return builder.create();
    }

    private AlertDialog.Builder createUpdateDialog() {
        System.out.println("createUpdateDialog");
        textNameItem.setText(getArguments().getString("name"));
        builder.setTitle(R.string.title_dialog_update_main_item).
                setPositiveButton(R.string.positiv_btn_dialog_new_main_item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (textNameItem.getText().toString().equals("")) {
                            return;
                        }
                        int selectedSpinnerPosition = spiner.getSelectedItemPosition();
                        MainMenuItem mainMenuItem = new MainMenuItem();
                        mainMenuItem.setName(textNameItem.getText().toString());
                        mainMenuItem.setId(getArguments().getLong("id"));
                        mainMenuItem.setEnumMask(EnumMask.values()[selectedSpinnerPosition]);
                          if(viewFragmentController!=null) {
                              viewFragmentController.updateItem(mainMenuItem);
                          }
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
        System.out.println("createUpdateDialog");

        builder.setTitle(R.string.title_dialog_new_main_item);

        builder.setPositiveButton(R.string.positiv_btn_dialog_new_main_item, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (textNameItem.getText().toString().equals("")) {

                    return;
                }
                String nameOfNewItem = textNameItem.getText().toString();
                int selectedSpinnerPosition = spiner.getSelectedItemPosition();


                MainMenuItem mainMenuItem = new MainMenuItem();
                mainMenuItem.setName(nameOfNewItem);
                // test enum
                //  mainMenuItem.setEnumMask(EnumMask.TSHORT);
                mainMenuItem.setEnumMask(EnumMask.values()[selectedSpinnerPosition]);

                if(viewFragmentController!=null) {
                    viewFragmentController.addNewItem(mainMenuItem);
                }
            }
        });
        builder.setNegativeButton(R.string.negative_btn_dialog_new_main_item, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("No", "No");
            }
        });

        return builder;

    }



}
