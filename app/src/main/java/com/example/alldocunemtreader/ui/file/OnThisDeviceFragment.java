package com.example.alldocunemtreader.ui.file;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alldocunemtreader.R;

import java.io.File;
import java.util.concurrent.Callable;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class OnThisDeviceFragment extends Fragment {
    private Toolbar tbOnThisDevice;
    private RecyclerView rvShowFolder;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_on_this_device, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initView(view);
        setToolbarButton();
        setRecycleView();
    }

    private void initView(View view) {
        tbOnThisDevice = view.findViewById(R.id.tb_on_this_device);
        rvShowFolder = view.findViewById(R.id.rv_show_folder);
    }

    private void setToolbarButton() {
        tbOnThisDevice.setNavigationIcon(R.drawable.ic_back_black);
        tbOnThisDevice.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(requireView());
                navController.popBackStack();
            }
        });
    }

    @SuppressLint("CheckResult")
    private void setRecycleView() {

    }

    private File getFileNode() {
        File externalStorageRoot = Environment.getExternalStorageDirectory();
        String externalStorageRootPath = externalStorageRoot.getAbsolutePath();
        //获取文件树
        File root = new File(externalStorageRootPath);
        return root;
    }
}