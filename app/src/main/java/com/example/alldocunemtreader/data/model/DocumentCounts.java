package com.example.alldocunemtreader.data.model;

import java.io.Serializable;

/**
 * Author: Eccentric
 * Created on 2024/6/25 11:42.
 * Description: com.example.alldocunemtreader.data.model.DocumentCounts
 */
public class DocumentCounts implements Serializable {
    private int allDocumentCount;
    private int allPDFCount;
    private int allDOCCount;
    private int allXLSCount;
    private int allPPTCount;
    private int allTXTCount;
    private int allOTHERCount;

    public DocumentCounts() {
    }

    public DocumentCounts(int allDocumentCount,
                          int allPDFCount,
                          int allDOCCount,
                          int allXLSCount,
                          int allPPTCount,
                          int allTXTCount,
                          int allOTHERCount) {
        this.allDocumentCount = allDocumentCount;
        this.allPDFCount = allPDFCount;
        this.allDOCCount = allDOCCount;
        this.allXLSCount = allXLSCount;
        this.allPPTCount = allPPTCount;
        this.allTXTCount = allTXTCount;
        this.allOTHERCount = allOTHERCount;
    }

    public int getAllDocumentCount() {
        return allDocumentCount;
    }

    public void setAllDocumentCount(int allDocumentCount) {
        this.allDocumentCount = allDocumentCount;
    }

    public int getAllPDFCount() {
        return allPDFCount;
    }

    public void setAllPDFCount(int allPDFCount) {
        this.allPDFCount = allPDFCount;
    }

    public int getAllDOCCount() {
        return allDOCCount;
    }

    public void setAllDOCCount(int allDOCCount) {
        this.allDOCCount = allDOCCount;
    }

    public int getAllXLSCount() {
        return allXLSCount;
    }

    public void setAllXLSCount(int allXLSCount) {
        this.allXLSCount = allXLSCount;
    }

    public int getAllPPTCount() {
        return allPPTCount;
    }

    public void setAllPPTCount(int allPPTCount) {
        this.allPPTCount = allPPTCount;
    }

    public int getAllTXTCount() {
        return allTXTCount;
    }

    public void setAllTXTCount(int allTXTCount) {
        this.allTXTCount = allTXTCount;
    }

    public int getAllOTHERCount() {
        return allOTHERCount;
    }

    public void setAllOTHERCount(int allOTHERCount) {
        this.allOTHERCount = allOTHERCount;
    }
}
