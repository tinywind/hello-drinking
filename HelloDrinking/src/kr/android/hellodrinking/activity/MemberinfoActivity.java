package kr.android.hellodrinking.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import kr.android.hellodrinking.HelloDrinkingApplication;
import kr.android.hellodrinking.R;
import kr.android.hellodrinking.transmission.Request;
import kr.android.hellodrinking.transmission.dto.ResponceBeanPackege;
import kr.android.hellodrinking.transmission.dto.UserBean;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MemberinfoActivity extends FrameActivity implements OnClickListener {
	private static final int CAMERA_PIC_REQUEST = 1337;

	private Button mButtonSend;
	private Button mButtonTakeFile;
	private Button mButtonTakePhoto;

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
	protected void loadContent() {
		mInflater.inflate(R.layout.memberinfo, mViewgroup);
		mButtonMember.setClickable(false);
		// setContentView(R.layout.memberinfo);

		mEditAge = (EditText) findViewById(R.id.memberinfo_edit_age);
		mEditId = (EditText) findViewById(R.id.memberinfo_edit_id);
		mEditJob = (EditText) findViewById(R.id.memberinfo_edit_job);
		mEditName = (EditText) findViewById(R.id.memberinfo_edit_name);
		mEditPhone = (EditText) findViewById(R.id.memberinfo_edit_phone);
		mEditPw = (EditText) findViewById(R.id.memberinfo_edit_pw);
		mEditPw2 = (EditText) findViewById(R.id.memberinfo_edit_pw2);
		mEditSex = (EditText) findViewById(R.id.memberinfo_edit_sex);
		mImagePhoto = (ImageView) findViewById(R.id.memberinfo_image);
		mButtonSend = (Button) findViewById(R.id.memberinfo_button_send);
		mButtonTakeFile = (Button) findViewById(R.id.memberinfo_button_takefile);
		mButtonTakePhoto = (Button) findViewById(R.id.memberinfo_button_takephoto);
		mButtonSend.setOnClickListener(this);
		mButtonTakeFile.setOnClickListener(this);
		mButtonTakePhoto.setOnClickListener(this);

		// 불러오기
		Request request = new Request(HelloDrinkingApplication.DEFAULT_SERVER, HelloDrinkingApplication.DEFAULT_PORT);
		ResponceBeanPackege responce = null;
		try {
			responce = request.getUser(((HelloDrinkingApplication) getApplication()).getId());
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(this, "인터넷 연결이 바르지 않습니다.", Toast.LENGTH_SHORT).show();
		} finally {
			request.close();
		}

		if (responce == null) {
			Toast.makeText(this, "통신이 원할하지 않습니다.", Toast.LENGTH_SHORT).show();
			onBackPressed();
		}
		if (responce.isSuccessed()) {
			UserBean user = (UserBean) responce.getObject();
			mEditAge.setText(user.getAge());
			mEditId.setText(user.getId());
			mEditJob.setText(user.getJob());
			mEditName.setText(user.getName());
			mEditPhone.setText(user.getPhone());
			mEditSex.setText(user.getSex());

			try {
				File cacheDir = getCacheDir();
				try {
					if (!cacheDir.isDirectory() || !cacheDir.exists())
						cacheDir.mkdir();
				} catch (Exception e) {
					Toast.makeText(this, "Create Folder Error about Cache Directory", Toast.LENGTH_SHORT).show();
				}

				File file = new File(user.getImageFilePath());
				if (file.getName().equals(""))
					return;

				String path = file.getName();
				int index = path.lastIndexOf('\\');
				String name = path.substring(index + 1);

				File imagefile = new File(cacheDir, name);
				FileOutputStream fileWriter = null;

				try {
					fileWriter = new FileOutputStream(imagefile);
					fileWriter.write(user.getBuffer());
					fileWriter.flush();
				} catch (IOException e) {
					e.printStackTrace();
					if (imagefile.exists())
						imagefile.delete();
					return;
				} finally {
					try {
						fileWriter.close();
					} catch (IOException e) {
						System.err.println(e.getMessage());
					}
				}

				FileInputStream fis = new FileInputStream(imagefile);
				Bitmap bitmap = BitmapFactory.decodeStream(fis);
				mImagePhoto.setImageBitmap(bitmap);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		} else {
			Toast.makeText(this, responce.getException().getMessage(), Toast.LENGTH_SHORT).show();
		}
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
		if (view.getId() == R.id.memberinfo_button_takefile) {
			// 갤러리 부르기
		} else if (view.getId() == R.id.memberinfo_button_takephoto) {
			Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
		} else if (view.getId() == R.id.memberinfo_button_send) {
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
				File file = new File(getCacheDir(), "photo.jpg");
				if (file.exists()) {
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

				bitmap.compress(CompressFormat.JPEG, 80, fos);
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
				responce = request.modifyUser(id, name, password, age, sex, phone, job, imageFilePath);
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