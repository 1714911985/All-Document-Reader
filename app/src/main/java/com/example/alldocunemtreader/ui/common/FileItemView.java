package com.example.alldocunemtreader.ui.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.alldocunemtreader.R;

/**
 * Author: Eccentric
 * Created on 2024/7/5 10:28.
 * Description: com.example.alldocunemtreader.ui.common.FileItemView
 */
public class FileItemView extends LinearLayout {
    private ImageView ivFileItem;
    private TextView tvFileName;

    public FileItemView(Context context) {
        super(context);
        init(context, null);
    }

    public FileItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FileItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.custom_file_item, this);

        ivFileItem = findViewById(R.id.iv_file_item);
        tvFileName = findViewById(R.id.tv_file_name);
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FileItemView);
            int iconResId = typedArray.getResourceId(R.styleable.FileItemView_fileItemImg, R.drawable.ic_folder_storage);
            String fileName = typedArray.getString(R.styleable.FileItemView_fileItemName);
            typedArray.recycle();
            if (iconResId != 0) {
                ivFileItem.setImageResource(iconResId);
            }

            if (!TextUtils.isEmpty(fileName)) {
                tvFileName.setText(fileName);
            }
        }
    }

    public void setTvFileName(String text) {
        tvFileName.setText(text);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }
}
