package com.example.alldocunemtreader.data.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Author: Eccentric
 * Created on 2024/6/21 17:59.
 * Description: 文档基本信息类
 */
@Entity(tableName = "documents")
public class DocumentInfo {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String path;
    private long size;
    private String type;
    private long createdTime;
    private long changedTime;
    private long latestTime;
    private int isFavorite;
    private int isDelete;

    public DocumentInfo() {
    }

    @Ignore
    public DocumentInfo(String name,
                        String path,
                        long size,
                        String type,
                        long createdTime,
                        long changedTime,
                        long latestTime,
                        int isFavorite,
                        int isDelete) {
        this.name = name;
        this.path = path;
        this.size = size;
        this.type = type;
        this.createdTime = createdTime;
        this.changedTime = changedTime;
        this.latestTime = latestTime;
        this.isFavorite = isFavorite;
        this.isDelete = isDelete;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public long getChangedTime() {
        return changedTime;
    }

    public void setChangedTime(long changedTime) {
        this.changedTime = changedTime;
    }

    public long getLatestTime() {
        return latestTime;
    }

    public void setLatestTime(long latestTime) {
        this.latestTime = latestTime;
    }

    public int getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(int isFavorite) {
        this.isFavorite = isFavorite;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    @Ignore
    public String toString() {
        return "DocumentInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", size=" + size +
                ", type='" + type + '\'' +
                ", createdTime=" + createdTime +
                ", changedTime=" + changedTime +
                ", latestTime=" + latestTime +
                ", isFavorite=" + isFavorite +
                ", isDelete=" + isDelete +
                '}';
    }
}
