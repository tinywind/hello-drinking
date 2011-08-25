package kr.android.hellodrinking.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OptionalDataException;

import kr.android.hellodrinking.HelloDrinkingApplication;
import kr.android.hellodrinking.R;
import kr.android.hellodrinking.transmission.Request;
import kr.android.hellodrinking.transmission.dto.ResponceBeanPackege;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MemberJoinActivity extends Activity implements OnClickListener {
	private static final int CAMERA_PIC_REQUEST = 1337;

	private Button mButtonTakePhoto;
	private Button mButtonTakeFile;
	private Button mButtonSend;
	private EditText mEditId;
	private EditText mEditPw;
	private EditText mEditPw2;
	private EditText mEditName;
	private EditText mEditAge;
	private EditText mEditSex;
	private EditText mEditPhone;
	private EditText mEditJob;
	private ImageView mImagePhoto;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.memberjoin);

		mButtonTakeFile = (Button) findViewById(R.id.memberjoin_button_takefile);
		mButtonTakePhoto = (Button) findViewById(R.id.memberjoin_button_takephoto);
		mButtonSend = (Button) findViewById(R.id.memberjoin_button_send);

		mEditId = (EditText) findViewById(R.id.memberjoin_edit_id);
		mEditPw = (EditText) findViewById(R.id.memberjoin_edit_pw);
		mEditPw2 = (EditText) findViewById(R.id.memberjoin_edit_pw2);
		mEditName = (EditText) findViewById(R.id.memberjoin_edit_name);
		mEditAge = (EditText) findViewById(R.id.memberjoin_edit_age);
		mEditSex = (EditText) findViewById(R.id.memberjoin_edit_sex);
		mEditPhone = (EditText) findViewById(R.id.memberjoin_edit_phone);
		mEditJob = (EditText) findViewById(R.id.memberjoin_edit_job);

		mImagePhoto = (ImageView) findViewById(R.id.memberjoin_image);

		mButtonTakePhoto.setOnClickListener(this);
		mButtonSend.setOnClickListener(this);
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
		if (view.getId() == R.id.memberjoin_button_takefile) {
			// 갤러리 부르기
		} else if (view.getId() == R.id.memberjoin_button_takephoto) {
			Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
		} else if (view.getId() == R.id.memberjoin_button_send) {
			String id = mEditId.getText().toString().trim();
			String password = mEditPw.getText().toString().trim();
			String password2 = mEditPw2.getText().toString().trim();
			String name = mEditName.getText().toString().trim();
			String age = mEditAge.getText().toString().trim();
			String sex = mEditSex.getText().toString().trim();
			String phone = mEditPhone.getText().toString().trim();
			String job = mEditJob.getText().toString().trim();
			String imageFilePath = null;

			if (id.length() < 1) {
				Toast.makeText(this, "ID가 비어 있습니다.", Toast.LENGTH_SHORT).show();
				mEditId.requestFocus();
				return;
			}
			if (password.length() < 1) {
				Toast.makeText(this, "Password가 비어 있습니다.", Toast.LENGTH_SHORT).show();
				mEditPw.requestFocus();
				return;
			}
			if (!password.equals(password2)) {
				Toast.makeText(this, "Password가 잘못 입력되었습니다.", Toast.LENGTH_SHORT).show();
				mEditPw.setText("");
				mEditPw2.setText("");
				return;
			}

			File cacheDir = getCacheDir();
			try {
				if (!cacheDir.isDirectory() || !cacheDir.exists())
					cacheDir.mkdir();
			} catch (Exception e) {
				Toast.makeText(this, "Create Folder Error about Cache Directory", Toast.LENGTH_SHORT).show();
			}
			try {
				File file = new File(getCacheDir(),"photo.jpg"); 
				if(file.exists()){
					file.delete();
					file.createNewFile();
				}
				FileOutputStream fos = new FileOutputStream(file); 
				
				Drawable drawable = mImagePhoto.getDrawable();
				Rect rect = drawable.getBounds();
				
				Bitmap bitmap = Bitmap.createBitmap(rect.right, rect.bottom, Bitmap.Config.ARGB_8888);
				Canvas canvas = new Canvas(bitmap);
				drawable.setBounds(0, 0, rect.right, rect.bottom);
				drawable.draw(canvas);
				
				bitmap.compress(CompressFormat.JPEG, 80 , fos); 
				fos.flush(); 
				fos.close();

				imageFilePath = file.getAbsolutePath();
			} catch (Exception e) {
				Toast.makeText(this, "Save Error about Image File", Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}

			Request request = new Request(HelloDrinkingApplication.DEFAULT_SERVER, HelloDrinkingApplication.DEFAULT_PORT);
			ResponceBeanPackege responce = null;
			try {
				responce = request.register(id, name, password, age, sex, phone, job, imageFilePath);
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(this, "인터넷 연결이 바르지 않습니다.", Toast.LENGTH_SHORT).show();
			}
			request.close();
			if (responce.isSuccessed()) {
				((HelloDrinkingApplication) getApplication()).setId(id);
				Intent intent = new Intent(this, PostsActivity.class);
				startActivity(intent);
			} else {
				Toast.makeText(this, responce.getException().getMessage(), Toast.LENGTH_LONG).show();
			}
		}
	}
}