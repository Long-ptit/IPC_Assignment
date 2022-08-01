package com.example.baseproject;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyRemoteService extends Service {
    private static final String TAG = "MyLog";
    private int mNumberProduct;
    private ExecutorService mExecutorService = Executors.newSingleThreadExecutor();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
        mNumberProduct = 0;
        initProduction();
    }

    private void initProduction() {
        Log.d(TAG, "initProduction: ");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (mNumberProduct < 10) {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mNumberProduct++;
                    }
                }
            }
        });
        thread.start();
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
        public void sendProduct(ICallBack callback) throws RemoteException {
            mExecutorService.execute(new Runnable() {
                @Override
                public void run() {
                    if (mNumberProduct == 0) {
                        try {
                            callback.onWaiting("Product in production");
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                    while (true) {
                        Log.d(TAG, "run: " + mNumberProduct);
                        if (mNumberProduct > 0) {
                            mNumberProduct--;
                            try {
                                Log.d(TAG, "run success ");
                                callback.onFinish(getDateNow() + " Success, goods in inventory: " + mNumberProduct);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                    }
                }
            });
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

    private String getDateNow() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        String time = "[" + dateFormat.format(date) + "]:";
        return time;
    }
}
