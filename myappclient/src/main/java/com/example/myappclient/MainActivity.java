package com.example.myappclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.baseproject.ICallBack;
import com.example.baseproject.IRemoteService;
import com.example.baseproject.MyParcel;

import java.sql.Connection;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MyLog";
    private IRemoteService iRemoteService;

    private ICallBack callBack = new ICallBack.Stub() {
        @Override
        public void onFinish(String aString) throws RemoteException {
            Log.d(TAG, "onFinish: " + aString);
        }

    };
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iRemoteService = IRemoteService.Stub.asInterface(service);

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }

        @Override
        public void onBindingDied(ComponentName name) {
            ServiceConnection.super.onBindingDied(name);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.btn_buy);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    iRemoteService.sendParcel(callBack);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        String pkgName = "com.example.baseproject";
        String serviceName = pkgName + ".MyRemoteService";

        Intent intent = new Intent();
        intent.setComponent(new ComponentName(pkgName, serviceName));
        intent.putExtra("msg", "Trinh Xuan Long");
        if ( startService(intent) == null) {
            Log.d(TAG, "null create service: ");
        }

        Intent intent1 = new Intent();
        intent1.setPackage(pkgName);
        intent1.setClassName(pkgName, serviceName);

        if (!bindService(intent1, mConnection, Context.BIND_AUTO_CREATE)) {
            Log.d(TAG, "null bind: ");
        }
    }
}