package info.upump.wardrobe3.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import info.upump.wardrobe3.FragmentController;
import info.upump.wardrobe3.MainFragment;
import info.upump.wardrobe3.R;
import info.upump.wardrobe3.ViewFragmentControllerCallback;
import info.upump.wardrobe3.model.EnumMask;
import info.upump.wardrobe3.model.MainMenuItem;

/**
 * Created by explo on 29.10.2017.
 */

public class MainItemDialog extends DialogFragment {
    public EditText textNameItem;
    public static final String TAG = "dialogMain";
    private AlertDialog.Builder builder;
    private Spinner spinner;

    private ViewFragmentControllerCallback viewFragmentControllerCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        System.out.println("onAttach");


    }

    @Override
    public void onDetach() {
        super.onDetach();
        System.out.println("onDetach");
    }

    public static DialogFragment getInstanceMainDialog(MainMenuItem mainMenuItem, int operation) {
        MainItemDialog mainItemDialog = new MainItemDialog();
        Bundle bundle = new Bundle();
        bundle.putInt(OperationAsync.OPERATION, operation);
        if (mainMenuItem != null) {
            bundle.putLong(Constants.ID, mainMenuItem.getId());
            bundle.putString(Constants.NAME, mainMenuItem.getName());
           // bundle.putString(Constants.IMG, mainMenuItem.getImgUriToString()); TODO включить в случае реализации индивидуальной картинки
        }
        mainItemDialog.setArguments(bundle);
        return mainItemDialog;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //  setRetainInstance(true);
        //viewFragmentControllerCallback = getViewFragmentControllerCallback();
        viewFragmentControllerCallback = (ViewFragmentControllerCallback) getTargetFragment();

        System.out.println(viewFragmentControllerCallback);

        setCancelable(false);// запрещаем закрытие диалога кликом в путсоту

        int operation = getArguments().getInt(OperationAsync.OPERATION);

        builder = new AlertDialog.Builder(getActivity());

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();

        View inflate = layoutInflater.inflate(R.layout.dailog_fragment_main_add_item, null);
        textNameItem = inflate.findViewById(R.id.editTextForMainItemDialog);
        spinner = inflate.findViewById(R.id.spinnerForMainItemDialog);

        EnumMask[] values = EnumMask.values();
        String[] spinnerData = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            spinnerData[i] = values[i].toString();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, spinnerData);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


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
                        int selectedSpinnerPosition = spinner.getSelectedItemPosition();
                        MainMenuItem mainMenuItem = new MainMenuItem();
                        mainMenuItem.setName(textNameItem.getText().toString());
                        mainMenuItem.setId(getArguments().getLong("id"));
                        mainMenuItem.setEnumMask(EnumMask.values()[selectedSpinnerPosition]);
                       /*
                        было
                        if (viewFragmentControllerCallback == null) {
                            viewFragmentControllerCallback = (ViewFragmentControllerCallback) ((FragmentController) getActivity()).getCurrentFragment();
                        }
                        System.out.println(viewFragmentControllerCallback);
                        viewFragmentControllerCallback.updateItem(mainMenuItem);*/
                       //стало
                        viewFragmentControllerCallback.updateItem(mainMenuItem);
                    }
                })
                .setNegativeButton(R.string.negative_btn_dialog_new_main_item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("No", "No");
                        System.out.println(((FragmentController) getActivity()).getCurrentFragment());
                        if (viewFragmentControllerCallback == null) {
                            viewFragmentControllerCallback = (ViewFragmentControllerCallback) ((FragmentController) getActivity()).getCurrentFragment();
                        }
                        System.out.println("отмена " + viewFragmentControllerCallback);
                        viewFragmentControllerCallback.cancelUpdate();
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
                int selectedSpinnerPosition = spinner.getSelectedItemPosition();

                MainMenuItem mainMenuItem = new MainMenuItem();
                mainMenuItem.setName(nameOfNewItem);
                System.out.println("EnumMask.values( " + EnumMask.values()[selectedSpinnerPosition].ordinal());
                mainMenuItem.setEnumMask(EnumMask.values()[selectedSpinnerPosition]);

                if (viewFragmentControllerCallback != null) {
                    viewFragmentControllerCallback.addNewItem(mainMenuItem);
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

    ViewFragmentControllerCallback getViewFragmentControllerCallback() {
        return (ViewFragmentControllerCallback) ((FragmentController) getActivity()).getCurrentFragment();
    }


}
