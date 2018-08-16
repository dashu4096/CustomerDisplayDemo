package com.paydevice.smartpos.customerdisplaydemo.presentation;

import android.app.Presentation;
import android.content.Context;
import android.os.Bundle;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.Display;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.util.Log;
import java.io.IOException;
import com.paydevice.smartpos.customerdisplaydemo.R;

public class VideoPresentation extends Presentation {
	private static final String TAG = "VideoPresentation";

	private SurfaceView mSurface;
	private SurfaceHolder mHolder;
	private MediaPlayer mPlayer;
	private Context mContext;

    public VideoPresentation(Context context, Display display, int theme) {
        super(context, display, theme);
		mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.videoview);

		mSurface = (SurfaceView)findViewById(R.id.surface_view);
		mHolder = mSurface.getHolder();
		mHolder.setKeepScreenOn(true);
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		mHolder.addCallback(new SurfaceHolder.Callback() {
			@Override  
			public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {  
				if (mPlayer != null) {
					mPlayer.setDisplay(holder);
				}
			}

			@Override  
			public void surfaceCreated(SurfaceHolder holder) {
				try {  
					mPlayer = new MediaPlayer();
					mPlayer.setDataSource(mContext, Uri.parse("android.resource://" + mContext.getPackageName() + "/" + R.raw.vid_bigbuckbunny));
					mPlayer.setDisplay(holder);
					mPlayer.setLooping(true);
					mPlayer.prepare();
					mPlayer.start();
				} catch (IllegalArgumentException e) {  
					// TODO Auto-generated catch block  
					e.printStackTrace();  
				} catch (SecurityException e) {  
					// TODO Auto-generated catch block  
					e.printStackTrace();  
				} catch (IllegalStateException e) {  
					// TODO Auto-generated catch block  
					e.printStackTrace();  
				} catch (IOException e) {  
					// TODO Auto-generated catch block  
					e.printStackTrace();
				}
			}

			@Override  
			public void surfaceDestroyed(SurfaceHolder holder) {  
				if (mPlayer.isPlaying()) {
					mPlayer.stop();
				}
				mPlayer.release();
				mPlayer = null;
			}  
		});
	}

	@Override
	public void show() {
		super.show();
	}

	@Override
	public void dismiss() {
		super.dismiss();
	}

	@Override
	public void onDisplayRemoved() {
		Log.d(TAG, "Secondary display has removed!");
		dismiss();
	}

	@Override
	public void onDisplayChanged() {
		Log.d(TAG, "Secondary display has changed!");
	}

}
