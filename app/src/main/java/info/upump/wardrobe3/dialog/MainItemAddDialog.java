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

import java.util.concurrent.ExecutionException;

import info.upump.wardrobe3.Datable;
import info.upump.wardrobe3.R;
import info.upump.wardrobe3.model.MainMenuItem;

/**
 * Created by explo on 29.10.2017.
 */

public class MainItemAddDialog extends DialogFragment {
    public EditText textNameItem;
    public static final String TAG = "dialogMain";
    private int operation;
    private AlertDialog.Builder builder;
    private Bundle bundle;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle =savedInstanceState;
        operation = getArguments().getInt("operation");

        builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();

        View inflate = layoutInflater.inflate(R.layout.dailog_main_add_item, null);
        textNameItem = inflate.findViewById(R.id.editTextForMainItemDialog);
        builder.setView(inflate);

        switch (operation) {
            case MainItemOperationAsynck.SAVE:
                builder = createSaveDialog();
                break;
            case MainItemOperationAsynck.UPDATE:
                builder = createUpdateDialog();
                break;
        }

        return builder.create();
    }

    private AlertDialog.Builder createUpdateDialog() {
        textNameItem.setText(getArguments().getString("name"));
        builder.setMessage("Введите название ящика").
                setPositiveButton("Сохранить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("YES", "YES");
                        Log.d(textNameItem.getText().toString(), "deded");

                        if (textNameItem.getText().toString().equals("")) {
                            return;
                        }

                        Boolean resultAdding = false;

                        MainMenuItem mainMenuItem = new MainMenuItem();
                        mainMenuItem.setName(textNameItem.getText().toString());
                        mainMenuItem.setId(getArguments().getLong("id"));

                        MainItemOperationAsynck addItemAsynck = new MainItemOperationAsynck(getActivity(), MainItemOperationAsynck.UPDATE);
                        addItemAsynck.execute(mainMenuItem);
                        try {
                            resultAdding = addItemAsynck.get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                            resultAdding = false;
                        }
                        if (resultAdding == true) {

                            Datable activity = (Datable) getActivity();
                            //     System.out.println(activity.);
                            activity.notifyCnangeAdapterItems();

                        }
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("No", "No");
                    }
                });

        return builder;
    }

    private AlertDialog.Builder createSaveDialog() {
        builder.setMessage("Введите название ящика").
                setPositiveButton("Сохранить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("YES", "YES");
                        Log.d(textNameItem.getText().toString(), "deded");

                        if (textNameItem.getText().toString().equals("")) {
                            return;
                        }

                        Boolean resultAdding = false;

                        MainMenuItem mainMenuItem = new MainMenuItem();
                        mainMenuItem.setName(textNameItem.getText().toString());

                        MainItemOperationAsynck addItemAsynck = new MainItemOperationAsynck(getActivity(), MainItemOperationAsynck.SAVE);
                        addItemAsynck.execute(mainMenuItem);
                        try {
                            resultAdding = addItemAsynck.get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                            resultAdding = false;
                        }
                        if (resultAdding == true) {

                            Datable activity = (Datable) getActivity();
                            //     System.out.println(activity.);
                            activity.notifyCnangeAdapterItems();

                        }
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("No", "No");
                    }
                });

        return builder;

    }


}
