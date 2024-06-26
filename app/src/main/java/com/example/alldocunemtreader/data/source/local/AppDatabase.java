package com.example.alldocunemtreader.data.source.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.alldocunemtreader.data.model.DocumentInfo;

/**
 * Author: Eccentric
 * Created on 2024/6/21 18:04.
 * Description: com.example.alldocunemtreader.data.source.local.AppDatabase
 */
@Database(entities = {DocumentInfo.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DocumentInfoDao documentInfoDao();

    private volatile static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "my_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
