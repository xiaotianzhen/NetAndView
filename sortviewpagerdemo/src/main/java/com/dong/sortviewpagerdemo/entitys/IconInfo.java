package com.dong.sortviewpagerdemo.entitys;

/**
 * Created by 川东 on 2016/12/2.
 */

public class IconInfo {
    String iconName;
    int iconId;

    public IconInfo(String iconName, int iconId) {
        this.iconName = iconName;
        this.iconId = iconId;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }
}
