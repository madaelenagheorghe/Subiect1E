package com.example.subiect1e;

import androidx.room.TypeConverter;

import java.util.Date;

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value){
        return value==null ? null : new Date(value);
    } //converts a date object to a long one

    @TypeConverter
    public static Long dateToTimestamp(Date date){
        return date==null ? null : date.getTime();
    } //converts a long object to a date one

    @TypeConverter
    public static DataPackage.packageType fromPackageType(String value){
        return value==null ? null : DataPackage.packageType.valueOf(value);
    }

    @TypeConverter
    public static String stringToPackageType (DataPackage.packageType packageType){
        return packageType==null ? null : packageType.name();
    }
}
