package com.example.alldocunemtreader.ui.search;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alldocunemtreader.R;
import com.example.alldocunemtreader.data.model.DocumentInfo;
import com.example.alldocunemtreader.ui.base.BaseFragment;
import com.example.alldocunemtreader.ui.common.adapter.RecycleListAdapter;
import com.example.alldocunemtreader.viewmodelfactory.SearchViewModelFactory;
import com.jakewharton.rxbinding4.widget.RxTextView;
import com.jakewharton.rxbinding4.widget.TextViewAfterTextChangeEvent;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;


public class SearchFragment extends BaseFragment {
    private ImageView ivBack;
    private EditText etSearch;
    private RecyclerView rvShowSearch;
    private SearchViewModel searchViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initView(view);
        initData();
        setBackButton();
        listenEtSearchChange();
        searchAndEcho();
    }

    private void initView(View view) {
        ivBack = view.findViewById(R.id.iv_back);
        View svSearch = view.findViewById(R.id.sv_search);
        etSearch = svSearch.findViewById(R.id.et_search);
        rvShowSearch = view.findViewById(R.id.rv_show_search);
    }

    private void initData() {
        searchViewModel = new ViewModelProvider(this,
                new SearchViewModelFactory(requireActivity().getApplication())).get(SearchViewModel.class);
    }

    /**
     * 设置返回按钮
     */
    private void setBackButton() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(requireView());
                navController.popBackStack();
            }
        });
    }

    /**
     * 监听搜索框内容变化
     */
    @SuppressLint("CheckResult")
    private void listenEtSearchChange() {
        RxTextView.afterTextChangeEvents(etSearch)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TextViewAfterTextChangeEvent>() {
                    @Override
                    public void accept(TextViewAfterTextChangeEvent textViewAfterTextChangeEvent) throws Throwable {
                        searchAndEcho();
                    }
                });

    }

    /**
     * 查询数据库并回显数据
     */
    private void searchAndEcho() {
        searchViewModel.searchDocumentByEditText(etSearch.getText().toString());
        searchViewModel
                .getDocumentInfoLiveData()
                .observe(getViewLifecycleOwner(), new Observer<List<DocumentInfo>>() {
                    @Override
                    public void onChanged(List<DocumentInfo> documentInfos) {
                        setRecycleView(documentInfos);
                    }
                });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void setRecycleView(List<DocumentInfo> filesList) {
        RecycleListAdapter recycleListAdapter =
                new RecycleListAdapter(requireActivity(), filesList, SearchFragment.this);
        rvShowSearch.setAdapter(recycleListAdapter);
        rvShowSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        recycleListAdapter.notifyDataSetChanged();
    }
}