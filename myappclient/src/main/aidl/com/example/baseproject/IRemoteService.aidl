// IRemoteService.aidl
package com.example.baseproject;
import com.example.baseproject.ICallBack;
// Declare any non-default types here with import statements

interface IRemoteService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
     int getPid();

            oneway void sendProduct( ICallBack callback);
}