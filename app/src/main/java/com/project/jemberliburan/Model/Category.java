package com.project.jemberliburan.Model;

public class Category {
    private String name;
    private int iconResId; // ID untuk ikon
    private boolean showIcon; // Apakah ikon harus ditampilkan

    public Category(String name, int iconResId, boolean showIcon) {
        this.name = name;
        this.iconResId = iconResId;
        this.showIcon = showIcon;
    }

    public String getName() {
        return name;
    }

    public int getIconResId() {
        return iconResId;
    }

    public boolean isShowIcon() {
        return showIcon;
    }
}
