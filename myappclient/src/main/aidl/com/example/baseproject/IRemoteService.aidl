// IRemoteService.aidl
package com.example.baseproject;
import com.example.baseproject.MyParcel;
import com.example.baseproject.ICallBack;
// Declare any non-default types here with import statements

interface IRemoteService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
     int getPid();
         void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

            oneway void sendParcel(in MyParcel parcel, ICallBack callback);
}