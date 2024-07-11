package com.example.alldocunemtreader.ui.common.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alldocunemtreader.R;
import com.example.alldocunemtreader.constants.RequestCodeConstants;
import com.example.alldocunemtreader.data.model.EventBusMessage;
import com.example.alldocunemtreader.data.model.FileItem;
import com.example.alldocunemtreader.utils.EventBusUtils;

import java.util.List;

/**
 * Author: Eccentric
 * Created on 2024/7/10 17:51.
 * Description: com.example.alldocunemtreader.ui.common.adapter.FileItemAdapter
 */
public class FileItemAdapter extends RecyclerView.Adapter<FileItemAdapter.ViewHolder> {
    private final List<FileItem> fileItemList;
    private final FileItemClickListener listener;
    private FileItem currentParent;

    public FileItemAdapter(List<FileItem> fileItemList, FileItemClickListener listener) {
        this.fileItemList = fileItemList;
        this.listener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateAdapter(List<FileItem> newFileItemList, FileItem newParent) {
        this.fileItemList.clear();
        this.fileItemList.addAll(newFileItemList);
        this.currentParent = newParent;
        if (fileItemList.isEmpty()) {
            EventBusUtils.post(new EventBusMessage<>(RequestCodeConstants.REQUEST_NO_RESULTS, null));
        }else {
            EventBusUtils.post(new EventBusMessage<>(RequestCodeConstants.REQUEST_HAS_RESULTS, null));
        }
        notifyDataSetChanged();
    }

    public FileItem getCurrentParent() {
        return this.currentParent;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_file_item, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FileItem currentFileItem = fileItemList.get(position);
        holder.tvFileName.setText(currentFileItem.getName());
        holder.ivFileItem.setImageResource(currentFileItem.getIconId());
    }


    @Override
    public int getItemCount() {
        return fileItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivFileItem;
        private final TextView tvFileName;

        public ViewHolder(@NonNull View itemView, FileItemClickListener listener) {
            super(itemView);
            ivFileItem = itemView.findViewById(R.id.iv_file_item);
            tvFileName = itemView.findViewById(R.id.tv_file_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onFileItemClick(fileItemList.get(getAdapterPosition()));
                }
            });
        }

    }

    public interface FileItemClickListener {
        void onFileItemClick(FileItem fileItem);
    }
}
