package com.paydevice.smartpos.customerdisplaydemo;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRouter;
import android.os.Binder;
import android.os.IBinder;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;
import android.util.Log;

import com.paydevice.smartpos.customerdisplaydemo.presentation.VideoPresentation;
import com.paydevice.smartpos.customerdisplaydemo.presentation.PhotoPresentation;
import com.paydevice.smartpos.customerdisplaydemo.presentation.TextPresentation;
import com.paydevice.smartpos.customerdisplaydemo.presentation.CustomerPresentation;
import com.paydevice.smartpos.customerdisplaydemo.presentation.CustomerPresentation.CustomerInputListener;

public class SecondaryDisplayService extends Service {

	private static final String TAG = "SecondaryDisplayService";

	public static final int TYPE_VIDEO = 1;
	public static final int TYPE_PHOTO = 2;
	public static final int TYPE_TEXT  = 3;
	public static final int TYPE_INPUT = 4;

	CustomerListener mCustomerListener = null;

	private boolean mVideoPlaying = false;
	private boolean mPhotoPlaying = false;
	private boolean mTextPlaying = false;
	private boolean mCustomerPlaying = false;

    private MediaRouter mMediaRouter;

    private VideoPresentation mVideoPresentation;
    private PhotoPresentation mPhotoPresentation;
    private TextPresentation  mTextPresentation;
    private CustomerPresentation mCustomerPresentation;

	private final MsgBinder mBinder = new MsgBinder();
	public class MsgBinder extends Binder {
		public SecondaryDisplayService getService() {
			return SecondaryDisplayService.this;
		}
	} 


	@Override
	public void onCreate() {
        mMediaRouter = (MediaRouter)getSystemService(Context.MEDIA_ROUTER_SERVICE);
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
		if (intent != null) {
			Log.d(TAG,"Received start id " + startId + ": " + intent.toString());
		}
		return START_REDELIVER_INTENT;
	}

	@Override
	public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
	    return mBinder;
	}

	@Override
	public void onRebind(Intent intent) {
        Log.d(TAG, "onReBind");
	}

	@Override
	public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnBind");
	    return true;
	}


	public void play(int type) {
		stop();
		MediaRouter.RouteInfo route = mMediaRouter.getSelectedRoute(MediaRouter.ROUTE_TYPE_LIVE_VIDEO);
		if (route != null) {
			Display secondaryDisplay = route.getPresentationDisplay();
			if (secondaryDisplay != null) {
				switch(type) {
					case TYPE_VIDEO:
						Log.d(TAG,"Star play video!");
						if (mVideoPresentation == null) {
							mVideoPresentation = new VideoPresentation(getApplicationContext(), secondaryDisplay, R.style.SecondaryDisplay);
							mVideoPresentation.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
						}
						mVideoPresentation.show();
						mVideoPlaying = true;
						break;

					case TYPE_PHOTO:
						Log.d(TAG,"Star play photo!");
						if (mPhotoPresentation == null) {
							mPhotoPresentation = new PhotoPresentation(getApplicationContext(), secondaryDisplay, R.style.SecondaryDisplay);
							mPhotoPresentation.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
						}
						mPhotoPresentation.show();
						mPhotoPlaying = true;
						break;

					case TYPE_TEXT:
						Log.d(TAG,"Star play text!");
						if (mTextPresentation == null) {
							mTextPresentation = new TextPresentation(getApplicationContext(), secondaryDisplay, R.style.SecondaryDisplay);
							mTextPresentation.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
						}
						mTextPresentation.show();
						mTextPlaying = true;
						String amount = "100";
						String change = "10";
						mTextPresentation.setData(amount, change);
						break;

					case TYPE_INPUT:
						Log.d(TAG,"Star play input!");
						if (mCustomerPresentation == null) {
							mCustomerPresentation = new CustomerPresentation(getApplicationContext(), secondaryDisplay, R.style.SecondaryDisplay, new CustomerInputListener() {
								@Override
								public void onConfirm(String input) {
									String str = getString(R.string.str_get_input) + input;
									Toast.makeText(SecondaryDisplayService.this, str, Toast.LENGTH_SHORT).show();
									if (mCustomerListener != null) {
										mCustomerListener.onConfirm(input);
									}
								}
							});
							mCustomerPresentation.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
						}
						mCustomerPresentation.show();
						mCustomerPlaying = true;
						break;
				}
			} else {
				Log.d(TAG,"Have not secondary display!");
				Toast.makeText(SecondaryDisplayService.this, R.string.no_secondary_display, Toast.LENGTH_SHORT).show();
			}
		}
	}

	public void stop() {
		if (mVideoPresentation != null) {
			Log.d(TAG,"Stop play video!");
			mVideoPresentation.dismiss();
			mVideoPlaying = false;
			mVideoPresentation = null;
		}
		if (mPhotoPresentation != null) {
			Log.d(TAG,"Stop play photo!");
			mPhotoPresentation.dismiss();
			mPhotoPlaying = false;
			mPhotoPresentation = null;
		}
		if (mTextPresentation != null) {
			Log.d(TAG,"Stop play text!");
			mTextPresentation.dismiss();
			mTextPlaying = false;
			mTextPresentation = null;
		}
		if (mCustomerPresentation != null) {
			Log.d(TAG,"Stop play input!");
			mCustomerPresentation.dismiss();
			mCustomerPlaying = false;
			mCustomerPresentation = null;
		}
	}

	public boolean isVideoPlaying() {
		return mVideoPlaying;
	}

	public boolean isPhotoPlaying() {
		return mPhotoPlaying;
	}

	public boolean isTextPlaying() {
		return mTextPlaying;
	}

	public boolean isCustomerPlaying() {
		return mCustomerPlaying;
	}

	public void setCustomerListener(CustomerListener l) {
		this.mCustomerListener = l; 
	}

	public interface CustomerListener {
		void onConfirm(String input);
	}

}
