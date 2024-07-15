package com.example.alldocunemtreader.ui.file;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.example.alldocunemtreader.R;
import com.example.alldocunemtreader.constants.DocumentRelatedConstants;
import com.example.alldocunemtreader.constants.RequestCodeConstants;
import com.example.alldocunemtreader.ui.common.FileItemView;
import com.example.alldocunemtreader.utils.DocumentUtils;

public class FileFragment extends Fragment {
    private Toolbar tbFile;
    private FileItemView fivDevice, fivMoreFiles;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_file, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initView(view);
        setToolbarButton();
    }


    private void initView(View view) {
        tbFile = view.findViewById(R.id.tb_file);
        fivDevice = view.findViewById(R.id.fiv_device);
        fivMoreFiles = view.findViewById(R.id.fiv_more_files);
        fivDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.fg_on_this_device, null, getNavOptions());
            }
        });

        fivMoreFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goRecentDocument();
            }
        });
    }


    private void setToolbarButton() {
        tbFile.setNavigationIcon(R.drawable.ic_back_black);
        tbFile.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(requireView());
                navController.popBackStack();
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
        startActivityForResult(intent, RequestCodeConstants.REQUEST_GO_RECENT_DOCUMENT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RequestCodeConstants.REQUEST_GO_RECENT_DOCUMENT && resultCode == Activity.RESULT_OK && data != null) {
            Uri uri = data.getData();
            String documentName = DocumentUtils.getFileNameFromUri(requireActivity(), uri);
            String documentPath = DocumentUtils.getPathFromUri(requireActivity(), uri);
            Bundle bundle = new Bundle();
            bundle.putString("documentPath", documentPath);
            bundle.putString("documentName", documentName);
            Navigation.findNavController(getView()).navigate(R.id.fg_preview, bundle, getNavOptions());
        }
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