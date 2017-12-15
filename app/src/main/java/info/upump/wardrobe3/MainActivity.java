package info.upump.wardrobe3;

import android.app.DialogFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.net.URI;
import java.util.List;

import info.upump.wardrobe3.dialog.MainItemDialog;
import info.upump.wardrobe3.dialog.MainItemOperationAsync;
import info.upump.wardrobe3.dialog.SubItemOperationAsync;
import info.upump.wardrobe3.model.SubItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, FragmentController {
    public static final int DETAIL_SUB_ACTIVITY_ITEM_RESULT = 100;
    public static final int DETAIL_EDIT_SUB_ACTIVITY_ITEM_RESULT = 101;
    private FragmentTransaction fragmentTransaction;
    private Fragment fragment;
    private String fragmentTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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


        if (savedInstanceState == null) {

            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);

            fragment = new MainFragment();
            fragmentTag = MainFragment.TAG;

            fragmentTransaction.replace(R.id.mainContainer, fragment, fragmentTag);
            fragmentTransaction.commit();
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
        Fragment currentFragment = getCurrentFragment();
        if (currentFragment == null
                ) return;
        fragmentTag = currentFragment.getTag();
        System.out.println("mTag  " + fragmentTag);
        DialogFragment dialogFragment;
        Bundle bundle;
        switch (fragmentTag) {
            case MainFragment.TAG:
                dialogFragment = new MainItemDialog();
                bundle = new Bundle();
                bundle.putInt("operation", MainItemOperationAsync.SAVE);
                dialogFragment.setArguments(bundle);
                dialogFragment.show(getFragmentManager(), MainItemDialog.TAG);
                break;
         /*   case SubFragment.TAG:
                Intent intent = new Intent(this, SubItemDetail.class);
                long id = getIdItemCurrentFragment();
                intent.putExtra("id", id);
                startActivityForResult(intent, DETAIL_SUB_ACTIVITY_ITEM_RESULT);
                break;*/
            case SubFragment.TAG:
                  fragment = new SubItemDetailFragment();
                bundle = new Bundle();
                bundle.putLong("idSubItem", getCurrentFragment().getId());
                fragment.setArguments(bundle);
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                fragmentTransaction.replace(R.id.mainContainer, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;

        }

    }


    @Override
    public Fragment getCurrentFragment() {
      /*  List<Fragment> fragments = getSupportFragmentManager().getFragments();
        Fragment fragment = null;
        System.out.println("колво " + fragments.size());
        for (Fragment f : fragments) {
            if (f != null) {
*//*
                System.out.println("1 "+f.isVisible());
                System.out.println(" 2"+f.isResumed());
                System.out.println("3 "+f.isHidden());
                System.out.println(" 4"+f.isDetached());
                System.out.println("5 "+f.isAdded());
                System.out.println(" 6"+f.isStateSaved());
                System.out.println(" 7"+f.isRemoving());
                System.out.println(" 8"+f.getTag());*//*
                if (f.isVisible()) {
                    System.out.println("текущ " + f.getTag());
                    fragment = f;

                }
            }
        }*/

        return fragment;
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }

        switch (requestCode) {
            case DETAIL_SUB_ACTIVITY_ITEM_RESULT:
                if (resultCode == RESULT_OK) {
                    SubItem subItem = new SubItem();
                    subItem.setName(data.getStringExtra("name"));
                    subItem.setCost(data.getFloatExtra("cost", 0));
                    subItem.setDescription(data.getStringExtra("description"));
                    try {
                        subItem.setImg(data.getStringExtra("image"));
                    } catch (NullPointerException e) {

                    }
                    subItem.setIdMainItem(data.getLongExtra("idParent", 0));
                    ViewFragmentController viewFragmentController = (ViewFragmentController) getCurrentFragment();
                    viewFragmentController.addNewItem(subItem);
                }
                if (resultCode == RESULT_CANCELED) {
                    final Uri uri = Uri.parse(data.getStringExtra("image"));
                    //TODO не понятно нужно ли делать отдельный поток
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            getContentResolver().delete(uri, null, null);

                        }
                    });
                    thread.start();
                }
                break;
            case DETAIL_EDIT_SUB_ACTIVITY_ITEM_RESULT:
                if (resultCode == RESULT_OK) {
                    System.out.println("edit");
                    SubItem subItem = new SubItem();
                    subItem.setId(data.getLongExtra("id", 0));
                    subItem.setName(data.getStringExtra("name"));
                    subItem.setCost(data.getFloatExtra("cost", 0));
                    subItem.setDescription(data.getStringExtra("description"));

                    try {
                        subItem.setImg(data.getStringExtra("image"));
                    } catch (NullPointerException e) {

                    }

                    subItem.setIdMainItem(getIdItemCurrentFragment());
                    ViewFragmentController viewFragmentController = (ViewFragmentController) getCurrentFragment();
                    viewFragmentController.updateItem(subItem);
                }
                if (resultCode == RESULT_CANCELED) {


                }
                break;
        }

/*
        if (resultCode == RESULT_OK) {
            if (requestCode == DETAIL_SUB_ACTIVITY_ITEM_RESULT) {
                SubItem subItem = new SubItem();
                subItem.setName(data.getStringExtra("name"));
                subItem.setCost(data.getFloatExtra("cost", 0));
                subItem.setDescription(data.getStringExtra("description"));
                //       System.out.println(data.getStringExtra("img"));
                try {
                    subItem.setImg(data.getStringExtra("image"));
                } catch (NullPointerException e) {

                }
                // subItem.setIdMainItem(getIdItemCurrentFragment());
                subItem.setIdMainItem(data.getLongExtra("idParent", 0));
                //    System.out.println("ryjgrf "+data.getLongExtra("idParent", 0));
                //      System.out.println("new");
                ViewFragmentController viewFragmentController = (ViewFragmentController) getCurrentFragment();
                viewFragmentController.addNewItem(subItem);

            }
            if (requestCode == DETAIL_EDIT_SUB_ACTIVITY_ITEM_RESULT) {
                System.out.println("edit");
                SubItem subItem = new SubItem();
                subItem.setId(data.getLongExtra("id", 0));
                subItem.setName(data.getStringExtra("name"));
                subItem.setCost(data.getFloatExtra("cost", 0));
                subItem.setDescription(data.getStringExtra("description"));

                try {
                    subItem.setImg(data.getStringExtra("image"));
                } catch (NullPointerException e) {

                }

                subItem.setIdMainItem(getIdItemCurrentFragment());
                ViewFragmentController viewFragmentController = (ViewFragmentController) getCurrentFragment();
                viewFragmentController.updateItem(subItem);
            }

        }*/
    }

}
