package com.example.subiect1e;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities={DataPackage.class}, version = 2, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class DataPackageDB extends RoomDatabase {
    private final static String DBNAME="packages.db";
    private static DataPackageDB instance;

    public static DataPackageDB getInstance(Context context) {
        if(instance==null)
            instance= Room.databaseBuilder(context,DataPackageDB.class,DBNAME).allowMainThreadQueries().fallbackToDestructiveMigration().build();
        return instance;
    }
    public abstract DataPackageDao getDaoPackages();
}
