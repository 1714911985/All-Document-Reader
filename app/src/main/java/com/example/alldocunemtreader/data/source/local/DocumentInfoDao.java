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

    @Query("SELECT * FROM documents")
    List<DocumentInfo> getAllDocuments();

    @Query("UPDATE documents SET isFavorite = :isFavorite WHERE id = :id")
    void updateIsFavorite(int id, int isFavorite);

    @Query("UPDATE documents SET name = :newFileName, path = :newFilePath WHERE id = :id")
    void updateFileNameAndFilePath(int id, String newFileName, String newFilePath);

    @Query("UPDATE documents SET latestTime = :newLastedTime WHERE id = :id")
    void updateLastedTime(int id, Long newLastedTime);

    @Query("DELETE FROM documents WHERE id = :id")
    void deleteById(int id);
}
