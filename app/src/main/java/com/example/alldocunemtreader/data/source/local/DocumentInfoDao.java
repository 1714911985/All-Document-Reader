package com.example.alldocunemtreader.data.source.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.alldocunemtreader.data.model.DocumentInfo;

import java.util.List;

/**
 * Author: Eccentric
 * Created on 2024/6/21 18:04.
 * Description: com.example.alldocunemtreader.data.source.local.DocumentInfoDao
 */
@Dao
public interface DocumentInfoDao {
    @Insert
    void insert(DocumentInfo documentInfo);

    @Query("UPDATE documents SET name = :name, size = :sizeLong, type = :type, " +
            "createdTime = :dateAddedLong, changedTime = :dateModifiedLong, " +
            "isDelete = :isDelete WHERE path = :pathStr")
    void update(String name, String pathStr, long sizeLong,
                String type, long dateAddedLong, long dateModifiedLong, int isDelete);

    @Query("DELETE FROM documents WHERE isDelete = :isDelete")
    void delete(int isDelete);

    @Query("SELECT * FROM documents where path = :path")
    DocumentInfo queryDocumentByPath(String path);

    @Query("SELECT COUNT(*) FROM documents")
    int getAllDocumentCount();

    @Query("SELECT COUNT(*) FROM documents WHERE type = 'PDF'")
    int getAllPDFCount();

    @Query("SELECT COUNT(*) FROM documents WHERE type = 'PPT'")
    int getAllPPTCount();

    @Query("SELECT COUNT(*) FROM documents WHERE type = 'DOC'")
    int getAllDOCCount();

    @Query("SELECT COUNT(*) FROM documents WHERE type = 'XLS'")
    int getAllXLSCount();

    @Query("SELECT COUNT(*) FROM documents WHERE type = 'TXT'")
    int getAllTXTCount();

    @Query("SELECT COUNT(*) FROM documents WHERE type = 'OTHER'")
    int getAllOTHERCount();

    @Query("SELECT * FROM documents")
    List<DocumentInfo> getAllDocuments();

    @Query("SELECT * FROM documents WHERE name LIKE '%'|| :fuzzyFileName|| '%'")
    List<DocumentInfo> fuzzyGetDocuments(String fuzzyFileName);

    @Query("UPDATE documents SET isFavorite = :isFavorite WHERE id = :id")
    void updateIsFavorite(int id, int isFavorite);

    @Query("UPDATE documents SET name = :newFileName WHERE id = :id")
    void updateFileName(int id, String newFileName);
    @Query("UPDATE documents SET latestTime = :newLastedTime WHERE id = :id")
    void updateLastedTime(int id, Long newLastedTime);

    //    -----------------getALLDocumentInfoSortBy---------------------
    @Query("SELECT * FROM documents ORDER BY name ASC")
    List<DocumentInfo> getALLDocumentInfoSortByFileNameAscending();

    @Query("SELECT * FROM documents ORDER BY name DESC")
    List<DocumentInfo> getALLDocumentInfoSortByFileNameDescending();

    @Query("SELECT * FROM documents ORDER BY createdTime ASC")
    List<DocumentInfo> getALLDocumentInfoSortByCreatedTimeAscending();

    @Query("SELECT * FROM documents ORDER BY createdTime DESC")
    List<DocumentInfo> getALLDocumentInfoSortByCreatedTimeDescending();

    @Query("SELECT * FROM documents ORDER BY type ASC")
    List<DocumentInfo> getALLDocumentInfoSortByFileTypeAscending();

    @Query("SELECT * FROM documents ORDER BY type DESC")
    List<DocumentInfo> getALLDocumentInfoSortByFileTypeDescending();

    @Query("SELECT * FROM documents ORDER BY size ASC")
    List<DocumentInfo> getALLDocumentInfoSortByFileSizeAscending();

    @Query("SELECT * FROM documents ORDER BY size DESC")
    List<DocumentInfo> getALLDocumentInfoSortByFileSizeDescending();

    //    -----------------getPPTDocumentInfoSortBy---------------------
    @Query("SELECT * FROM documents WHERE type = 'PPT' ORDER BY name ASC")
    List<DocumentInfo> getPPTDocumentInfoSortByFileNameAscending();

