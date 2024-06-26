package com.example.alldocunemtreader.ui.common;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;

import com.example.alldocunemtreader.R;

/**
 * Author: Eccentric
 * Created on 2024/6/25 15:13.
 * Description: com.example.alldocunemtreader.ui.common.ClearableEditText
 */
public class ClearableEditText extends AppCompatEditText {

    private Drawable clearDrawable;

    public ClearableEditText(Context context) {
        super(context);
        init();
    }

    public ClearableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClearableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // 获取清除图标
        clearDrawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_clear);
        if (clearDrawable != null) {
            clearDrawable.setBounds(0, 0, clearDrawable.getIntrinsicWidth(), clearDrawable.getIntrinsicHeight());
        }

        // 添加文本变化监听器
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setClearIconVisible(s.length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // 添加触摸事件监听器
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (getCompoundDrawables()[2] != null) {
                    boolean tappedClear = event.getX() > (getWidth() - getPaddingRight() - clearDrawable.getIntrinsicWidth());
                    if (tappedClear) {
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            setText("");
                        }
                        return true;
                    }
                }
                return false;
            }
        });

        // 设置初始的清除图标状态
        setClearIconVisible(false);
    }

    private void setClearIconVisible(boolean visible) {
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], visible ? clearDrawable : null, getCompoundDrawables()[3]);
    }
}
