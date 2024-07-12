package com.example.alldocunemtreader.ui.preview;

import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.alldocunemtreader.R;

import java.io.File;
import java.io.IOException;

public class PreviewFragment extends Fragment implements View.OnClickListener {
    private ImageView ivPreview;
    private PdfRenderer pdfRenderer;
    private PdfRenderer.Page currentPage;
    private ParcelFileDescriptor parcelFileDescriptor;
    private Button btnNext, btnPrevious;
    private File pdfFile;
    private String documentPath;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_preview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initView(view);
        initData();
        openPdfFile();
        showPage(0);
    }


    private void initView(View view) {
        ivPreview = view.findViewById(R.id.iv_preview);
        btnNext = view.findViewById(R.id.btn_next);
        btnPrevious = view.findViewById(R.id.btn_previous);
        btnNext.setOnClickListener(this);
        btnPrevious.setOnClickListener(this);
    }

    private void initData() {
        if (getArguments() != null) {
            documentPath = getArguments().getString("documentPath");
        }

        if (documentPath != null) {
            pdfFile = new File(documentPath);
        }

    }

    private void openPdfFile() {
        try {
            parcelFileDescriptor = ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY);
            pdfRenderer = new PdfRenderer(parcelFileDescriptor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showPage(int index) {
        if (pdfRenderer.getPageCount() <= index) {
            return;
        }

        // 关闭之前的页面
        if (currentPage != null) {
            currentPage.close();
        }

        // 打开新页面
        currentPage = pdfRenderer.openPage(index);

        // 渲染到一个 Bitmap 上
        Bitmap bitmap = Bitmap.createBitmap(currentPage.getWidth(), currentPage.getHeight(),
                Bitmap.Config.ARGB_8888);
        currentPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);

        // 在 ImageView 中显示 Bitmap
        ivPreview.setImageBitmap(bitmap);

        // 更新按钮状态
        updateButtonState();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_previous) {
            onPreviousPage();
        } else if (v.getId() == R.id.btn_next) {
            onNextPage();
        }
    }

    // 下一页
    private void onNextPage() {
        showPage(currentPage.getIndex() + 1);
    }

    // 上一页
    private void onPreviousPage() {
        showPage(currentPage.getIndex() - 1);
    }

    // 更新按钮状态
    private void updateButtonState() {
        btnPrevious.setEnabled(currentPage.getIndex() > 0);
        btnNext.setEnabled(currentPage.getIndex() < pdfRenderer.getPageCount() - 1);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        closePdfRenderer();
    }

    // 释放资源
    private void closePdfRenderer() {
        if (currentPage != null) {
            currentPage.close();
        }
        if (pdfRenderer != null) {
            pdfRenderer.close();
        }
        try {
            if (parcelFileDescriptor != null) {
                parcelFileDescriptor.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}