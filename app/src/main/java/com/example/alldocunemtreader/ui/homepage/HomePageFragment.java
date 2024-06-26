package com.example.alldocunemtreader.ui.homepage;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import com.example.alldocunemtreader.R;
import com.example.alldocunemtreader.constants.DocumentRelatedConstants;
import com.example.alldocunemtreader.constants.MMKVKeyConstants;
import com.example.alldocunemtreader.constants.RequestCodeConstants;
import com.example.alldocunemtreader.data.model.DocumentCounts;
import com.example.alldocunemtreader.data.model.EventBusMessage;
import com.example.alldocunemtreader.data.model.GridItem;
import com.example.alldocunemtreader.ui.common.adapter.CollectAdapter;
import com.example.alldocunemtreader.ui.common.adapter.GridAdapter;
import com.example.alldocunemtreader.ui.main.MainActivity;
import com.example.alldocunemtreader.utils.ArrangementHelper;
import com.example.alldocunemtreader.utils.EventBusUtils;
import com.example.alldocunemtreader.utils.LanguageChangedManager;
import com.example.alldocunemtreader.utils.MMKVManager;
import com.example.alldocunemtreader.utils.ThemeModeManager;
import com.example.alldocunemtreader.viewmodelfactory.HomePageViewModelFactory;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomePageFragment extends Fragment implements View.OnClickListener {
    public static final String MIME_TYPE_TEXT_PLAIN = "text/plain";
    //分享的URL   谷歌应用商城链接+包名
    private static final String APP_URL = "https://play.google.com/store/apps/details?id=com.example.alldocumentreader";

    private DrawerLayout dlyMain;
    private ImageButton ivRefresh, ivSortNormal;
    private Toolbar tbMain;
    private GridView gvClassification;
    private TabLayout tlyCollect;
    private ViewPager2 vp2Collect;
    private Button btnMaybeLater, btnRateNow;
    private NavigationView ngvDrawer;
    private NavController navController;
    private Dialog dlThemeMode, dlRateUs;

    private GridAdapter adapter;
    private HomePageViewModel homePageViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        EventBusUtils.register(this);
        initView(view);
        init();
        initData();
        setGridView(new DocumentCounts(0, 0, 0, 0,
                0, 0, 0));
        bindOpenButtonToToolBar();
        setViewPager2();
        setTabLayout();
        setDrawerLayoutButton();
        setToolBarButton();
    }


    @Override
    public void onResume() {
        super.onResume();
        homePageViewModel.refresh();
    }

    private void init() {
        //设置主题模式
        ThemeModeManager.initThemeMode();

        //设置语言
        LanguageChangedManager.initLanguage(requireActivity(), ngvDrawer);
    }


    private void initView(View view) {
        dlyMain = view.findViewById(R.id.dly_main);
        tbMain = view.findViewById(R.id.tb_main);
        gvClassification = view.findViewById(R.id.gv_classification);
        tlyCollect = view.findViewById(R.id.tly_collect);
        vp2Collect = view.findViewById(R.id.vp2_collect);
        ngvDrawer = view.findViewById(R.id.ngv_drawer);
        ivRefresh = view.findViewById(R.id.iv_refresh);
        ivSortNormal = view.findViewById(R.id.iv_sort_normal);
        ivRefresh.setOnClickListener(this);
        ivSortNormal.setOnClickListener(this);
        navController = Navigation.findNavController(view);
    }

    private void initData() {
        homePageViewModel = new ViewModelProvider(this,
                new HomePageViewModelFactory(requireActivity().getApplication()))
                .get(HomePageViewModel.class);
    }

    /**
     * 绑定打开侧边栏按钮
     */
    private void bindOpenButtonToToolBar() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(requireActivity(),
                dlyMain, tbMain, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        dlyMain.addDrawerListener(toggle);
        toggle.syncState();
    }

    /**
     * 设置最近和收藏的viewpager2
     */
    private void setViewPager2() {
        vp2Collect.setAdapter(new CollectAdapter(requireActivity()));
    }

    /**
     * 绑定TabLayout和ViewPager2
     */
    private void setTabLayout() {
        new TabLayoutMediator(tlyCollect, vp2Collect, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText(getResources().getText(R.string.recent));
                        break;
                    case 1:
                        tab.setText(getResources().getText(R.string.favorite));
                        break;
                }
            }
        }).attach();
    }

    private void setDrawerLayoutButton() {
        NavOptions navOptions = homePageViewModel.getNavOptions();

        ngvDrawer.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.item_theme_mode) {//主题模式
                    setAndShowChangeThemeModeDialog();
                    return true;
                } else if (item.getItemId() == R.id.item_language) {//语言切换
                    navController.navigate(R.id.fg_change_language, null, navOptions);
                    return true;
                } else if (item.getItemId() == R.id.item_evaluate_us) {//评价
                    setAndShowRateUsDialog();
                    return true;
                } else if (item.getItemId() == R.id.item_analytical_applications) {//分享
                    shareApp();
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 设置并显示主题切换的Dialog
     */
    private void setAndShowChangeThemeModeDialog() {
        //                                            设置dialog自带的背景框为透明
        dlThemeMode = new Dialog(requireActivity(), R.style.CustomDialogTheme);
        dlThemeMode.setContentView(R.layout.dialog_theme_mode);
        Window window = dlThemeMode.getWindow();
        //设置dialog的大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setWindowAnimations(R.style.DialogAnimation);
        dlThemeMode.show();

        dlyMain.close();

        //设置默认选中的模式
        int mode = MMKVManager.decodeInt(MMKVKeyConstants.MODE, AppCompatDelegate.MODE_NIGHT_NO);
        switch (mode) {
            case AppCompatDelegate.MODE_NIGHT_NO:
                RadioButton rbLightMode = dlThemeMode.findViewById(R.id.rb_light_mode);
                rbLightMode.setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_YES:
                RadioButton rbNightMode = dlThemeMode.findViewById(R.id.rb_dark_mode);
                rbNightMode.setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM:
                RadioButton rbAutoMode = dlThemeMode.findViewById(R.id.rb_auto_mode);
                rbAutoMode.setChecked(true);
                break;
            default:
                break;
        }

        listenThemeModeChanged();

    }

    /**
     * 监听用户切换主题
     */
    private void listenThemeModeChanged() {
        RadioGroup rgThemeMode = dlThemeMode.findViewById(R.id.rg_theme_mode);
        rgThemeMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ThemeModeManager.listenThemeModeChanged(checkedId);
                requireActivity().finish();
                startActivity(new Intent(requireActivity(), MainActivity.class));
            }
        });
    }

    /**
     * 设置并显示评价的Dialog
     */
    private void setAndShowRateUsDialog() {
        dlRateUs = new Dialog(requireActivity(), R.style.CustomDialogTheme);
        dlRateUs.setContentView(R.layout.dialog_rate_us);
        Window window = dlRateUs.getWindow();
        //设置dialog的大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setWindowAnimations(R.style.DialogAnimation);
        dlRateUs.show();

        btnMaybeLater = dlRateUs.findViewById(R.id.btn_maybe_later);
        btnRateNow = dlRateUs.findViewById(R.id.btn_rate_now);
        btnMaybeLater.setOnClickListener(this);
        btnRateNow.setOnClickListener(this);
        dlyMain.close();
    }

    /**
     * 分享App
     */
    private void shareApp() {
        String title = "分享应用";
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType(MIME_TYPE_TEXT_PLAIN);
        share.putExtra(Intent.EXTRA_TEXT, APP_URL);
        startActivity(Intent.createChooser(share, title));
    }

    /**
     * toolbar右边的两个按钮
     */
    private void setToolBarButton() {
        tbMain.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.item_search) {
                    //搜索按钮
                    navController.navigate(R.id.fg_search, null, homePageViewModel.getNavOptions());
                    return true;
                } else if (item.getItemId() == R.id.item_file) {
                    //文件按钮
                    goRecentDocument();
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 去最近使用文件界面
     */
    private void goRecentDocument() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{
                DocumentRelatedConstants.MIME_TYPE_PDF,
                DocumentRelatedConstants.MIME_TYPE_DOC,
                DocumentRelatedConstants.MIME_TYPE_DOCX,
                DocumentRelatedConstants.MIME_TYPE_PPT,
                DocumentRelatedConstants.MIME_TYPE_PPTX,
                DocumentRelatedConstants.MIME_TYPE_XLS,
                DocumentRelatedConstants.MIME_TYPE_XLSX,
                DocumentRelatedConstants.MIME_TYPE_TXT,
                DocumentRelatedConstants.MIME_TYPE_XML,
                DocumentRelatedConstants.MIME_TYPE_JSON
        });
        startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventBusMessage(EventBusMessage eventBusMessage) {
        homePageViewModel.fetchDocumentCounts();
        homePageViewModel.getDocumentCountList().observe(this, new Observer<DocumentCounts>() {
            @Override
            public void onChanged(DocumentCounts documentCounts) {
                setGridView(documentCounts);
            }
        });
    }

    /**
     * 设置GridView显示文件图标和数量
     *
     * @param documentCounts
     */
    private void setGridView(DocumentCounts documentCounts) {
        List<GridItem> gridItemList = generateItems(documentCounts);
        adapter = new GridAdapter(requireActivity(), R.layout.grid_file, gridItemList, getView());
        gvClassification.setAdapter(adapter);
    }

    private List<GridItem> generateItems(DocumentCounts documentCounts) {
        return new ArrayList<>(Arrays.asList(
                new GridItem(R.drawable.ic_home_all, getResources().getText(R.string.all).toString(),
                        String.valueOf(documentCounts.getAllDocumentCount()),
                        documentCounts.getAllDocumentCount() == 1 ?
                                getResources().getText(R.string.file).toString() : getResources().getText(R.string.files).toString()),
                new GridItem(R.drawable.ic_home_pdf,
                        getResources().getText(R.string.pdf).toString(), String.valueOf(documentCounts.getAllPDFCount()),
                        documentCounts.getAllPDFCount() == 1 ?
                                getResources().getText(R.string.file).toString() : getResources().getText(R.string.files).toString()),
                new GridItem(R.drawable.ic_home_doc,
                        getResources().getText(R.string.document).toString(), String.valueOf(documentCounts.getAllDOCCount()),
                        documentCounts.getAllDOCCount() == 1 ?
                                getResources().getText(R.string.file).toString() : getResources().getText(R.string.files).toString()),
                new GridItem(R.drawable.ic_home_xls,
                        getResources().getText(R.string.xls).toString(), String.valueOf(documentCounts.getAllXLSCount()),
                        documentCounts.getAllXLSCount() == 1 ?
                                getResources().getText(R.string.file).toString() : getResources().getText(R.string.files).toString()),
                new GridItem(R.drawable.ic_home_ppt,
                        getResources().getText(R.string.ppt).toString(), String.valueOf(documentCounts.getAllPPTCount()),
                        documentCounts.getAllPPTCount() == 1 ?
                                getResources().getText(R.string.file).toString() : getResources().getText(R.string.files).toString()),
                new GridItem(R.drawable.ic_home_txt,
                        getResources().getText(R.string.txt).toString(), String.valueOf(documentCounts.getAllTXTCount()),
                        documentCounts.getAllTXTCount() == 1 ?
                                getResources().getText(R.string.file).toString() : getResources().getText(R.string.files).toString()),
                new GridItem(R.drawable.ic_home_other,
                        getResources().getText(R.string.other).toString(), String.valueOf(documentCounts.getAllOTHERCount()),
                        documentCounts.getAllOTHERCount() == 1 ?
                                getResources().getText(R.string.file).toString() : getResources().getText(R.string.files).toString())
        ));
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_maybe_later) {
            dlRateUs.dismiss();
        } else if (v.getId() == R.id.btn_rate_now) {
            Toast.makeText(requireActivity(), getResources().getText(R.string.thanks_for_your_rate), Toast.LENGTH_SHORT).show();
            dlRateUs.dismiss();
        } else if (v.getId() == R.id.iv_refresh) {
            homePageViewModel.refresh();
        } else if (v.getId() == R.id.iv_sort_normal) {
            ArrangementHelper.showBottomDialog(requireActivity());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtils.unregister(this);
    }
}