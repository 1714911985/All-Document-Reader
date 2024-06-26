package com.example.alldocunemtreader.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.alldocunemtreader.R;
import com.example.alldocunemtreader.constants.LanguageConstants;
import com.example.alldocunemtreader.constants.MMKVKeyConstants;
import com.google.android.material.navigation.NavigationView;

import java.util.Locale;

/**
 * Author: Eccentric
 * Created on 2024/6/26 09:11.
 * Description: com.example.alldocunemtreader.utils.LanguageChangedManager
 */
public class LanguageChangedManager {
    public static void initLanguage(Context context, NavigationView ngvDrawer) {
        String language = MMKVManager.decodeString(MMKVKeyConstants.LANGUAGE, LanguageConstants.ENGLISH);
        setLanguage(context, language, ngvDrawer);
    }

    private static void setLanguage(Context context, String language, NavigationView ngvDrawer) {
        Locale locale = new Locale(language);
        Configuration config = new Configuration();
        config.setLocale(locale);
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
        updateSidebarText(ngvDrawer);
    }

    public static void updateSidebarText(NavigationView ngvDrawer) {
        // 找到侧边栏中的菜单项标题，并更新文本内容
        Menu menu = ngvDrawer.getMenu();

        MenuItem itemLanguage = menu.findItem(R.id.item_language);
        MenuItem itemThemeMode = menu.findItem(R.id.item_theme_mode);
        MenuItem itemRateUs = menu.findItem(R.id.item_evaluate_us);
        MenuItem itemShare = menu.findItem(R.id.item_analytical_applications);

        itemLanguage.setTitle(R.string.language);
        itemThemeMode.setTitle(R.string.theme_mode);
        itemRateUs.setTitle(R.string.rate_us);
        itemShare.setTitle(R.string.share_app);

        View headerView = ngvDrawer.getHeaderView(0);
        TextView tvSecondNav = headerView.findViewById(R.id.tv_second_nav);
        tvSecondNav.setText(R.string.nav_header_text);
    }

}
