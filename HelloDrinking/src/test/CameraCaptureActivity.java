package test;

import kr.android.hellodrinking.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/*
 * 출처 : http://curv.tistory.com/148
 */
public class CameraCaptureActivity extends Activity {
	private static final int CAMERA_PIC_REQUEST = 1337;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cameracapture);

		Button btnPhoto = (Button) findViewById(R.id.btnButton);
		btnPhoto.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
			}
		});
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAMERA_PIC_REQUEST) {
			if (data != null) {
				Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
				if (thumbnail != null) {
					ImageView image = (ImageView) findViewById(R.id.imgView);
					image.setImageBitmap(thumbnail);
				}
			}
		}
	}
}
