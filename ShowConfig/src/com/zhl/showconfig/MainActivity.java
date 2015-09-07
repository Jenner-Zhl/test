package com.zhl.showconfig;

import java.util.Date;
import java.util.UUID;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

	private String TAG = "zhl";
	TextView tv;
	TextView tv_acc;
	SensorManager sm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv = (TextView) findViewById(R.id.text);
		tv_acc = (TextView) findViewById(R.id.acc);
		
		String extStorage = Environment.getExternalStorageDirectory().getPath();
		
//		File[] dirs = getExternalFilesDirs("adb");
//		Log.d(TAG, extStorage + "\n" + getFilesDir());
//		for(int i=getExternalFilesDirs(Environment.DIRECTORY_DOWNLOADS).length-1; i>=0; i--) {
//			if (dirs[i] != null) {
//				Log.d(TAG, i
//						+ ":"
//						+ dirs[i].getParentFile().getParentFile()
//								.getParentFile().getParentFile()
//								.getParentFile());
//			}
//		}
		Log.d(TAG, "onCreate end" + getResources().getDisplayMetrics().density);
		Log.d(TAG, DateFormat.getTimeFormat(this).format(new Date()));
		

				initSensor();

	}
	
	private void initSensor() {
		sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		sm.registerListener(new SensorEventListener() {
			
			@Override
			public void onSensorChanged(SensorEvent event) {
				if(Sensor.TYPE_ACCELEROMETER == event.sensor.getType()) {
					tv_acc.setText("x:\t" + event.values[0] + "\n"
							+ "y:\t" + event.values[1] + "\n"
							+ "z:\t" + event.values[2] );
				}
				
			}
			
			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy) {
				// TODO Auto-generated method stub
				
			}
		}, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
		
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		Point outSize = new Point(0,0);
		Display disp = getWindowManager().getDefaultDisplay();
		disp.getSize(outSize);
		Configuration con = getResources().getConfiguration();
		
		long maxMem = Runtime.getRuntime().maxMemory();
		long freeMem = Runtime.getRuntime().freeMemory();
		long totalMem = Runtime.getRuntime().totalMemory();
		
		tv.append("\ndensityDpi:           " + con.densityDpi);
		tv.append("\nlocale:               " + con.locale);
		tv.append("\nheightDP:             " + con.screenHeightDp);
		tv.append("\nwidthDp:              " + con.screenWidthDp);
		tv.append("\nheightPixel:          " + outSize.y);
		tv.append("\nwidthPixel:           " + outSize.x);
		tv.append("\nmax memory:           " + maxMem);
		tv.append("\nfree memory:          " + freeMem);
		tv.append("\ntotal memory:         " + totalMem);
		
		
		if(event == null) {
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onBackPressed() {
		onKeyUp(0, null);
	}
	
	

}
