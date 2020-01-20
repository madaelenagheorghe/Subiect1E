package com.example.subiect1e;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;
@Entity(tableName = "packages")

public class DataPackage implements Serializable  {

    public enum packageType{
        position, state
    }
    @PrimaryKey (autoGenerate = true)
    private int packageId;
    private packageType packageType;
    private float latitude;
    private float longitude;
    private Date timestamp;

    public DataPackage(){

    }
    @Ignore
    public DataPackage(int packageId, DataPackage.packageType packageType, float latitude, float longitude, Date timestamp) {
        this.packageId = packageId;
        this.packageType = packageType;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public DataPackage.packageType getPackageType() {
        return packageType;
    }

    public void setPackageType(DataPackage.packageType packageType) {
        this.packageType = packageType;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return
                "packageId=" + packageId +
                ", packageType=" + packageType +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", timestamp=" + timestamp;
    }
}
