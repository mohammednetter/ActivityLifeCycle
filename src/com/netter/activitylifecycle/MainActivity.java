package com.netter.activitylifecycle;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	public static final int DIALOG_SAMPLE = 1;
	AlertDialog.Builder sampleDialog = null;
	int count = 0;
	
	public class CounterClass implements Runnable {
		@Override
		public void run() {
			for (;;) {
				try {
					Thread.sleep(1000);
					count++;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	TextView count_disp = null;
	Thread stopThread, pauseThread;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// RESET COUNT ON CREATE
		count = 0;
		count_disp = (TextView) findViewById(R.id.textViewCounter);

		// CREATE THREADS
		CounterClass stopCount = new CounterClass();
		CounterClass pauseCount = new CounterClass();
		stopThread = new Thread(stopCount);
		pauseThread = new Thread(pauseCount);

		// BUTTON START B
		Button btnStartB = (Button) findViewById(R.id.buttonStartB);
		btnStartB.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent activityB = new Intent(MainActivity.this,
						Activity_B.class);
				startActivity(activityB);
			}
		});

		// BUTTON CLOSE APP
		Button btnCloseApp = (Button) findViewById(R.id.buttonCloseApp);
		btnCloseApp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		// BUTTON DIALOG Button btnDailog = (Button)
		Button btnDialog = (Button) findViewById(R.id.buttonDialog);
		btnDialog.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				showDialog(DIALOG_SAMPLE);
			}
		});

	}

	protected Dialog onCreateDialog(int id) {

		switch (id) {
		case DIALOG_SAMPLE:
			sampleDialog = new AlertDialog.Builder(MainActivity.this);
			sampleDialog
					.setMessage("Sample Dialog")
					.setPositiveButton("Close",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
								}
							}).show();
		}
		return super.onCreateDialog(id);
	}

	@SuppressWarnings("deprecation")
	protected void onResume() {
		super.onResume();
		count_disp.setText(String.valueOf(count));
	}

	protected void onStop() {
		super.onStop();
		// START THREAD
		if (!stopThread.isAlive())
			stopThread.start();
	}

	protected void onPause() {
		super.onPause();
		// START THREAD
		if (!pauseThread.isAlive())
			pauseThread.start();
	}

}
