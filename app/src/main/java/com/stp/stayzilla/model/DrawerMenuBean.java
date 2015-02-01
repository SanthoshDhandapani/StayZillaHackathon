package com.stp.stayzilla.model;

public class DrawerMenuBean {
    private String title;
    private String fontName;

    public DrawerMenuBean(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setFontName(String id) {
        this.fontName = id;
    }

    public String getFontName() {
        return fontName;
    }
}