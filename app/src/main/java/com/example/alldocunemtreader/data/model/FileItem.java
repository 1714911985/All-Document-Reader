package com.example.alldocunemtreader.data.model;

import java.util.List;

/**
 * Author: Eccentric
 * Created on 2024/7/10 17:18.
 * Description: com.example.alldocunemtreader.data.model.FileItem
 */
public class FileItem {
    private String name;
    private Integer iconId;
    private String type;
    private FileItem parent;
    private List<FileItem> children;

    public FileItem() {
    }

    public FileItem(String name, Integer iconId, String type, FileItem parent, List<FileItem> children) {
        this.name = name;
        this.iconId = iconId;
        this.type = type;
        this.parent = parent;
        this.children = children;
    }

    @Override
    public String toString() {
        return "FileItem{" +
                "name='" + name + '\'' +
                ", iconId=" + iconId +
                ", type='" + type + '\'' +
                ", parent=" + parent +
                ", children=" + children +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIconId() {
        return iconId;
    }

    public void setIconId(Integer iconId) {
        this.iconId = iconId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public FileItem getParent() {
        return parent;
    }

    public void setParent(FileItem parent) {
        this.parent = parent;
    }

    public List<FileItem> getChildren() {
        return children;
    }

    public void setChildren(List<FileItem> children) {
        this.children = children;
    }
}