    @Query("SELECT * FROM documents WHERE type = 'PPT' ORDER BY name DESC")
    List<DocumentInfo> getPPTDocumentInfoSortByFileNameDescending();

    @Query("SELECT * FROM documents WHERE type = 'PPT' ORDER BY createdTime ASC")
    List<DocumentInfo> getPPTDocumentInfoSortByCreatedTimeAscending();

    @Query("SELECT * FROM documents WHERE type = 'PPT' ORDER BY createdTime DESC")
    List<DocumentInfo> getPPTDocumentInfoSortByCreatedTimeDescending();

    @Query("SELECT * FROM documents WHERE type = 'PPT' ORDER BY type ASC")
    List<DocumentInfo> getPPTDocumentInfoSortByFileTypeAscending();

    @Query("SELECT * FROM documents WHERE type = 'PPT' ORDER BY type DESC")
    List<DocumentInfo> getPPTDocumentInfoSortByFileTypeDescending();

    @Query("SELECT * FROM documents WHERE type = 'PPT' ORDER BY size ASC")
    List<DocumentInfo> getPPTDocumentInfoSortByFileSizeAscending();

    @Query("SELECT * FROM documents WHERE type = 'PPT' ORDER BY size DESC")
    List<DocumentInfo> getPPTDocumentInfoSortByFileSizeDescending();

    //    -----------------getDOCDocumentInfoSortBy---------------------
    @Query("SELECT * FROM documents WHERE type = 'DOC' ORDER BY name ASC")
    List<DocumentInfo> getDOCDocumentInfoSortByFileNameAscending();

    @Query("SELECT * FROM documents WHERE type = 'DOC' ORDER BY name DESC")
    List<DocumentInfo> getDOCDocumentInfoSortByFileNameDescending();

    @Query("SELECT * FROM documents WHERE type = 'DOC' ORDER BY createdTime ASC")
    List<DocumentInfo> getDOCDocumentInfoSortByCreatedTimeAscending();

    @Query("SELECT * FROM documents WHERE type = 'DOC' ORDER BY createdTime DESC")
    List<DocumentInfo> getDOCDocumentInfoSortByCreatedTimeDescending();

    @Query("SELECT * FROM documents WHERE type = 'DOC' ORDER BY type ASC")
    List<DocumentInfo> getDOCDocumentInfoSortByFileTypeAscending();

    @Query("SELECT * FROM documents WHERE type = 'DOC' ORDER BY type DESC")
    List<DocumentInfo> getDOCDocumentInfoSortByFileTypeDescending();

    @Query("SELECT * FROM documents WHERE type = 'DOC' ORDER BY size ASC")
    List<DocumentInfo> getDOCDocumentInfoSortByFileSizeAscending();

    @Query("SELECT * FROM documents WHERE type = 'DOC' ORDER BY size DESC")
    List<DocumentInfo> getDOCDocumentInfoSortByFileSizeDescending();

    //    -----------------getXLSDocumentInfoSortBy---------------------
    @Query("SELECT * FROM documents WHERE type = 'XLS' ORDER BY name ASC")
    List<DocumentInfo> getXLSDocumentInfoSortByFileNameAscending();

    @Query("SELECT * FROM documents WHERE type = 'XLS' ORDER BY name DESC")
    List<DocumentInfo> getXLSDocumentInfoSortByFileNameDescending();

    @Query("SELECT * FROM documents WHERE type = 'XLS' ORDER BY createdTime ASC")
    List<DocumentInfo> getXLSDocumentInfoSortByCreatedTimeAscending();

    @Query("SELECT * FROM documents WHERE type = 'XLS' ORDER BY createdTime DESC")
    List<DocumentInfo> getXLSDocumentInfoSortByCreatedTimeDescending();

    @Query("SELECT * FROM documents WHERE type = 'XLS' ORDER BY type ASC")
    List<DocumentInfo> getXLSDocumentInfoSortByFileTypeAscending();

    @Query("SELECT * FROM documents WHERE type = 'XLS' ORDER BY type DESC")
    List<DocumentInfo> getXLSDocumentInfoSortByFileTypeDescending();

