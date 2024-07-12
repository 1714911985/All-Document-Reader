package com.example.alldocunemtreader.ui.preview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.alldocunemtreader.R;

public class PreviewFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_preview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // TODO 预览文件
        WebView wvPreview = view.findViewById(R.id.wv_preview);

        // 在 WebView 中加载本地文件，示例为加载 SD 卡上的文件
        String fileUrl = "file://" + getArguments().getString("documentPath"); // 替换为您的文件路径

        // 配置 WebView
        WebSettings webSettings = wvPreview.getSettings();
        webSettings.setJavaScriptEnabled(true); // 支持 JavaScript
        wvPreview.setWebViewClient(new WebViewClient());

        // 加载文件
        wvPreview.loadUrl(fileUrl);
    }
}