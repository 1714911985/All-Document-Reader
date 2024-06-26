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


public class DOCSelectedFragment extends BaseFragment {
    private RecyclerView rvDocSelected;
    private NestedScrollView nsvDocSelectedNoResult;
    private ShowRecycleViewModel showRecycleViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doc_selected, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initView(view);
        initData();
        initializeAdapterAndSetToRecyclerView();
    }

    private void initView(View view) {
        rvDocSelected = view.findViewById(R.id.rv_doc_selected);
        nsvDocSelectedNoResult = view.findViewById(R.id.nsv_doc_selected_no_result);
    }

    private void initData() {
        showRecycleViewModel = new ViewModelProvider(DOCSelectedFragment.this,
                new ShowRecycleViewModelFactory(requireActivity().getApplication())).get(ShowRecycleViewModel.class);
    }

    private void initializeAdapterAndSetToRecyclerView() {
        RecyclerViewHelper.initializeAdapterAndSetToRecyclerView(requireActivity(), rvDocSelected, DOCSelectedFragment.this,2);
        updateAdapter(ArrangementHelper.getViewSettings());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageEventBus(EventBusMessage<List<Integer>> message) {
        if (Objects.equals(message.getCode(), RequestCodeConstants.REQUEST_REFRESH)){
            updateAdapter(message.getMessage());
        }
    }

    private void updateAdapter(List<Integer> message) {
        showRecycleViewModel.searchDocumentInfoList(DocumentRelatedConstants.TYPE_DOC, message);
        showRecycleViewModel.getDocumentInfoListLiveData().observe(getViewLifecycleOwner(), new Observer<List<DocumentInfo>>() {
            @Override
            public void onChanged(List<DocumentInfo> documentInfoList) {
                RecyclerViewHelper.updateAdapter(requireActivity(), rvDocSelected, message.get(0), documentInfoList, DOCSelectedFragment.this,nsvDocSelectedNoResult,2);
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
