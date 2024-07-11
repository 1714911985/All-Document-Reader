package com.example.alldocunemtreader.ui.file;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alldocunemtreader.R;
import com.example.alldocunemtreader.constants.DocumentRelatedConstants;
import com.example.alldocunemtreader.data.model.FileItem;
import com.example.alldocunemtreader.ui.common.adapter.FileItemAdapter;
import com.example.alldocunemtreader.viewmodelfactory.OnThisDeviceViewModelFactory;

public class OnThisDeviceFragment extends Fragment implements FileItemAdapter.FileItemClickListener {
    private Toolbar tbOnThisDevice;
    private RecyclerView rvShowFolder;
    private FileItemAdapter adapter;
    private OnThisDeviceViewModel onThisDeviceViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_on_this_device, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initView(view);
        initData();
        setRecycleView();
        setToolbarButton();
    }

    private void initView(View view) {
        tbOnThisDevice = view.findViewById(R.id.tb_on_this_device);
        rvShowFolder = view.findViewById(R.id.rv_show_folder);
    }

    private void initData() {
        onThisDeviceViewModel = new ViewModelProvider(this, new OnThisDeviceViewModelFactory(requireActivity().getApplication())).get(OnThisDeviceViewModel.class);
    }

    private void setRecycleView() {
        adapter = new FileItemAdapter(onThisDeviceViewModel.getFileItemList(), this);
        rvShowFolder.setAdapter(adapter);
        rvShowFolder.setLayoutManager(new LinearLayoutManager(requireActivity()));
    }


    private void setToolbarButton() {
        tbOnThisDevice.setNavigationIcon(R.drawable.ic_back_black);
        tbOnThisDevice.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. 拿到当前文件列表的父文件
                FileItem currentParent = adapter.getCurrentParent();
                if (currentParent != null) {
                    //2. 父文件不为null  更新为父亲那一代的列表
                    if (currentParent.getParent() != null) {
                        // 2.1 父文件的父亲不为null 说明没到最外层
                        adapter.updateAdapter(currentParent.getParent().getChildren(), currentParent.getParent());
                    } else {
                        // 2.2 父文件的父亲为null  说明父文件是最外层文件  即Environment.getExternalStorageDirectory()下的文件
                        adapter.updateAdapter(onThisDeviceViewModel.getFileItemList(), null);
                    }
                } else {
                    // 3. 父文件为null   直接返回
                    NavController navController = Navigation.findNavController(requireView());
                    navController.popBackStack();
                }
            }
        });
    }

    @Override
    public void onFileItemClick(FileItem fileItem) {
        if (DocumentRelatedConstants.TYPE_DIRECTORY.equals(fileItem.getType())) {
            adapter.updateAdapter(fileItem.getChildren(), fileItem);
        }
    }
}