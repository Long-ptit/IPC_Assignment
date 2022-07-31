package com.example.baseproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

	private static final String TAG = "MyLog";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

	}

	@Override
	protected void onResume() {
		super.onResume();
		String pkgName = "com.example.baseproject";
		String serviceName = pkgName + ".MyRemoteService";

//		Intent intent = new Intent();
//		intent.setComponent(new ComponentName(pkgName, serviceName));
//		intent.putExtra("msg", "kaka");
//		if ( startService(intent) == null) {
//			Log.d(TAG, "null: ");
//		}
	}
}