    @Query("SELECT * FROM documents WHERE type = 'XLS' ORDER BY size ASC")
    List<DocumentInfo> getXLSDocumentInfoSortByFileSizeAscending();

    @Query("SELECT * FROM documents WHERE type = 'XLS' ORDER BY size DESC")
    List<DocumentInfo> getXLSDocumentInfoSortByFileSizeDescending();

    //    -----------------getPDFDocumentInfoSortBy---------------------
    @Query("SELECT * FROM documents WHERE type = 'PDF' ORDER BY name ASC")
    List<DocumentInfo> getPDFDocumentInfoSortByFileNameAscending();

    @Query("SELECT * FROM documents WHERE type = 'PDF' ORDER BY name DESC")
    List<DocumentInfo> getPDFDocumentInfoSortByFileNameDescending();

    @Query("SELECT * FROM documents WHERE type = 'PDF' ORDER BY createdTime ASC")
    List<DocumentInfo> getPDFDocumentInfoSortByCreatedTimeAscending();

    @Query("SELECT * FROM documents WHERE type = 'PDF' ORDER BY createdTime DESC")
    List<DocumentInfo> getPDFDocumentInfoSortByCreatedTimeDescending();

    @Query("SELECT * FROM documents WHERE type = 'PDF' ORDER BY type ASC")
    List<DocumentInfo> getPDFDocumentInfoSortByFileTypeAscending();

    @Query("SELECT * FROM documents WHERE type = 'PDF' ORDER BY type DESC")
    List<DocumentInfo> getPDFDocumentInfoSortByFileTypeDescending();

    @Query("SELECT * FROM documents WHERE type = 'PDF' ORDER BY size ASC")
    List<DocumentInfo> getPDFDocumentInfoSortByFileSizeAscending();

    @Query("SELECT * FROM documents WHERE type = 'PDF' ORDER BY size DESC")
    List<DocumentInfo> getPDFDocumentInfoSortByFileSizeDescending();

    //    -----------------getTXTDocumentInfoSortBy---------------------
    @Query("SELECT * FROM documents WHERE type = 'TXT' ORDER BY name ASC")
    List<DocumentInfo> getTXTDocumentInfoSortByFileNameAscending();

    @Query("SELECT * FROM documents WHERE type = 'TXT' ORDER BY name DESC")
    List<DocumentInfo> getTXTDocumentInfoSortByFileNameDescending();

    @Query("SELECT * FROM documents WHERE type = 'TXT' ORDER BY createdTime ASC")
    List<DocumentInfo> getTXTDocumentInfoSortByCreatedTimeAscending();

    @Query("SELECT * FROM documents WHERE type = 'TXT' ORDER BY createdTime DESC")
    List<DocumentInfo> getTXTDocumentInfoSortByCreatedTimeDescending();

    @Query("SELECT * FROM documents WHERE type = 'TXT' ORDER BY type ASC")
    List<DocumentInfo> getTXTDocumentInfoSortByFileTypeAscending();

    @Query("SELECT * FROM documents WHERE type = 'TXT' ORDER BY type DESC")
    List<DocumentInfo> getTXTDocumentInfoSortByFileTypeDescending();

    @Query("SELECT * FROM documents WHERE type = 'TXT' ORDER BY size ASC")
    List<DocumentInfo> getTXTDocumentInfoSortByFileSizeAscending();

    @Query("SELECT * FROM documents WHERE type = 'TXT' ORDER BY size DESC")
    List<DocumentInfo> getTXTDocumentInfoSortByFileSizeDescending();

    //    -----------------getOTHERDocumentInfoSortBy---------------------
    @Query("SELECT * FROM documents WHERE type = 'OTHER' ORDER BY name ASC")
    List<DocumentInfo> getOTHERDocumentInfoSortByFileNameAscending();

    @Query("SELECT * FROM documents WHERE type = 'OTHER' ORDER BY name DESC")
    List<DocumentInfo> getOTHERDocumentInfoSortByFileNameDescending();

    @Query("SELECT * FROM documents WHERE type = 'OTHER' ORDER BY createdTime ASC")
    List<DocumentInfo> getOTHERDocumentInfoSortByCreatedTimeAscending();

