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
import com.example.alldocunemtreader.data.model.GridItem;
import com.example.alldocunemtreader.data.repository.DocumentInfoRepository;
import com.example.alldocunemtreader.data.repository.HomePageRepository;
import com.example.alldocunemtreader.utils.ArrangementHelper;
import com.example.alldocunemtreader.utils.EventBusUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Author: Eccentric
 * Created on 2024/6/24 11:13.
 * Description: com.example.alldocunemtreader.ui.homepage.HomePageViewModel
 */
public class HomePageViewModel extends AndroidViewModel {
    private HomePageRepository homePageRepository;
    private DocumentInfoRepository documentInfoRepository;
    private Application application;

    public HomePageViewModel(@NonNull Application application, HomePageRepository homePageRepository, DocumentInfoRepository documentInfoRepository) {
        super(application);
        this.application = application;
        this.homePageRepository = homePageRepository;
        this.documentInfoRepository = documentInfoRepository;
    }

    public LiveData<DocumentCounts> getDocumentCountList() {
        return homePageRepository.getDocumentCountsLiveData();
    }


    public void fetchDocumentCounts() {
        homePageRepository.fetchDocumentCounts(documentInfoRepository.getCurrentCache());
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

    public List<GridItem> generateItems(DocumentCounts documentCounts) {
        return new ArrayList<>(Arrays.asList(
                new GridItem(R.drawable.ic_home_all, application.getResources().getText(R.string.all).toString(),
                        String.valueOf(documentCounts.getAllDocumentCount()),
                        documentCounts.getAllDocumentCount() == 1 ?
                                application.getResources().getText(R.string.file).toString() : application.getResources().getText(R.string.files).toString()),
                new GridItem(R.drawable.ic_home_pdf,
                        application.getResources().getText(R.string.pdf).toString(), String.valueOf(documentCounts.getAllPDFCount()),
                        documentCounts.getAllPDFCount() == 1 ?
                                application.getResources().getText(R.string.file).toString() : application.getResources().getText(R.string.files).toString()),
                new GridItem(R.drawable.ic_home_doc,
                        application.getResources().getText(R.string.document).toString(), String.valueOf(documentCounts.getAllDOCCount()),
                        documentCounts.getAllDOCCount() == 1 ?
                                application.getResources().getText(R.string.file).toString() : application.getResources().getText(R.string.files).toString()),
                new GridItem(R.drawable.ic_home_xls,
                        application.getResources().getText(R.string.xls).toString(), String.valueOf(documentCounts.getAllXLSCount()),
                        documentCounts.getAllXLSCount() == 1 ?
                                application.getResources().getText(R.string.file).toString() : application.getResources().getText(R.string.files).toString()),
                new GridItem(R.drawable.ic_home_ppt,
                        application.getResources().getText(R.string.ppt).toString(), String.valueOf(documentCounts.getAllPPTCount()),
                        documentCounts.getAllPPTCount() == 1 ?
                                application.getResources().getText(R.string.file).toString() : application.getResources().getText(R.string.files).toString()),
                new GridItem(R.drawable.ic_home_txt,
                        application.getResources().getText(R.string.txt).toString(), String.valueOf(documentCounts.getAllTXTCount()),
                        documentCounts.getAllTXTCount() == 1 ?
                                application.getResources().getText(R.string.file).toString() : application.getResources().getText(R.string.files).toString()),
                new GridItem(R.drawable.ic_home_other,
                        application.getResources().getText(R.string.other).toString(), String.valueOf(documentCounts.getAllOTHERCount()),
                        documentCounts.getAllOTHERCount() == 1 ?
                                application.getResources().getText(R.string.file).toString() : application.getResources().getText(R.string.files).toString())
        ));
    }


}
