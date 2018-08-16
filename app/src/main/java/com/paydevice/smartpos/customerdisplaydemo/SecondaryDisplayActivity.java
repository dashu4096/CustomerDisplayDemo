package com.paydevice.smartpos.customerdisplaydemo;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.util.Log;

import com.paydevice.smartpos.customerdisplaydemo.SecondaryDisplayService.CustomerListener;

public class SecondaryDisplayActivity extends Activity {

	private static final String TAG = "SecondaryDisplayActivity";


	private Button mVideoBtn;
	private Button mPhotoBtn;
	private Button mTextBtn;
	private Button mCustomerBtn;

	private boolean mVideoShowing = false;
	private boolean mPhotoShowing = false;
	private boolean mTextShowing = false;
	private boolean mCustomerShowing = false;

	private SecondaryDisplayService mService = null;
	private ServiceConnection mConn = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mService = ((SecondaryDisplayService.MsgBinder)service).getService();
			mService.setCustomerListener(new CustomerListener() {
				@Override
				public void onConfirm(String input) {
					Log.d(TAG,"Customer input:"+input);
				}
			});
			//refresh UI
			if (mService.isVideoPlaying()) {
				Log.d(TAG,"video playing");
				mVideoShowing = true;
				mVideoBtn.setText(R.string.stop_video);
				mPhotoBtn.setEnabled(false);
				mTextBtn.setEnabled(false);
				mCustomerBtn.setEnabled(false);
			} else if (mService.isPhotoPlaying()) {
				Log.d(TAG,"photo playing");
				mPhotoShowing = true;
				mPhotoBtn.setText(R.string.stop_photo);
				mVideoBtn.setEnabled(false);
				mTextBtn.setEnabled(false);
				mCustomerBtn.setEnabled(false);
			} else if (mService.isTextPlaying()) {
				Log.d(TAG,"text playing");
				mTextShowing = true;
				mTextBtn.setText(R.string.stop_text);
				mPhotoBtn.setEnabled(false);
				mVideoBtn.setEnabled(false);
				mCustomerBtn.setEnabled(false);
			} else if (mService.isCustomerPlaying()) {
				Log.d(TAG,"customer playing");
				mCustomerShowing = true;
				mCustomerBtn.setText(R.string.stop_customer);
				mPhotoBtn.setEnabled(false);
				mVideoBtn.setEnabled(false);
				mTextBtn.setEnabled(false);
			}

		}
		@Override
		public void onServiceDisconnected(ComponentName name) {
			if (mService != null) {
				mService.setCustomerListener(null);
				mService = null;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_secondarydisplay);

		//bind service
		Intent intent = new Intent(SecondaryDisplayActivity.this, SecondaryDisplayService.class);
		startService(intent);
		bindService(intent, mConn, Service.BIND_AUTO_CREATE);


		mVideoBtn = (Button) this.findViewById(R.id.video_btn);
		mVideoBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg) {
				if (!mVideoShowing) {
					mVideoShowing = true;
					mVideoBtn.setText(R.string.stop_video);
					mPhotoBtn.setEnabled(false);
					mTextBtn.setEnabled(false);
					mCustomerBtn.setEnabled(false);
					if (mService != null) {
						mService.play(SecondaryDisplayService.TYPE_VIDEO);
					}
				} else {
					if (mService != null) {
						mService.stop();
					}
					mVideoShowing = false;
					mVideoBtn.setText(R.string.start_video);
					mPhotoBtn.setEnabled(true);
					mTextBtn.setEnabled(true);
					mCustomerBtn.setEnabled(true);
				}
			}
		});

		mPhotoBtn = (Button) this.findViewById(R.id.photo_btn);
		mPhotoBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg) {
				if (!mPhotoShowing) {
					mPhotoShowing = true;
					mPhotoBtn.setText(R.string.stop_photo);
					mVideoBtn.setEnabled(false);
					mTextBtn.setEnabled(false);
					mCustomerBtn.setEnabled(false);
					if (mService != null) {
						mService.play(SecondaryDisplayService.TYPE_PHOTO);
					}
				} else {
					if (mService != null) {
						mService.stop();
					}
					mPhotoShowing = false;
					mPhotoBtn.setText(R.string.start_photo);
					mVideoBtn.setEnabled(true);
					mTextBtn.setEnabled(true);
					mCustomerBtn.setEnabled(true);
				}
			}
		});

		mTextBtn = (Button) this.findViewById(R.id.text_btn);
		mTextBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg) {
				if (!mTextShowing) {
					mTextShowing = true;
					mTextBtn.setText(R.string.stop_text);
					mVideoBtn.setEnabled(false);
					mPhotoBtn.setEnabled(false);
					mCustomerBtn.setEnabled(false);
					if (mService != null) {
						mService.play(SecondaryDisplayService.TYPE_TEXT);
					}
				} else {
					if (mService != null) {
						mService.stop();
					}
					mTextShowing = false;
					mTextBtn.setText(R.string.start_text);
					mVideoBtn.setEnabled(true);
					mPhotoBtn.setEnabled(true);
					mCustomerBtn.setEnabled(true);
				}
			}
		});

		mCustomerBtn = (Button) this.findViewById(R.id.customer_btn);
		mCustomerBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg) {
				if (!mCustomerShowing) {
					mCustomerShowing = true;
					mCustomerBtn.setText(R.string.stop_customer);
					mPhotoBtn.setEnabled(false);
					mVideoBtn.setEnabled(false);
					mTextBtn.setEnabled(false);
					if (mService != null) {
						mService.play(SecondaryDisplayService.TYPE_INPUT);
					}
				} else {
					if (mService != null) {
						mService.stop();
					}
					mCustomerShowing = false;
					mCustomerBtn.setText(R.string.start_customer);
					mPhotoBtn.setEnabled(true);
					mVideoBtn.setEnabled(true);
					mTextBtn.setEnabled(true);
				}
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG,"onResume()");
	}

    @Override
    protected void onPause() {
        super.onPause();
	}
    @Override
    protected void onDestroy() {
        super.onDestroy();
		if (mService != null) {
			unbindService(mConn);
		}
	}

}
