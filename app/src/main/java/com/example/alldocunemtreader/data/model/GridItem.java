package com.example.alldocunemtreader.data.model;

/**
 * Author: Eccentric
 * Created on 2024/6/24 13:33.
 * Description: com.example.alldocunemtreader.data.model.GridItem
 */
public class GridItem {
    private int imageResource;
    private String text1;
    private String text2;
    private String text3;

    public GridItem(int imageResource, String text1, String text2, String text3) {
        this.imageResource = imageResource;
        this.text1 = text1;
        this.text2 = text2;
        this.text3 = text3;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public String getText3() {
        return text3;
    }

    public void setText3(String text3) {
        this.text3 = text3;
    }
}
