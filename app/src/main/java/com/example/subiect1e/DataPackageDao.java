package com.example.subiect1e;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;

import java.util.List;

@Dao
public interface DataPackageDao {
    @Insert
    public void insert(DataPackage package1);

    @Query("SELECT * FROM packages")
    List<DataPackage> getPackages();

    @Query("DELETE FROM packages")
    public void deleteTable();
}
