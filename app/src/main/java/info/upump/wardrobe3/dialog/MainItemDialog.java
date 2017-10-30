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

import java.util.List;
import java.util.concurrent.ExecutionException;

import info.upump.wardrobe3.MainActivity;
import info.upump.wardrobe3.MainFragment;
import info.upump.wardrobe3.R;
import info.upump.wardrobe3.adapter.MainAdapter;
import info.upump.wardrobe3.model.MainMenuItem;

/**
 * Created by explo on 29.10.2017.
 */

public class MainItemDialog extends DialogFragment {
    public EditText textNameItem;
    public static final String TAG = "dialogMain";
    private MainFragment fromFragment;
    private AlertDialog.Builder builder;
    private List<MainMenuItem> mainMenuItemList;
    private MainAdapter mainAdapter;


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
        System.out.println("createUpdateDialog");

        textNameItem.setText(getArguments().getString("name"));
        builder.setMessage("Введите название ящика").
                setPositiveButton("Сохранить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("YES", "YES");
                        Log.d(textNameItem.getText().toString(), "deded");
                        initContainerForNotify();
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
                            System.out.println("ссылка равна на фраг " + fromFragment == null);

                            if (mainMenuItemList != null) {
                                for (MainMenuItem m : mainMenuItemList) {
                                    if (m.getId() == mainMenuItem.getId()) {
                                        m.setName(mainMenuItem.getName());
                                    }
                                }
                                mainAdapter.notifyDataSetChanged();
                                System.out.println("обновили адаптер");
                            }
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
        System.out.println("createUpdateDialog");
        builder.setMessage("Введите название ящика").
                setPositiveButton("Сохранить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("YES", "YES");
                        Log.d(textNameItem.getText().toString(), "deded");
                        initContainerForNotify();

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
                            if (mainMenuItemList != null) {
                                mainMenuItemList.add(mainMenuItem);
                                mainAdapter.notifyDataSetChanged();
                                System.out.println("обновили адаптер");
                            }

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

    private void initContainerForNotify() {
        MainActivity activity = (MainActivity) getActivity();
        fromFragment = (MainFragment) activity.getSupportFragmentManager().findFragmentByTag(MainFragment.TAG);
        System.out.println(fromFragment == null);
        mainMenuItemList = fromFragment.getMainMenuItemList();
        mainAdapter = fromFragment.getMainAdapter();

    }


}
