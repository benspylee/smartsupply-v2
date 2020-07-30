package com.asuravan.smartsupply.activity.commons;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.asuravan.smartsupply.R;

public class BlueBeeNumPicker extends LinearLayout {

    // ui components
    private Context mContext;
    private Button decrementButton;
    private Button incrementButton;
    private EditText displayEditText;

    private int minValue;
    private int maxValue;
    private int unit;
    private int currentValue;
    private int layout;
    private boolean focusable;

    public BlueBeeNumPicker(Context context) {
        super(context);
    }

    public BlueBeeNumPicker(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs);
    }

    private void initialize(Context context, AttributeSet attrs) {
        this.layout = R.layout.numberpicker;
        this.mContext = context;
        LayoutInflater.from(this.mContext).inflate(layout, this, true);
    }

    public BlueBeeNumPicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BlueBeeNumPicker(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
