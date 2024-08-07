package com.example.alldocunemtreader.ui.classification.tabs;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alldocunemtreader.R;
import com.example.alldocunemtreader.constants.DocumentRelatedConstants;
import com.example.alldocunemtreader.constants.RequestCodeConstants;
import com.example.alldocunemtreader.data.model.DocumentInfo;
import com.example.alldocunemtreader.data.model.EventBusMessage;
import com.example.alldocunemtreader.ui.base.BaseFragment;
import com.example.alldocunemtreader.utils.ArrangementHelper;
import com.example.alldocunemtreader.utils.EventBusUtils;
import com.example.alldocunemtreader.utils.RecyclerViewHelper;
import com.example.alldocunemtreader.viewmodelfactory.ShowRecycleViewModelFactory;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Objects;


public class OTHERSelectedFragment extends BaseFragment {
    private RecyclerView rvOtherSelected;
    private NestedScrollView nsvOtherSelectedNoResult;
    private ShowRecycleViewModel showRecycleViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_other_selected, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initView(view);
        initData();
        initializeAdapterAndSetToRecyclerView();
    }

    private void initView(View view) {
        rvOtherSelected = view.findViewById(R.id.rv_other_selected);
        nsvOtherSelectedNoResult = view.findViewById(R.id.nsv_other_selected_no_result);
    }

    private void initData() {
        showRecycleViewModel = new ViewModelProvider(OTHERSelectedFragment.this,
                new ShowRecycleViewModelFactory(requireActivity().getApplication())).get(ShowRecycleViewModel.class);
    }

    private void initializeAdapterAndSetToRecyclerView() {
        RecyclerViewHelper.initializeAdapterAndSetToRecyclerView(requireActivity(), rvOtherSelected, OTHERSelectedFragment.this, 2);
        updateAdapter(ArrangementHelper.getViewSettings());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageEventBus(EventBusMessage<List<Integer>> message) {
        if (Objects.equals(message.getCode(), RequestCodeConstants.REQUEST_REFRESH)) {
            updateAdapter(message.getMessage());
        }
    }

    private void updateAdapter(List<Integer> message) {
        showRecycleViewModel.searchDocumentInfoList(DocumentRelatedConstants.TYPE_OTHER, message);
        showRecycleViewModel.getDocumentInfoListLiveData().observe(getViewLifecycleOwner(), new Observer<List<DocumentInfo>>() {
            @Override
            public void onChanged(List<DocumentInfo> documentInfoList) {
                RecyclerViewHelper.updateAdapter(requireActivity(), rvOtherSelected, message.get(0), documentInfoList, OTHERSelectedFragment.this, nsvOtherSelectedNoResult, 2);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBusUtils.register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBusUtils.unregister(this);
    }
}