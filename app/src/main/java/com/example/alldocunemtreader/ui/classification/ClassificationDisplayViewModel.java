package com.example.alldocunemtreader.ui.classification;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavOptions;

import com.example.alldocunemtreader.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Author: Eccentric
 * Created on 2024/6/25 13:58.
 * Description: com.example.alldocunemtreader.ui.classification.ClassificationDisplayViewModel
 */
public class ClassificationDisplayViewModel extends ViewModel {
    private MutableLiveData<String> titleLiveData = new MutableLiveData<>();

    public void setToolBarTitle(Context context, int position) {
        CharSequence title = null;
        switch (position) {
            case 0:
                title = context.getResources().getText(R.string.title_all);
                break;
            case 1:
                title = context.getResources().getText(R.string.title_pdf);
                break;
            case 2:
                title = context.getResources().getText(R.string.title_document);
                break;
            case 3:
                title = context.getResources().getText(R.string.title_xls);
                break;
            case 4:
                title = context.getResources().getText(R.string.title_ppt);
                break;
            case 5:
                title = context.getResources().getText(R.string.title_txt);
                break;
            case 6:
                title = context.getResources().getText(R.string.title_other);
                break;
        }
        titleLiveData.postValue(title.toString());
    }

    public LiveData<String> getTitleLiveData() {
        return titleLiveData;
    }

    public NavOptions getNavOptions() {
        // 创建一个NavOptions实例
        return new NavOptions.Builder()
                .setEnterAnim(R.anim.common_slide_in_right) // 进入动画
                .setExitAnim(R.anim.common_slide_out_left)   // 退出动画
                .setPopEnterAnim(R.anim.common_slide_in_left) // 回退进入动画
                .setPopExitAnim(R.anim.common_slide_out_right)// 回退退出动画;
                .build();
    }

    public List<Integer> getToolbarColorByPosition(int position) {
        int toolbarColorId = 0;
        int textColorId = 0;
        int indicatorColorId = 0;
        switch (position) {
            case 0:
                toolbarColorId = R.color.all_toolbar_color;
                textColorId = R.color.all_text_color;
                indicatorColorId = R.color.all_indicator_color;

                break;
            case 1:
                toolbarColorId = R.color.pdf_toolbar_color;
                textColorId = R.color.pdf_text_color;
                indicatorColorId = R.color.pdf_indicator_color;
                break;
            case 2:
                toolbarColorId = R.color.doc_toolbar_color;
                textColorId = R.color.doc_text_color;
                indicatorColorId = R.color.doc_indicator_color;
                break;
            case 3:
                toolbarColorId = R.color.xls_toolbar_color;
                textColorId = R.color.xls_text_color;
                indicatorColorId = R.color.xls_indicator_color;
                break;
            case 4:
                toolbarColorId = R.color.ppt_toolbar_color;
                textColorId = R.color.ppt_text_color;
                indicatorColorId = R.color.ppt_indicator_color;
                break;
            case 5:
                toolbarColorId = R.color.txt_toolbar_color;
                textColorId = R.color.txt_text_color;
                indicatorColorId = R.color.txt_indicator_color;
                break;
            case 6:
                toolbarColorId = R.color.other_toolbar_color;
                textColorId = R.color.other_text_color;
                indicatorColorId = R.color.other_indicator_color;
                break;
        }
        return new ArrayList<>(Arrays.asList(toolbarColorId,indicatorColorId,textColorId));
    }

    public int getTextIdByPosition(int position) {
        int stringId = 0;
        switch (position) {
            case 0:
                stringId = R.string.ALL;
                break;
            case 1:
                stringId = R.string.PDF;
                break;
            case 2:
                stringId = R.string.WORD;
                break;
            case 3:
                stringId = R.string.EXCEL;
                break;
            case 4:
                stringId = R.string.SLIDE;
                break;
            case 5:
                stringId = R.string.TXT;
                break;
            case 6:
                stringId = R.string.OTHER;
                break;
        }
        return stringId;
    }

    public int getTextColorByPosition(int position) {
        int textColorId = 0;
        switch (position) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
        }
        return textColorId;
    }
}
