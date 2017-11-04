package info.upump.wardrobe3.model;

import java.util.Date;

/**
 * Created by explo on 30.10.2017.
 */

public class SubItem extends MainMenuItem {
    private float cost;
    private Date date;
    private long idMainItem;
    private  String description;

    public SubItem() {
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getIdMainItem() {
        return idMainItem;
    }

    public void setIdMainItem(long idMainItem) {
        this.idMainItem = idMainItem;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        SubItem subItem = (SubItem) o;

        if (Float.compare(subItem.cost, cost) != 0) return false;
        if (idMainItem != subItem.idMainItem) return false;
        return date != null ? date.equals(subItem.date) : subItem.date == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (cost != +0.0f ? Float.floatToIntBits(cost) : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (int) (idMainItem ^ (idMainItem >>> 32));
        return result;
    }

}
