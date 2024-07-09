package com.example.alldocunemtreader.ui.common.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
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
 * Created on 2024/6/25 15:33.
 * Description: com.example.alldocunemtreader.ui.common.adapter.RecycleGridAdapter
 */
public class RecycleGridAdapter extends RecyclerView.Adapter<RecycleGridAdapter.ViewHolder> {
    private Context context;
    private List<DocumentInfo> documentInfoList;
    private DocumentInfoDao documentInfoDao;
    private RecycleListAdapter.OnShowFileDetailsBottomSheetDialogListener listener;

    public RecycleGridAdapter(Context context, List<DocumentInfo> documentInfoList, RecycleListAdapter.OnShowFileDetailsBottomSheetDialogListener listener) {
        this.context = context;
        this.listener = listener;
        this.documentInfoList = documentInfoList;
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        documentInfoDao = appDatabase.documentInfoDao();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_grid_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DocumentInfo currentFile = documentInfoList.get(position); // 获取当前项的文件对象

        // 根据文件类型拿到对应的背景颜色
        int colorId = DocumentUtils.getBackgroundColorForFileType(currentFile.getType());
        int resId = DocumentUtils.getIconResourceForFileType(currentFile.getType());

        holder.ivItemFile.setImageResource(resId);
        holder.tvItemFileName.setText(currentFile.getName());
        holder.tvItemFileName.setSelected(true);
        holder.tvItemFileTimeAndSize.setText(DocumentUtils
                .getFileTimeAndSize(currentFile.getCreatedTime() * 1000, currentFile.getSize()));
        holder.ivIsFavorite.setImageResource(currentFile.getIsFavorite() == 0 ?
                R.drawable.ic_star : R.drawable.ic_star_on);
        holder.viewBackground.setBackground(new GradientDrawable() {{
            setColor(ContextCompat.getColor(context, colorId));
            setCornerRadius(5f);
        }});

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
                Toast.makeText(context, "您点击了 " + currentFile.getName() + " 文件",
                        Toast.LENGTH_SHORT).show();
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

    //修改最近打开时间
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
        View viewBackground;
        ImageButton ibItemMore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivItemFile = itemView.findViewById(R.id.iv_item_file);
            ivIsFavorite = itemView.findViewById(R.id.iv_is_favorite);
            tvItemFileName = itemView.findViewById(R.id.tv_item_file_name);
            tvItemFileTimeAndSize = itemView.findViewById(R.id.tv_item_file_time_and_size);
            viewBackground = itemView.findViewById(R.id.v_background);
            ibItemMore = itemView.findViewById(R.id.ib_item_more);
        }
    }
}
