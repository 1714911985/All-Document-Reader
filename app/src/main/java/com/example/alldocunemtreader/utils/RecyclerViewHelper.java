package com.example.alldocunemtreader.utils;

import android.content.Context;
import android.view.View;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alldocunemtreader.R;
import com.example.alldocunemtreader.data.model.DocumentInfo;
import com.example.alldocunemtreader.ui.base.BaseFragment;
import com.example.alldocunemtreader.ui.common.adapter.RecycleGridAdapter;
import com.example.alldocunemtreader.ui.common.adapter.RecycleListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Eccentric
 * Created on 2024/6/25 16:22.
 * Description: com.example.alldocunemtreader.utils.RecyclerViewHelper
 */
public class RecyclerViewHelper {
    public static void initializeAdapterAndSetToRecyclerView(Context context,
                                                             RecyclerView recyclerView,
                                                             RecycleListAdapter.OnShowFileDetailsBottomSheetDialogListener onShowFileDetailsBottomSheetDialogListener,
                                                             int num) {
        List<Integer> viewSettings = ArrangementHelper.getViewSettings();
        List<DocumentInfo> initialDocumentInfoList = new ArrayList<>();
        if (viewSettings.get(0) == R.id.bdrb_list) {
            RecycleListAdapter recycleListAdapter = new RecycleListAdapter(context, initialDocumentInfoList, onShowFileDetailsBottomSheetDialogListener);
            recyclerView.setAdapter(recycleListAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            //grid
            RecycleGridAdapter recycleGridAdapter = new RecycleGridAdapter(context, initialDocumentInfoList, onShowFileDetailsBottomSheetDialogListener);
            recyclerView.setAdapter(recycleGridAdapter);
            recyclerView.setLayoutManager(new GridLayoutManager(context, num));
        }
    }

    public static void updateAdapter(FragmentActivity activity,
                                     RecyclerView recyclerView,
                                     int viewMethodId,
                                     List<DocumentInfo> documentInfoList,
                                     BaseFragment fragment,
                                     NestedScrollView nsvAllSelectedNoResult,
                                     int num) {
        if (viewMethodId == R.id.bdrb_grid) {
            setupGridAdapter(activity, recyclerView, documentInfoList, fragment, num);
        } else {
            setupListAdapter(activity, recyclerView, documentInfoList, fragment);
        }
        setNoResultViewVisibility(nsvAllSelectedNoResult, documentInfoList);
    }

    private static void setupGridAdapter(FragmentActivity activity, RecyclerView recyclerView, List<DocumentInfo> documentInfoList, BaseFragment fragment, int num) {
        RecycleGridAdapter recycleGridAdapter = new RecycleGridAdapter(activity, documentInfoList, fragment);
        recyclerView.setAdapter(recycleGridAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(activity, num));
    }

    private static void setupListAdapter(FragmentActivity activity, RecyclerView recyclerView, List<DocumentInfo> documentInfoList, BaseFragment fragment) {
        RecycleListAdapter recycleListAdapter = new RecycleListAdapter(activity, documentInfoList, fragment);
        recyclerView.setAdapter(recycleListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
    }


    private static void setNoResultViewVisibility(NestedScrollView nsvAllSelectedNoResult, List<DocumentInfo> documentInfoList) {
        if (documentInfoList.size() > 0) {
            nsvAllSelectedNoResult.setVisibility(View.INVISIBLE);
        } else {
            nsvAllSelectedNoResult.setVisibility(View.VISIBLE);
        }
    }
}
