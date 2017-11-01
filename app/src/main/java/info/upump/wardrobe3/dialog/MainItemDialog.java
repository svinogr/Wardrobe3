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
import android.widget.EditText;

import info.upump.wardrobe3.FragmentController;
import info.upump.wardrobe3.R;
import info.upump.wardrobe3.ViewFragmentController;
import info.upump.wardrobe3.model.MainMenuItem;

/**
 * Created by explo on 29.10.2017.
 */

public class MainItemDialog extends DialogFragment {
    public EditText textNameItem;
    public static final String TAG = "dialogMain";
    private AlertDialog.Builder builder;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        System.out.println("onAttach");

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        System.out.println("onCreateDialog");

        int operation = getArguments().getInt("operation");

        builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();

        View inflate = layoutInflater.inflate(R.layout.dailog_main_add_item, null);
        textNameItem = inflate.findViewById(R.id.editTextForMainItemDialog);
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
                        MainMenuItem mainMenuItem = new MainMenuItem();
                        mainMenuItem.setName(textNameItem.getText().toString());
                        mainMenuItem.setId(getArguments().getLong("id"));

                        ViewFragmentController viewFragmentController = getViewFragmentController();
                        viewFragmentController.updateItem(mainMenuItem);

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

                MainMenuItem mainMenuItem = new MainMenuItem();
                mainMenuItem.setName(nameOfNewItem);

                ViewFragmentController viewFragmentController = getViewFragmentController();
                viewFragmentController.addNewItem(mainMenuItem);
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

    private ViewFragmentController getViewFragmentController() {
        FragmentController fragmentController = (FragmentController) getActivity();
        return (ViewFragmentController) fragmentController.getCurrentFragment();
    }

}
