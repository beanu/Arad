package com.google.zxing;

import android.graphics.Bitmap;

/**
 * 自定义处理结果
 * 
 * @ClassName: MyCaptureActivity
 * @Description: TODOS
 * @author Beanu
 */
public abstract class MyCaptureActivity extends CaptureActivity {

	public abstract void handleResult(String resultString);

	/**
	 * @param result
	 * @param barcode
	 */
	@Override
	public void handleDecode(Result result, Bitmap barcode) {
		inactivityTimer.onActivity();
		playBeepSoundAndVibrate();

		final String resultString = result.getText();
		// Intent resultIntent = new Intent();
		// Bundle bundle = new Bundle();
		// bundle.putString("result", resultString);
		// resultIntent.putExtras(bundle);
		// this.setResult(RESULT_OK, resultIntent);

		handleResult(resultString);
	}
}
