package com.asuravan.smartsupply.utils;

import android.content.Context;
import android.view.View;
import android.widget.PopupMenu;

public class CustomPopUp extends PopupMenu {

    public Object item;

    public Object getItem() {
        return item;
    }

    public void setItem(Object item) {
        this.item = item;
    }

    public CustomPopUp(Context context, View anchor) {
        super(context, anchor);
    }
}
