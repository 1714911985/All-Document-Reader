package com.example.alldocunemtreader.ui.common.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alldocunemtreader.R;
import com.example.alldocunemtreader.constants.RequestCodeConstants;
import com.example.alldocunemtreader.data.model.DocumentInfo;
import com.example.alldocunemtreader.data.model.EventBusMessage;
import com.example.alldocunemtreader.data.source.local.AppDatabase;
import com.example.alldocunemtreader.data.source.local.DocumentInfoDao;
import com.example.alldocunemtreader.utils.ArrangementHelper;
import com.example.alldocunemtreader.utils.DocumentUtils;
import com.example.alldocunemtreader.utils.EventBusUtils;
import com.example.alldocunemtreader.utils.ThreadPoolManager;

import java.util.List;

/**
 * Author: Eccentric
 * Created on 2024/6/25 14:50.
 * Description: com.example.alldocunemtreader.ui.common.adapter.RecycleListAdapter
 */
public class RecycleListAdapter extends RecyclerView.Adapter<RecycleListAdapter.ViewHolder> {
    private Context context;
    private List<DocumentInfo> documentInfoList;
    private DocumentInfoDao documentInfoDao;
    private OnShowFileDetailsBottomSheetDialogListener listener;

    public RecycleListAdapter(Context context, List<DocumentInfo> documentInfoList,
                              OnShowFileDetailsBottomSheetDialogListener listener) {
        this.context = context;
        this.documentInfoList = documentInfoList;
        this.listener = listener;
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        documentInfoDao = appDatabase.documentInfoDao();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<DocumentInfo> newDocumentInfoList) {
        this.documentInfoList.clear();
        this.documentInfoList.addAll(newDocumentInfoList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_list_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DocumentInfo currentFile = documentInfoList.get(position); // 获取当前项的文件对象

        int resId = DocumentUtils.getIconResourceForFileType(currentFile.getType());

        holder.ivItemFile.setImageResource(resId);
        holder.tvItemFileName.setText(currentFile.getName());
        holder.tvItemFileTimeAndSize.
                setText(DocumentUtils.getFileTimeAndSize(currentFile.getCreatedTime() * 1000,
                        currentFile.getSize()));
        holder.ivIsFavorite.setImageResource(currentFile.getIsFavorite() == 0 ?
                R.drawable.ic_star : R.drawable.ic_star_on);

        holder.ivIsFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFavoriteStatus(currentFile, holder);
            }
        });

        holder.ibItemMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.showBottomSheetDialog(context, currentFile);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "您点击了 " + currentFile.getName() + " 文件", Toast.LENGTH_SHORT)
                        .show();
                toggleLastedTime(currentFile, holder);
            }
        });
    }

    @Override
    public int getItemCount() {
        return documentInfoList.size();
    }

    //切换收藏状态
    private void toggleFavoriteStatus(DocumentInfo documentInfo, ViewHolder holder) {
        ThreadPoolManager.getInstance().executeSingle(new Runnable() {
            @Override
            public void run() {
                documentInfoDao.updateIsFavorite(documentInfo.getId(),
                        1 - documentInfo.getIsFavorite());

                // 在主线程中更新UI
                ((Activity) holder.itemView.getContext()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        documentInfo.setIsFavorite(1 - documentInfo.getIsFavorite()); // 更新文件对象的收藏状态
                        holder.ivIsFavorite.setImageResource(documentInfo.getIsFavorite() == 0 ?
                                R.drawable.ic_star : R.drawable.ic_star_on);
                        EventBusUtils.post(new EventBusMessage<>(RequestCodeConstants.REQUEST_REFRESH,
                                ArrangementHelper.getViewSettings()));
                    }
                });
            }
        });
    }

    private void toggleLastedTime(DocumentInfo documentInfo, ViewHolder holder) {
        ThreadPoolManager.getInstance().executeSingle(new Runnable() {
            @Override
            public void run() {
                Long newLastedTime = System.currentTimeMillis();
                documentInfoDao.updateLastedTime(documentInfo.getId(), newLastedTime);
                ((Activity) holder.itemView.getContext()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        documentInfo.setLatestTime(newLastedTime);
                        EventBusUtils.post(new EventBusMessage<>(RequestCodeConstants.REQUEST_REFRESH,
                                ArrangementHelper.getViewSettings()));
                    }
                });
            }
        });
    }


    public interface OnShowFileDetailsBottomSheetDialogListener {
        void showBottomSheetDialog(Context context, DocumentInfo currentFile);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivItemFile, ivIsFavorite;
        TextView tvItemFileName, tvItemFileTimeAndSize;
        ImageButton ibItemMore;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivItemFile = itemView.findViewById(R.id.iv_item_file);
            ivIsFavorite = itemView.findViewById(R.id.iv_is_favorite);
            tvItemFileName = itemView.findViewById(R.id.tv_item_file_name);
            tvItemFileTimeAndSize = itemView.findViewById(R.id.tv_item_file_time_and_size);
            ibItemMore = itemView.findViewById(R.id.ib_item_more);
        }
    }
}