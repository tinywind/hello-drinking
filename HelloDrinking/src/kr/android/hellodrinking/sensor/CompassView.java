package kr.android.hellodrinking.sensor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.AttributeSet;
import android.widget.ImageButton;

public class CompassView extends ImageButton implements SensorEventListener {

	private Bitmap mBitmapBackground;
	private Bitmap mBitmapOrient;
	private float[] mValues;

	public CompassView(Context context) {
		super(context);
	}

	public CompassView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CompassView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setCompass(Compass compass) {
		compass.addListener(this);
	}

	public void setBackgroundBitmap(Bitmap bitmap) {
		mBitmapBackground = Bitmap.createScaledBitmap(bitmap, getLayoutParams().width, getLayoutParams().height, true);
		setBackgroundDrawable(new BitmapDrawable(mBitmapBackground));
	}

	public void setOrientationBitmap(Bitmap bitmap) {
		mBitmapOrient = Bitmap.createScaledBitmap(bitmap, getLayoutParams().width, getLayoutParams().height, true);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (mValues != null) {
			Paint paint = new Paint();
			paint.setStyle(Paint.Style.FILL);
			paint.setColor(Color.RED);
			paint.setStrokeWidth(10);

			int degree = (int) mValues[0];
			if (degree > 360)
				degree -= 360;

			canvas.drawText(degree + "º", 10, 10, paint);

			canvas.rotate(mValues[0], getLayoutParams().width / 2, getLayoutParams().height / 2);

			if (mBitmapOrient != null) {
				canvas.drawBitmap(mBitmapOrient, 0, 0, null);
			}
		}
		super.onDraw(canvas);
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		mValues = event.values;
		invalidate();
	}

	@Override
	public void onAccuracyChanged(Sensor paramSensor, int paramInt) {
	}
}
