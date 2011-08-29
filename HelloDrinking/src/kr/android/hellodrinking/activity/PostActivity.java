package kr.android.hellodrinking.activity;

import java.io.File;

import kr.android.hellodrinking.HelloDrinkingApplication;
import kr.android.hellodrinking.R;
import kr.android.hellodrinking.transmission.Request;
import kr.android.hellodrinking.transmission.dto.ResponceBeanPackege;
import kr.android.hellodrinking.utillity.GraphicUtils;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class PostActivity extends Activity implements OnClickListener {
	private static final int CAMERA_PIC_REQUEST = 1337;

	private Button mButtonSend;
	private Button mButtonTakePhoto;

	private EditText mEditId;
	private EditText mEditComment;
	private ImageView mImagePhoto;

	private double longitude;
	private double latitude;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.post);

		longitude = getIntent().getDoubleExtra("kr.android.hellodrinking.MYLOCATION_LONGITUDE", 0);
		latitude = getIntent().getDoubleExtra("kr.android.hellodrinking.MYLOCATION_LATITUDE", 0);

		mEditId = (EditText) findViewById(R.id.post_edit_id);
		mEditComment = (EditText) findViewById(R.id.post_edit_comment);
		mImagePhoto = (ImageView) findViewById(R.id.post_image);
		mButtonSend = (Button) findViewById(R.id.post_button_send);
		mButtonTakePhoto = (Button) findViewById(R.id.post_button_takephoto);
		mButtonSend.setOnClickListener(this);
		mButtonTakePhoto.setOnClickListener(this);

		mEditId.setText(((HelloDrinkingApplication) getApplication()).getId());
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAMERA_PIC_REQUEST) {
			if (data != null) {
				Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
				if (thumbnail != null) {
					mImagePhoto.setImageBitmap(thumbnail);
				}
			}
		}
	}

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.post_button_takephoto) {
			Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
		} else if (view.getId() == R.id.post_button_send) {
			String id = mEditId.getText().toString().trim();
			String comment = mEditComment.getText().toString().trim();
			String imageFilePath = null;

			File file = GraphicUtils.createTempImageFile(this, mImagePhoto.getDrawable());
			imageFilePath = file.getAbsolutePath();

			Request request = new Request(HelloDrinkingApplication.mServerIp, HelloDrinkingApplication.mServerPort);
			ResponceBeanPackege responce = null;
			try {
				responce = request.post(id, comment, imageFilePath, longitude, latitude);
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(this, "인터넷 연결이 바르지 않습니다.", Toast.LENGTH_SHORT).show();
			} finally {
				request.close();
			}
			if (responce.isSuccessed()) {
				finish();
			} else {
				Toast.makeText(this, responce.getException().getMessage(), Toast.LENGTH_LONG).show();
			}
		}
	}
}
