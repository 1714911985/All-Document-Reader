package com.example.alldocunemtreader.ui.common;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Author: Eccentric
 * Created on 2024/6/24 16:39.
 * Description: com.example.alldocunemtreader.ui.common.BottomDialogRadioGroup
 */
public class BottomDialogRadioGroup extends LinearLayout {
    private BottomDialogRadioButton selectedRadioItem;

    public BottomDialogRadioGroup(Context context) {
        super(context);
    }

    public BottomDialogRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomDialogRadioGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void check(BottomDialogRadioButton radioItem) {
        if (selectedRadioItem != null) {
            selectedRadioItem.setChecked(false);
        }
        selectedRadioItem = radioItem;
        selectedRadioItem.setChecked(true);
    }

    public BottomDialogRadioButton getChecked() {
        if (selectedRadioItem != null)
            return selectedRadioItem;
        return null;
    }
}
