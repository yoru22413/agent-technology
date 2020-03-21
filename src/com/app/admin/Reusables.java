package com.app.admin;

import javafx.scene.layout.Region;

public abstract class Reusables {

    public Reusables() {

    }

    public static void setStaticLayout(Region r, double height, double width) {
        setMinLayout(r, height, width);
        setMaxLayout(r, height, width);
    }

    public static void setMinLayout(Region c, double height, double width) {
        c.setMinHeight(height);
        c.setMinWidth(width);
    }

    public static void setMaxLayout(Region c, double height, double width) {
        c.setMaxHeight(height);
        c.setMaxWidth(width);
    }

    public static String hexFromPaint(String s) {
        String ret = "#";

        for(int i = 2; i < s.length(); i++) {
            ret = ret + s.charAt(i);
        }

        return ret;
    }

}
