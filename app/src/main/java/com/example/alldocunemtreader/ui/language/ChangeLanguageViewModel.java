package com.example.alldocunemtreader.ui.language;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.alldocunemtreader.constants.MMKVKeyConstants;
import com.example.alldocunemtreader.utils.MMKVManager;

import java.util.Locale;

/**
 * Author: Eccentric
 * Created on 2024/6/25 09:31.
 * Description: com.example.alldocunemtreader.ui.language.ChangeLanguageViewModel
 */
public class ChangeLanguageViewModel extends ViewModel {
    private final MutableLiveData<String> languageLiveData = new MutableLiveData<>();

    public void changeAppLanguage(Context context, String language) {
        if (context == null) {
            return;
        }
        try {
            Resources resources = context.getResources();
            Configuration configuration = resources.getConfiguration();
            //获取想要切换的语言类型
            Locale locale = new Locale(language);
            configuration.setLocale(locale);
            configuration.setLayoutDirection(locale);
            // updateConfiguration
            DisplayMetrics dm = resources.getDisplayMetrics();
            resources.updateConfiguration(configuration, dm);
            //保存
            MMKVManager.encode(MMKVKeyConstants.LANGUAGE,language);
            languageLiveData.setValue(language);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LiveData<String> getLanguageLiveData() {
        return languageLiveData;
    }
}
