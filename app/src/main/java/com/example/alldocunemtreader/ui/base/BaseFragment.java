package com.example.alldocunemtreader.ui.base;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.alldocunemtreader.R;
import com.example.alldocunemtreader.constants.RequestCodeConstants;
import com.example.alldocunemtreader.data.model.DocumentInfo;
import com.example.alldocunemtreader.data.model.EventBusMessage;
import com.example.alldocunemtreader.ui.common.ClearableEditText;
import com.example.alldocunemtreader.ui.common.adapter.RecycleListAdapter;
import com.example.alldocunemtreader.utils.ArrangementHelper;
import com.example.alldocunemtreader.utils.DocumentUtils;
import com.example.alldocunemtreader.utils.EventBusUtils;
import com.example.alldocunemtreader.viewmodelfactory.BaseViewModelFactory;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.List;
import java.util.Objects;

/**
 * Author: Eccentric
 * Created on 2024/6/25 15:02.
 * Description: com.example.alldocunemtreader.ui.base.BaseFragment
 */
public abstract class BaseFragment extends Fragment
        implements RecycleListAdapter.OnShowFileDetailsBottomSheetDialogListener, View.OnClickListener {
    private static final String AUTHORITY = "com.example.alldocunemtreader.fileProvider";
    private static final String SHARE = "share";

    private BottomSheetDialog bottomSheetDialog;
    private Dialog dlFileInfo, dlShowRename, dlJudgeDelete;
    private ImageView ivFileDetailsIcon;
    private TextView tvFileDetailsName, tvFileDetailsTimeAndSize;
    private ImageButton ibFileDetailsFavorite, ibFileDetailsRename, ibFileDetailsShortcut,
            ibFileDetailsInfo, ibFileDetailsDelete, ibFileDetailsShare;
    private ClearableEditText cetRenameDialogEditText;
    private DocumentInfo currentFile;
    private BaseViewModel baseViewModel;

    @Override
    public void showBottomSheetDialog(Context context, DocumentInfo currentFile) {
        this.currentFile = currentFile;
        bottomSheetDialog = new BottomSheetDialog(requireActivity());
        View view = LayoutInflater.from(requireActivity())
                .inflate(R.layout.dialog_bottom_file_details, null);
        bottomSheetDialog.setContentView(view);
        initView(view, currentFile);
        baseViewModel = new ViewModelProvider(this,
                new BaseViewModelFactory(requireActivity().getApplication())).get(BaseViewModel.class);
        bottomSheetDialog.show();
    }

    private void initView(View view, DocumentInfo currentFile) {
        ivFileDetailsIcon = view.findViewById(R.id.iv_file_details_icon);
        ivFileDetailsIcon.setImageResource(DocumentUtils.getIconResourceForFileType(currentFile.getType()));
        tvFileDetailsName = view.findViewById(R.id.tv_file_details_name);
        tvFileDetailsName.setText(currentFile.getName());
        tvFileDetailsTimeAndSize = view.findViewById(R.id.tv_file_details_time_and_size);
        tvFileDetailsTimeAndSize.setText(
                DocumentUtils.getFileTimeAndSize(currentFile.getCreatedTime(), currentFile.getSize()));
        ibFileDetailsFavorite = view.findViewById(R.id.ib_file_details_favorite);
        ibFileDetailsFavorite.setBackground(new ColorDrawable(ContextCompat.getColor(requireActivity(),
                currentFile.getIsFavorite() == 0 ? R.color.very_light_blue : R.color.light_yellow)));
        ibFileDetailsFavorite.setSelected(currentFile.getIsFavorite() != 0);
        ibFileDetailsRename = view.findViewById(R.id.ib_file_details_rename);
        ibFileDetailsShortcut = view.findViewById(R.id.ib_file_details_shortcut);
        ibFileDetailsInfo = view.findViewById(R.id.ib_file_details_info);
        ibFileDetailsDelete = view.findViewById(R.id.ib_file_details_delete);
        ibFileDetailsShare = view.findViewById(R.id.ib_file_details_share);
        ibFileDetailsFavorite.setOnClickListener(this);
        ibFileDetailsRename.setOnClickListener(this);
        ibFileDetailsShortcut.setOnClickListener(this);
        ibFileDetailsInfo.setOnClickListener(this);
        ibFileDetailsDelete.setOnClickListener(this);
        ibFileDetailsShare.setOnClickListener(this);
    }


    /**
     * 修改收藏状态
     */
    private void switchFavoriteStatus() {
        baseViewModel.switchFavoriteStatus(currentFile);
        baseViewModel.getIsFavoriteLiveData().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                ibFileDetailsFavorite.setBackground(new ColorDrawable(ContextCompat.getColor(requireActivity(),
                        1 - currentFile.getIsFavorite() == 0 ? R.color.very_light_blue : R.color.light_yellow)));
                ibFileDetailsFavorite.setSelected(1 - currentFile.getIsFavorite() != 0);
                refresh();
            }
        });
        bottomSheetDialog.dismiss();
    }

    /**
     * 刷新RecucleView
     */
    private void refresh() {
        //刷新
        EventBusUtils.post(new EventBusMessage<>(RequestCodeConstants.REQUEST_REFRESH,
                ArrangementHelper.getViewSettings()));
    }

    /**
     * 展示 重命名 弹窗
     */

    private void showRenameDialog() {
        bottomSheetDialog.dismiss();
        dlShowRename = new Dialog(requireActivity(), R.style.CustomDialogTheme);
        View view = getLayoutInflater().inflate(R.layout.dialog_rename, null);
        dlShowRename.setContentView(view);
        dlShowRename.getWindow().setWindowAnimations(R.style.DialogAnimation);
        initRenameDialogView(view);
        dlShowRename.show();
    }

    private void initRenameDialogView(View view) {
        cetRenameDialogEditText = view.findViewById(R.id.cet_rename_dialog_text);
        cetRenameDialogEditText.setText(DocumentUtils.getFileNameWithoutExtension(currentFile.getName()));
        Button btnRenameDialogCancel = view.findViewById(R.id.btn_rename_dialog_cancel);
        Button btnRenameDialogConfirm = view.findViewById(R.id.btn_rename_dialog_confirm);
        btnRenameDialogCancel.setOnClickListener(this);
        btnRenameDialogConfirm.setOnClickListener(this);
    }

    private void changeFileName() {
        //获取文件后缀
        String fileExtension = DocumentUtils.getFileExtension(currentFile.getName());
        String newFileNameWithoutExtension = cetRenameDialogEditText.getText().toString();
        if (!TextUtils.isEmpty(newFileNameWithoutExtension)) {
            final String newFileName = newFileNameWithoutExtension + fileExtension;
            baseViewModel.changeFileName(newFileName, currentFile);
        } else {
            EventBusUtils.post(new EventBusMessage<>(RequestCodeConstants.NEW_FILE_NAME_IS_NULL, null));
        }
        dlShowRename.dismiss();
    }


    private void showFileInfoDialog() {
        bottomSheetDialog.dismiss();
        dlFileInfo = new Dialog(requireActivity(), R.style.CustomDialogTheme);
        dlFileInfo.setContentView(R.layout.dialog_file_info);
        initFileInfoDialog(dlFileInfo, currentFile);
        Window window = dlFileInfo.getWindow();
        window.setWindowAnimations(R.style.DialogAnimation);
        dlFileInfo.show();
    }

    private void initFileInfoDialog(Dialog fileInfoDialog, DocumentInfo currentFile) {
        TextView tvDocumentName = fileInfoDialog.findViewById(R.id.tv_document_name);
        TextView tvDocumentPath = fileInfoDialog.findViewById(R.id.tv_document_path);
        TextView tvDocumentSize = fileInfoDialog.findViewById(R.id.tv_document_size);
        TextView tvDocumentType = fileInfoDialog.findViewById(R.id.tv_document_type);
        TextView tvDocumentLastModified = fileInfoDialog.findViewById(R.id.tv_document_last_modified);
        Button btnDocumentOk = fileInfoDialog.findViewById(R.id.btn_document_ok);

        tvDocumentName.setText(currentFile.getName());
        tvDocumentPath.setText(currentFile.getPath());
        tvDocumentSize.setText(DocumentUtils.formatSize(currentFile.getSize()));
        tvDocumentType.setText(currentFile.getType());
        tvDocumentLastModified.setText(DocumentUtils.formatTime(currentFile.getChangedTime()));
        btnDocumentOk.setOnClickListener(this);

    }

    private void showJudgeDeleteDialog() {
        bottomSheetDialog.dismiss();
        dlJudgeDelete = new Dialog(requireActivity(), R.style.CustomDialogTheme);
        View view = getLayoutInflater().inflate(R.layout.dialog_judge_delete, null);
        dlJudgeDelete.setContentView(view);
        dlJudgeDelete.getWindow().setWindowAnimations(R.style.DialogAnimation);
        initJudgeDeleteDialogView(view);
        dlJudgeDelete.show();
    }

    private void initJudgeDeleteDialogView(View view) {
        Button btnDeleteDialogCancel = view.findViewById(R.id.btn_delete_dialog_cancel);
        Button btnDeleteDialogConfirm = view.findViewById(R.id.btn_delete_dialog_confirm);
        btnDeleteDialogCancel.setOnClickListener(this);
        btnDeleteDialogConfirm.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ib_file_details_favorite) {
            switchFavoriteStatus();
        } else if (v.getId() == R.id.ib_file_details_rename) {
            showRenameDialog();
        } else if (v.getId() == R.id.ib_file_details_shortcut) {
            //啥也不干
            bottomSheetDialog.dismiss();
        } else if (v.getId() == R.id.ib_file_details_info) {
            showFileInfoDialog();
        } else if (v.getId() == R.id.ib_file_details_delete) {
            showJudgeDeleteDialog();
        } else if (v.getId() == R.id.ib_file_details_share) {
            bottomSheetDialog.dismiss();
            shareFile();
        } else if (v.getId() == R.id.btn_rename_dialog_cancel) {
            dlShowRename.dismiss();
        } else if (v.getId() == R.id.btn_rename_dialog_confirm) {
            // TODO 文件重命名
            dlShowRename.dismiss();
        } else if (v.getId() == R.id.btn_document_ok) {
            dlFileInfo.dismiss();
        } else if (v.getId() == R.id.btn_delete_dialog_cancel) {
            dlJudgeDelete.dismiss();
        } else if (v.getId() == R.id.btn_delete_dialog_confirm) {
            // TODO 文件删除
            dlJudgeDelete.dismiss();
        }
    }

    private void shareFile() {
        Uri uri = FileProvider.getUriForFile(requireActivity(),
                AUTHORITY,
                new File(currentFile.getPath()));
        Intent share = new Intent(Intent.ACTION_SEND);
        share.addCategory(Intent.CATEGORY_DEFAULT);
        share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        share.setType("*/*");
        share.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(share, SHARE));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageEventBusBase(EventBusMessage<List<Integer>> message) {
        if (Objects.equals(message.getCode(), RequestCodeConstants.NEW_FILE_NAME_IS_NULL)) {
            Toast.makeText(requireActivity(), getResources().getText(R.string.file_name_not_null), Toast.LENGTH_SHORT).show();
        } else if (Objects.equals(message.getCode(), RequestCodeConstants.MEDIASTORE_FILENAME_UPDATE_FAILED)) {
            //MediaStore更新文件名失败
            Toast.makeText(requireActivity(), getResources().getText(R.string.file_name_update_failed), Toast.LENGTH_SHORT).show();
        } else if (Objects.equals(message.getCode(), RequestCodeConstants.DATABASE_FILENAME_UPDATE_SUCCESS)) {
            //文件名更新成功
            Toast.makeText(requireActivity(), getResources().getText(R.string.file_name_update_success), Toast.LENGTH_SHORT).show();
        }
    }


}
