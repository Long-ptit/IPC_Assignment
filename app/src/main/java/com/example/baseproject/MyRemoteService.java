package com.example.baseproject;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyRemoteService extends Service {
    private static final String TAG = "MyLog";
    int numberProduct;
    private HandlerThread mHandlerThread;
    private Handler mHandler;
    private ExecutorService mExecutorService = Executors.newSingleThreadExecutor();
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
        numberProduct = 0;
        mHandlerThread = new HandlerThread("Request");
         mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());
        initProduction();
    }

    private void initProduction() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (numberProduct < 10) {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        numberProduct++;
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
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
            Log.d(TAG, "send basis type: " + aString);
        }

        @Override
        public void sendParcel(ICallBack callback) throws RemoteException {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        Log.d(TAG, "run: " + numberProduct);
                        if (numberProduct > 0) {
                            numberProduct--;
                            try {
                                Log.d(TAG, "run thanh cong ");
                                DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                                Date date = new Date();
                                String time = "[" + dateFormat.format(date) + "]";
                                callback.onFinish(getDateNow() + " Success, goods in inventory: " + numberProduct);
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
