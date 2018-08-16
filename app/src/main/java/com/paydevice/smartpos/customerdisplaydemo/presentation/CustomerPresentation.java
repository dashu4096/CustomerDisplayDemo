package com.paydevice.smartpos.customerdisplaydemo.presentation;

import android.app.Presentation;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;
import com.paydevice.smartpos.customerdisplaydemo.R;

public class CustomerPresentation extends Presentation {
	private static final String TAG = "CustomerPresentation";

	private Button mBtn1;
	private Button mBtn2;
	private Button mBtn3;
	private Button mBtn4;
	private Button mBtn5;
	private Button mBtn6;
	private Button mBtn7;
	private Button mBtn8;
	private Button mBtn9;
	private Button mBtn0;
	private Button mDelBtn;
	private Button mOkBtn;

	private String mNumStr = "";
	private EditText mInput;
	private CustomerInputListener mInputListener = null;
	private BtnListener mBtnListener = null;

	private class BtnListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			int id = v.getId();
			switch (id) {
				case R.id.btn_0:
					mNumStr += 0;
				break;
				case R.id.btn_1:
					mNumStr += 1;
				break;
				case R.id.btn_2:
					mNumStr += 2;
				break;
				case R.id.btn_3:
					mNumStr += 3;
				break;
				case R.id.btn_4:
					mNumStr += 4;
				break;
				case R.id.btn_5:
					mNumStr += 5;
				break;
				case R.id.btn_6:
					mNumStr += 6;
				break;
				case R.id.btn_7:
					mNumStr += 7;
				break;
				case R.id.btn_8:
					mNumStr += 8;
				break;
				case R.id.btn_9:
					mNumStr += 9;
				break;
				case R.id.btn_del:
					if(mNumStr.length() > 0) {
						mNumStr = mNumStr.substring(0, mNumStr.length() - 1);
					}
				break;
				case R.id.btn_ok:
					Log.d(TAG,"Secondary display --ok btn--");
					if (mInputListener != null) {
						mInputListener.onConfirm(mInput.getText().toString());
						return;
					}
				break;
			}
			mInput.setText(mNumStr);
			mInput.setSelection(null != mNumStr ? mNumStr.length() : 0);
		}
	}

    public CustomerPresentation(Context context, Display display, CustomerInputListener listener) {
        super(context, display);
		mInputListener = listener;
    }
    public CustomerPresentation(Context context, Display display, int theme, CustomerInputListener listener) {
        super(context, display, theme);
		mInputListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.customerview);

		mBtn0 = (Button)findViewById(R.id.btn_0);
		mBtn1 = (Button)findViewById(R.id.btn_1);
		mBtn2 = (Button)findViewById(R.id.btn_2);
		mBtn3 = (Button)findViewById(R.id.btn_3);
		mBtn4 = (Button)findViewById(R.id.btn_4);
		mBtn5 = (Button)findViewById(R.id.btn_5);
		mBtn6 = (Button)findViewById(R.id.btn_6);
		mBtn7 = (Button)findViewById(R.id.btn_7);
		mBtn8 = (Button)findViewById(R.id.btn_8);
		mBtn9 = (Button)findViewById(R.id.btn_9);
		mDelBtn = (Button)findViewById(R.id.btn_del);
		mOkBtn = (Button)findViewById(R.id.btn_ok);
		mBtnListener = new BtnListener();
		mBtn0.setOnClickListener(mBtnListener);
		mBtn1.setOnClickListener(mBtnListener);
		mBtn2.setOnClickListener(mBtnListener);
		mBtn3.setOnClickListener(mBtnListener);
		mBtn4.setOnClickListener(mBtnListener);
		mBtn5.setOnClickListener(mBtnListener);
		mBtn6.setOnClickListener(mBtnListener);
		mBtn7.setOnClickListener(mBtnListener);
		mBtn8.setOnClickListener(mBtnListener);
		mBtn9.setOnClickListener(mBtnListener);
		mDelBtn.setOnClickListener(mBtnListener);
		mOkBtn.setOnClickListener(mBtnListener);

		mInput = (EditText)findViewById(R.id.text);
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

	public interface CustomerInputListener {
		void onConfirm(String input);
	}
}

