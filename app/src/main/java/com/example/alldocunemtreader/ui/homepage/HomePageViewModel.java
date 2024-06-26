package com.example.alldocunemtreader.ui.homepage;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.navigation.NavOptions;

import com.example.alldocunemtreader.R;
import com.example.alldocunemtreader.constants.RequestCodeConstants;
import com.example.alldocunemtreader.data.model.DocumentCounts;
import com.example.alldocunemtreader.data.model.EventBusMessage;
import com.example.alldocunemtreader.data.repository.HomePageRepository;
import com.example.alldocunemtreader.utils.ArrangementHelper;
import com.example.alldocunemtreader.utils.EventBusUtils;

import java.util.List;

/**
 * Author: Eccentric
 * Created on 2024/6/24 11:13.
 * Description: com.example.alldocunemtreader.ui.homepage.HomePageViewModel
 */
public class HomePageViewModel extends AndroidViewModel {
    private HomePageRepository homePageRepository;

    public HomePageViewModel(@NonNull Application application, HomePageRepository homePageRepository) {
        super(application);
        this.homePageRepository = homePageRepository;
    }

    public LiveData<DocumentCounts> getDocumentCountList() {
        return homePageRepository.getDocumentCountsLiveData();
    }


    public void fetchDocumentCounts() {
        homePageRepository.fetchDocumentCounts();
    }

    /**
     * 刷新RecycleView的排列方式和顺序
     */
    public void refresh() {
        List<Integer> viewSettings = ArrangementHelper.getViewSettings();
        //刷新
        EventBusUtils.post(new EventBusMessage<>(RequestCodeConstants.REQUEST_REFRESH, viewSettings));
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

}
