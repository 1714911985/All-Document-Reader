package com.example.alldocunemtreader.ui.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.alldocunemtreader.R;

/**
 * Author: Eccentric
 * Created on 2024/6/24 16:39.
 * Description: com.example.alldocunemtreader.ui.common.BottomDialogRadioButton
 */
public class BottomDialogRadioButton extends LinearLayout {
    private ImageView ivBottomDialogButton;
    private TextView tvBottomDialogButton;
    private boolean isChecked = false;

    public BottomDialogRadioButton(Context context) {
        super(context);
        init(context, null);
    }

    public BottomDialogRadioButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BottomDialogRadioButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private int imgResId;
    private int imgSelectedResId;
    private int textColorId;
    private int backgroundId;
    private int textSelectedColorId;
    private int backgroundSelectedResId;
    private String text;

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.button_radio_bottom_dialog, this, true);
        ivBottomDialogButton = findViewById(R.id.iv_bottom_dialog_button);
        tvBottomDialogButton = findViewById(R.id.tv_bottom_dialog_button);
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BottomDialogRadioButton);
            imgResId = a.getResourceId(R.styleable.BottomDialogRadioButton_imgSrc, 0);
            backgroundId = a.getResourceId(R.styleable.BottomDialogRadioButton_background, 0);
            imgSelectedResId = a.getResourceId(R.styleable.BottomDialogRadioButton_imgSrcSelected, 0);
            backgroundSelectedResId = a.getResourceId(R.styleable.BottomDialogRadioButton_backgroundSelected, 0);
            textColorId = a.getColor(R.styleable.BottomDialogRadioButton_btnTextColor, 0);
            textSelectedColorId = a.getColor(R.styleable.BottomDialogRadioButton_btnTextColorSelected, 0);
            text = a.getString(R.styleable.BottomDialogRadioButton_btnText);
            drawButton(isChecked);

            a.recycle();

            this.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setChecked(true);
                    if (getParent() instanceof BottomDialogRadioGroup) {
                        ((BottomDialogRadioGroup) getParent()).check(BottomDialogRadioButton.this);
                    }
                }
            });
        }
    }

    private void drawButton(boolean isChecked) {
        ivBottomDialogButton.setImageResource(isChecked ? imgSelectedResId : imgResId);
        tvBottomDialogButton.setTextColor(isChecked ? textSelectedColorId : textColorId);
        tvBottomDialogButton.setText(text);
        BottomDialogRadioButton.this.setBackgroundResource(isChecked ? backgroundSelectedResId : backgroundId);
    }


    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
        drawButton(isChecked);
    }
}
