package info.upump.wardrobe3.model;

import java.util.Date;
import java.util.List;

/**
 * Created by explo on 26.10.2017.
 */

public class MainMenuItem implements IItem {
    protected long id;
    protected String name;
    protected String imgUriToString;
    protected EnumMask enumMask;
    private List<SubItem> subItems;

    public MainMenuItem() {
    }
    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String getImgUriToString() {
        return imgUriToString;
    }

    public void setImgUriToString(String imgUriToString) {
        this.imgUriToString = imgUriToString;
    }

    public List<SubItem> getSubItems() {
        return subItems;
    }

    public void setSubItems(List<SubItem> subItems) {
        this.subItems = subItems;
    }

    @Override
    public EnumMask getEnumMask() {
        return enumMask;
    }

    @Override
    public float getCost() {
        return 0;
    }

    @Override
    public Date getDate() {
        return null;
    }

    @Override
    public long getIdMainItem() {
        return 0;
    }

    @Override
    public String getDescription() {
        return null;
    }

    public void setEnumMask(EnumMask enumMask) {
        this.enumMask = enumMask;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MainMenuItem that = (MainMenuItem) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    /* @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MainMenuItem that = (MainMenuItem) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return imgUriToString != null ? imgUriToString.equals(that.imgUriToString) : that.imgUriToString == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (imgUriToString != null ? imgUriToString.hashCode() : 0);
        return result;
    }*/
}
