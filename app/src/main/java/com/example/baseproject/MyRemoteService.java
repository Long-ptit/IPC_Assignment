package com.example.baseproject;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyRemoteService extends Service {
    private static final String TAG = "MyLog";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return remoteService.asBinder();
    }

    //    private I
    private IRemoteService remoteService = new IRemoteService.Stub() {


        @Override
        public int getPid() {
            return Process.myPid();
        }

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
            Log.d(TAG, "send basis type: " + aString);
        }

        @Override
        public void sendParcel(MyParcel parcel, ICallBack callback) throws RemoteException {
            Log.d(TAG, "send Parcel: " + parcel.aString);
            callback.onFinish();
        }

    };


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getStringExtra("msg") != null) {
            String data = intent.getStringExtra("msg");
            Log.d(TAG, "onStartCommand: " + data);
        }
        return START_NOT_STICKY;
    }
}
