package com.example.alldocunemtreader.ui.classification;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import com.example.alldocunemtreader.R;
import com.example.alldocunemtreader.ui.common.adapter.ClassificationDisplayAdapter;
import com.example.alldocunemtreader.utils.ArrangementHelper;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

public class ClassificationDisplayFragment extends Fragment {
    private static final String POSITION = "position";
    private NavController navController;
    private Toolbar tbClassificationDisplay;
    private TabLayout tlyClassificationDisplay;
    private ViewPager2 vp2ClassificationDisplay;
    private ClassificationDisplayViewModel classificationDisplayViewModel;
    private int position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_classification_display, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initView(view);
        initData();
        setToolBarButton();
        setViewPager2();
        setTabLayout();
    }

    private void initView(View view) {
        navController = Navigation.findNavController(view);
        tbClassificationDisplay = view.findViewById(R.id.tb_classification_display);
        tlyClassificationDisplay = view.findViewById(R.id.tly_classification_display);
        vp2ClassificationDisplay = view.findViewById(R.id.vp2_classification_display);
    }

    private void initData() {
        Bundle bundle = getArguments();
        position = bundle.getInt(POSITION, 0);
        classificationDisplayViewModel = new ViewModelProvider(this).get(ClassificationDisplayViewModel.class);
    }

    /**
     * 设置toolbar 的 返回按钮  和 搜索  展示方式按钮
     */
    private void setToolBarButton() {
        tbClassificationDisplay.setNavigationIcon(R.drawable.ic_back);
        changeToolbarTitle();
        tbClassificationDisplay.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(requireView());
                navController.popBackStack();
            }
        });
        tbClassificationDisplay.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.item_search) {
                    //搜索按钮
                    navController.navigate(R.id.fg_search, null, classificationDisplayViewModel.getNavOptions());
                    return true;
                } else if (item.getItemId() == R.id.item_show) {
                    //展示方式按钮
                    ArrangementHelper.showBottomDialog(requireActivity());
                    return true;
                }
                return false;
            }
        });
    }

    private void changeToolbarTitle() {
        changeToolbarTitle(position);
    }

    private void changeToolbarTitle(int position) {
        classificationDisplayViewModel.setToolBarTitle(requireActivity(), position);
        classificationDisplayViewModel.getTitleLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String title) {
                tbClassificationDisplay.setTitle(title);
            }
        });
    }

    private void setViewPager2() {
        vp2ClassificationDisplay.setAdapter(new ClassificationDisplayAdapter(requireActivity()));
        vp2ClassificationDisplay.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                List<Integer> colorIdList = classificationDisplayViewModel.getToolbarColorByPosition(position);
                tbClassificationDisplay.setBackgroundColor(getResources().getColor(colorIdList.get(0)));
                tlyClassificationDisplay.setSelectedTabIndicatorColor(ContextCompat.getColor(requireContext(), colorIdList.get(1)));
                tlyClassificationDisplay.setTabTextColors(getResources().getColor(R.color.tab_color), requireActivity().getColor(colorIdList.get(2)));
            }
        });
    }

    private void setTabLayout() {
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tlyClassificationDisplay, vp2ClassificationDisplay, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                int stringId = classificationDisplayViewModel.getTextIdByPosition(position);
                tab.setText(getResources().getText(stringId));
            }
        });
        tabLayoutMediator.attach();
        tlyClassificationDisplay.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                changeToolbarTitle(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        vp2ClassificationDisplay.post(new Runnable() {
            @Override
            public void run() {
                TabLayout.Tab tab = tlyClassificationDisplay.getTabAt(position);
                if (tab != null) {
                    tab.select();
                }
            }
        });
    }
}