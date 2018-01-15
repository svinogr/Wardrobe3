package info.upump.wardrobe3.model;

import java.util.Date;
import java.util.List;

/**
 * Created by explo on 15.01.2018.
 */

public interface IItem {
    long getId();

    String getName();

    String getImgUriToString();

    EnumMask getEnumMask();

    float getCost();


    Date getDate();


    long getIdMainItem();


    String getDescription();


}