    @Query("SELECT * FROM documents WHERE type = 'OTHER' ORDER BY createdTime DESC")
    List<DocumentInfo> getOTHERDocumentInfoSortByCreatedTimeDescending();

    @Query("SELECT * FROM documents WHERE type = 'OTHER' ORDER BY type ASC")
    List<DocumentInfo> getOTHERDocumentInfoSortByFileTypeAscending();

    @Query("SELECT * FROM documents WHERE type = 'OTHER' ORDER BY type DESC")
    List<DocumentInfo> getOTHERDocumentInfoSortByFileTypeDescending();

    @Query("SELECT * FROM documents WHERE type = 'OTHER' ORDER BY size ASC")
    List<DocumentInfo> getOTHERDocumentInfoSortByFileSizeAscending();

    @Query("SELECT * FROM documents WHERE type = 'OTHER' ORDER BY size DESC")
    List<DocumentInfo> getOTHERDocumentInfoSortByFileSizeDescending();

    //    -----------------getIsFavoriteDocumentInfoSortBy---------------------
    @Query("SELECT * FROM documents WHERE isFavorite = 1 ORDER BY name ASC")
    List<DocumentInfo> getFavoriteDocumentInfoSortByFileNameAscending();

    @Query("SELECT * FROM documents WHERE isFavorite = 1 ORDER BY name DESC")
    List<DocumentInfo> getFavoriteDocumentInfoSortByFileNameDescending();

    @Query("SELECT * FROM documents WHERE isFavorite = 1 ORDER BY createdTime ASC")
    List<DocumentInfo> getFavoriteDocumentInfoSortByCreatedTimeAscending();

    @Query("SELECT * FROM documents WHERE isFavorite = 1 ORDER BY createdTime DESC")
    List<DocumentInfo> getFavoriteDocumentInfoSortByCreatedTimeDescending();

    @Query("SELECT * FROM documents WHERE isFavorite = 1 ORDER BY type ASC")
    List<DocumentInfo> getFavoriteDocumentInfoSortByFileTypeAscending();

    @Query("SELECT * FROM documents WHERE isFavorite = 1 ORDER BY type DESC")
    List<DocumentInfo> getFavoriteDocumentInfoSortByFileTypeDescending();

    @Query("SELECT * FROM documents WHERE isFavorite = 1 ORDER BY size ASC")
    List<DocumentInfo> getFavoriteDocumentInfoSortByFileSizeAscending();

    @Query("SELECT * FROM documents WHERE isFavorite = 1 ORDER BY size DESC")
    List<DocumentInfo> getFavoriteDocumentInfoSortByFileSizeDescending();

    //    -----------------getRecentDocumentInfoSortBy---------------------
    @Query("SELECT * FROM documents WHERE latestTime != 0 ORDER BY name ASC")
    List<DocumentInfo> getRecentDocumentInfoSortByFileNameAscending();

    @Query("SELECT * FROM documents WHERE latestTime != 0 ORDER BY name DESC")
    List<DocumentInfo> getRecentDocumentInfoSortByFileNameDescending();

    @Query("SELECT * FROM documents WHERE latestTime != 0 ORDER BY createdTime ASC")
    List<DocumentInfo> getRecentDocumentInfoSortByCreatedTimeAscending();

    @Query("SELECT * FROM documents WHERE latestTime != 0 ORDER BY createdTime DESC")
    List<DocumentInfo> getRecentDocumentInfoSortByCreatedTimeDescending();

    @Query("SELECT * FROM documents WHERE latestTime != 0 ORDER BY type ASC")
    List<DocumentInfo> getRecentDocumentInfoSortByFileTypeAscending();

    @Query("SELECT * FROM documents WHERE latestTime != 0 ORDER BY type DESC")
    List<DocumentInfo> getRecentDocumentInfoSortByFileTypeDescending();

    @Query("SELECT * FROM documents WHERE latestTime != 0 ORDER BY size ASC")
    List<DocumentInfo> getRecentDocumentInfoSortByFileSizeAscending();

    @Query("SELECT * FROM documents WHERE latestTime != 0 ORDER BY size DESC")
    List<DocumentInfo> getRecentDocumentInfoSortByFileSizeDescending();

    @Query("DELETE FROM documents WHERE id = :id")
    void deleteById(int id);
}
