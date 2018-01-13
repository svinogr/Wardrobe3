package info.upump.wardrobe3;

import android.support.v4.app.Fragment;

/**
 * Created by explo on 01.11.2017.
 */

public interface FragmentController extends PermissionController {
    Fragment getCurrentFragment();

    void setCurrentFragment(Fragment fragment);

    long getIdItemCurrentFragment();
    ViewFragmentControllerCallback getViewFragmentControllerCallback();
}
