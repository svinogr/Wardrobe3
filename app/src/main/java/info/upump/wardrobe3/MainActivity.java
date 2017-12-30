package info.upump.wardrobe3;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.Toast;

import info.upump.wardrobe3.dialog.Constants;
import info.upump.wardrobe3.dialog.MainItemDialog;
import info.upump.wardrobe3.dialog.OperationAsync;
import info.upump.wardrobe3.dialog.SubItemDialog;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, FragmentController {
    public static final int DETAIL_SUB_ACTIVITY_ITEM_RESULT = 100;
    public static final int DETAIL_EDIT_SUB_ACTIVITY_ITEM_RESULT = 101;
    private final static String FRAGMENT_TAG = "fragmentTag";
    private final static String VISIBLE_FRAGMENT = "visibleFragment";
    private static final int PERMISSION_PIC_CODE = 1;
    private FragmentTransaction fragmentTransaction;
    public ViewFragmentController viewFragmentController;
    private Fragment fragment;
    private String fragmentTag;
    private Bundle savedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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

        getPermissionForPicImage();


        //    initFirstFragment(savedInstanceState);

    }

    private void getPermissionForPicImage() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            System.out.println("запрос пермишн");

            if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ){
                System.out.println("контенкст пермиш "+ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE));
                if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                    Toast.makeText(MainActivity.this, "Дя продолжения необходим доступ к фото", Toast.LENGTH_SHORT)
                            .show();
                    //initFirstFragment(savedInstanceState);

                    this.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_PIC_CODE);

                }else {
                    //TODO если стоит донт аск
                    initFirstFragment(savedInstanceState);
                }
            }else initFirstFragment(savedInstanceState);


        } else initFirstFragment(savedInstanceState);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_PIC_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    initFirstFragment(savedInstanceState);
                } else {
                    // Permission Denied
                  /*  Toast.makeText(MainActivity.this, "Дя продолжения необходим доступ к фото", Toast.LENGTH_SHORT)
                            .show();*/
                    getPermissionForPicImage();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void initFirstFragment(Bundle savedInstanceState) {
        System.out.println("razr1 " + (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED));

        System.out.println("razr2 " + (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED));
        if (savedInstanceState == null) {

            fragment = new MainFragment();
            //  fragmentTag = MainFragment.TAG;
            setCurrentFragment(fragment);
        } else {
            //    fragmentTag = savedInstanceState.getString(FRAGMENT_TAG);
            int id = savedInstanceState.getInt(FRAGMENT_TAG);
            // fragmentTag = savedInstanceState.getString(FRAGMENT_TAG);

            fragment = getSupportFragmentManager().findFragmentById(id);
            fragmentTag = ((ViewFragmentController) fragment).getFragmentTag();
            ((ViewFragmentController) fragment).restartLoader();
            System.out.println("bundle state " + fragmentTag);
            System.out.println("bundle state frag " + fragment);
            //  viewFragmentController = (ViewFragmentController) fragment;

        }
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

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
         /*   case SubFragment.TAG:
                Intent intent = new Intent(this, SubItemDetail.class);
                long id = getIdItemCurrentFragment();
                intent.putExtra("id", id);
                startActivityForResult(intent, DETAIL_SUB_ACTIVITY_ITEM_RESULT);
                break;*/
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
        // outState.putString(FRAGMENT_TAG, fragmentTag);
        if (fragment != null) {
            outState.putInt(FRAGMENT_TAG, fragment.getId());
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("main activity result");
      /*  for(Fragment f: getSupportFragmentManager().getFragments()){
            System.out.println(f);
        }*/
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
        }


    }
}
