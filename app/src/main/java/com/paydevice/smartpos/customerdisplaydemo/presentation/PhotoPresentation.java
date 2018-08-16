package com.paydevice.smartpos.customerdisplaydemo.presentation;

import android.app.Presentation;
import android.content.Context;
import android.os.Bundle;
import android.view.animation.AnimationUtils; 
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewFlipper;
import android.util.Log;
import com.paydevice.smartpos.customerdisplaydemo.R;

public class PhotoPresentation extends Presentation {
	private static final String TAG = "PhotoPresentation";

	private ViewFlipper mFlipper;
	private Context mContext;

    public PhotoPresentation(Context context, Display display, int theme) {
        super(context, display, theme);
		mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.photoview);

		mFlipper = (ViewFlipper)findViewById(R.id.flipper);
		mFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, android.R.anim.fade_in));
		mFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, android.R.anim.fade_out));
		mFlipper.setFlipInterval(2000);
		mFlipper.setAutoStart(true);

		mFlipper.addView(addImageById(R.drawable.sun));
		mFlipper.addView(addImageById(R.drawable.mercury));
		mFlipper.addView(addImageById(R.drawable.venus));
		mFlipper.addView(addImageById(R.drawable.earth));
		mFlipper.addView(addImageById(R.drawable.mars));
		mFlipper.addView(addImageById(R.drawable.jupiter));
		mFlipper.addView(addImageById(R.drawable.saturn));
		mFlipper.addView(addImageById(R.drawable.uranus));
		mFlipper.addView(addImageById(R.drawable.neptune));
		mFlipper.addView(addImageById(R.drawable.pluto));
    }

	@Override
	public void show() {
		super.show();
		mFlipper.startFlipping();
	}

	@Override
	public void dismiss() {
		if (mFlipper.isFlipping()) {
			mFlipper.stopFlipping();
		}
		Log.d(TAG, "dismiss");
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

	private View addImageById(int id) {
		ImageView img = new ImageView(mContext);
		img.setImageResource(id);
		return img;
	}
}
