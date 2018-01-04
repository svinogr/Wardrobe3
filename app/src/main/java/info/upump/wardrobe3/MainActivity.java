package info.upump.wardrobe3;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.security.acl.Permission;

import info.upump.wardrobe3.dialog.Constants;
import info.upump.wardrobe3.dialog.MainItemDialog;
import info.upump.wardrobe3.dialog.OperationAsync;
import info.upump.wardrobe3.dialog.PermissionDialogFragment;
import info.upump.wardrobe3.dialog.SubItemDialog;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, FragmentController {
    public static final int DETAIL_SUB_ACTIVITY_ITEM_RESULT = 100;
    public static final int DETAIL_EDIT_SUB_ACTIVITY_ITEM_RESULT = 101;
    private final static String FRAGMENT_TAG = "fragmentTag";
    private final static String VISIBLE_FRAGMENT = "visibleFragment";
    private static final int PERMISSION_PIC_CODE = 1;
    private static final int REQUEST_PERMISSION_GALLERY = 10;
    private FragmentTransaction fragmentTransaction;
    public ViewFragmentController viewFragmentController;
    private Fragment fragment;
    private String fragmentTag;
    private Bundle savedInstanceState;
    private FloatingActionButton fab;
    private boolean flagPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        setContentView(R.layout.activity_main);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        checkFab(false);

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Fragment visibleFragment = getSupportFragmentManager().findFragmentByTag(VISIBLE_FRAGMENT);
                System.out.println("onBackStackChanged");
                if (visibleFragment instanceof MainFragment) {
                    fragmentTag = MainFragment.TAG;
                }
                if (visibleFragment instanceof SubFragment) {
                    fragmentTag = SubFragment.TAG;
                }
                fragment = visibleFragment;
                System.out.println("fragmentTag " + fragmentTag);
                System.out.println("fragment " + fragment);
            }
        });

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            System.out.println("запрос PERMISSION на версии SDK " + android.os.Build.VERSION.SDK_INT);

            getPermission();

        } else initFirstFragment(savedInstanceState);


    }

    private void getReRat() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            System.out.println(2);
            Snackbar.make(findViewById(R.id.drawer_layout), "1 Для продолжения необходимо предоставить доступ к галереи",
                    Snackbar.LENGTH_LONG).show();

        } else {
            Snackbar.make(findViewById(R.id.drawer_layout), "3 Вы запретили доступ к галереи",
                    Snackbar.LENGTH_INDEFINITE).setAction("Настройки", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // getPermission();

                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivityForResult(intent, REQUEST_PERMISSION_GALLERY);

                }
            }).show();
        }
    }

    @Override
    public void getPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            System.out.println(1);
            Snackbar.make(findViewById(R.id.drawer_layout), "1 Для продолжения необходимо предоставить доступ к галереи",
                    Snackbar.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSION_PIC_CODE);
        } else {
            System.out.println(3);
            initFirstFragment(savedInstanceState);
        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case PermissionDialogFragment.PERMISSION_PIC_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    System.out.println(4);
                    // Permission Granted
                    initFirstFragment(savedInstanceState);
                } else {
                      // getPermission();
                    System.out.println(5);
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        System.out.println(true);
                        getPermission();
                    } else {
                        System.out.println(false);

                    Snackbar.make(findViewById(R.id.drawer_layout), "2 Вы запретили доступ к галереи",
                            Snackbar.LENGTH_INDEFINITE).setAction("Настройки", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivityForResult(intent, REQUEST_PERMISSION_GALLERY);
                        }
                    }).show();
                    }

                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void initFirstFragment(Bundle savedInstanceState) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        checkFab(true);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {

            fragment = new MainFragment();
            //  fragmentTag = MainFragment.TAG;
            setCurrentFragment(fragment);
        } else {
            //    fragmentTag = savedInstanceState.getString(FRAGMENT_TAG);
            int id = savedInstanceState.getInt(FRAGMENT_TAG);
            // fragmentTag = savedInstanceState.getString(FRAGMENT_TAG);

            fragment = getSupportFragmentManager().findFragmentById(id);
            //  fragmentTag = ((ViewFragmentController) fragment).getFragmentTag();
            fragmentTag = ((ViewTag) fragment).getFragmentTag();
            if (fragment instanceof ViewFragmentController) {
                ((ViewFragmentController) fragment).restartLoader();
                setCurrentFragment(fragment);
            } else checkFab(false);
            System.out.println("bundle state " + fragmentTag);
            System.out.println("bundle state frag " + fragment);
            //  viewFragmentController = (ViewFragmentController) fragment;

        }
    }

    private void checkFab(boolean visible) {
        if (visible) {
            fab.setVisibility(View.VISIBLE);
        } else fab.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_wardrobe) {
            //init();
            MainFragment mainFragment = new MainFragment();
            setCurrentFragment(mainFragment);
            checkFab(true);
            // Handle the camera action
        } else if (id == R.id.nav_dressing) {
            DressingFragment dressingFragment = new DressingFragment();
            setCurrentFragment(dressingFragment);
            checkFab(false);


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        Bundle bundle;
        DialogFragment dialogFragment;

        switch (fragmentTag) {
            case MainFragment.TAG:

                dialogFragment = new MainItemDialog();
                bundle = new Bundle();
                bundle.putInt(OperationAsync.OPERATION, OperationAsync.SAVE);
                dialogFragment.setArguments(bundle);
                dialogFragment.show(getSupportFragmentManager(), MainItemDialog.TAG);
                break;
            case SubFragment.TAG:
                dialogFragment = new SubItemDialog();
                bundle = new Bundle();
                bundle.putInt(OperationAsync.OPERATION, OperationAsync.SAVE);
                bundle.putLong(Constants.ID_PARENT, getIdItemCurrentFragment());
                dialogFragment.setArguments(bundle);
                dialogFragment.show(getSupportFragmentManager(), SubItemDialog.TAG);
                break;

        }

    }


    @Override
    public Fragment getCurrentFragment() {

        return fragment;
    }

    @Override
    public void setCurrentFragment(Fragment fragment) {
        // this.fragment = fragment;
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        fragmentTransaction.replace(R.id.mainContainer, fragment, VISIBLE_FRAGMENT);
     /*   if(fragment instanceof ViewFragmentController){
            viewFragmentController = (ViewFragmentController) fragment;
        }*/
        if (fragment instanceof MainFragment) {
            fragmentTag = MainFragment.TAG;
            this.fragment = fragment;
            System.out.println("fragmentTag setCurrentFragment  " + fragmentTag);
        } else {
            fragmentTransaction.addToBackStack(null);
        }


        //fragmentTransaction.commit();
        fragmentTransaction.commitAllowingStateLoss();// чтобы не ьыло ошибки при сохранении savedInstance

    }

    @Override
    public long getIdItemCurrentFragment() {
        ViewFragmentController fragment = (ViewFragmentController) getCurrentFragment();
        if (fragment != null) {
            System.out.println("id parent " + fragment.getIdDb());
            return fragment.getIdDb();
        }
        return -1;
    }

    @Override
    public ViewFragmentController getViewFragmentController() {
        return viewFragmentController;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //     super.onSaveInstanceState(outState);
        System.out.println("onSaveInstanceState");
        System.out.println(fragment);
        // outState.putString(FRAGMENT_TAG, fragmentTag);
        if (fragment != null) {
            outState.putInt(FRAGMENT_TAG, fragment.getId());
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("main activity result");

        SubItemDialog subItemDialog;
        switch (requestCode) {
            case SubFragment.CAMERA_RESULT:
                subItemDialog = (SubItemDialog) getSupportFragmentManager().findFragmentByTag(SubItemDialog.TAG);
                System.out.println(subItemDialog);
                if (subItemDialog != null) {
                    subItemDialog.onActivityResult(requestCode, resultCode, data);
                }
                break;
            case SubFragment.CHOOSE_PHOTO_RESULT:
                subItemDialog = (SubItemDialog) getSupportFragmentManager().findFragmentByTag(SubItemDialog.TAG);
                System.out.println("CHOOSE_PHOTO_RESULT main ac" + subItemDialog);
                if (subItemDialog != null) {
                    subItemDialog.onActivityResult(requestCode, resultCode, data);
                }
                break;
            case REQUEST_PERMISSION_GALLERY:
                getPermission();

                break;
        }

    }

}
