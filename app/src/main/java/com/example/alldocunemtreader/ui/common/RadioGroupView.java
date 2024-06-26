package com.example.alldocunemtreader.ui.common;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Author: Eccentric
 * Created on 2024/6/25 09:26.
 * Description: com.example.alldocunemtreader.ui.common.RadioGroupView
 */
public class RadioGroupView extends LinearLayout {

    private CustomRadioItem selectedRadioItem;

    public RadioGroupView(Context context) {
        super(context);
    }

    public RadioGroupView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RadioGroupView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void check(CustomRadioItem radioItem) {
        if (selectedRadioItem != null) {
            selectedRadioItem.setChecked(false);
        }
        selectedRadioItem = radioItem;
        selectedRadioItem.setChecked(true);
    }
}