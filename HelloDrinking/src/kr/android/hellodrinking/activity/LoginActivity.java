package kr.android.hellodrinking.activity;

import kr.android.hellodrinking.HelloDrinkingApplication;
import kr.android.hellodrinking.R;
import kr.android.hellodrinking.transmission.Request;
import kr.android.hellodrinking.transmission.dto.ResponceBeanPackege;
import kr.android.hellodrinking.transmission.exception.LoginException;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class LoginActivity extends Activity {
	private EditText mEditId, mEditPw;
	private ImageButton mButtonLogin, mButtonJoin;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		mEditId = (EditText) findViewById(R.id.login_text_id);
		mEditPw = (EditText) findViewById(R.id.login_text_pw);

		mButtonJoin = (ImageButton) findViewById(R.id.login_button_join);
		mButtonJoin.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent(LoginActivity.this, MemberJoinActivity.class);
				startActivity(intent);
			}
		});

		mButtonLogin = (ImageButton) findViewById(R.id.login_button_login);
		mButtonLogin.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				String id = mEditId.getText().toString().trim();
				String pw = mEditPw.getText().toString().trim();

				if (id.length() < 1) {
					Toast.makeText(LoginActivity.this, "Enter Id", Toast.LENGTH_SHORT).show();
					mEditId.requestFocus();
					return;
				}
				if (pw.length() < 1) {
					Toast.makeText(LoginActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
					mEditPw.requestFocus();
					return;
				}

				Request request = new Request(HelloDrinkingApplication.DEFAULT_SERVER, HelloDrinkingApplication.DEFAULT_PORT);
				ResponceBeanPackege responce = null;
				try {
					responce = request.login(id, pw);
				} catch (Exception e) {
					e.printStackTrace();
					Toast.makeText(LoginActivity.this, "인터넷 연결이 옳바르지 않습니다.", Toast.LENGTH_SHORT).show();
					return;
				} finally {
					request.close();
				}
				if (responce == null) {
					Toast.makeText(LoginActivity.this, "서버와 바르게 연결되지 않았습니다.", Toast.LENGTH_SHORT).show();
				}
				if (responce.isSuccessed()) {
					((HelloDrinkingApplication) getApplication()).setId(id);
					Intent intent = new Intent(LoginActivity.this, PostsActivity.class);
					startActivity(intent);
				} else {
					Toast.makeText(LoginActivity.this, responce.getException().getMessage(), Toast.LENGTH_LONG).show();
					if (responce.getException() instanceof LoginException) {
						if (((LoginException) responce.getException()).state == LoginException.State.NotFoundId) {
							mEditId.setText("");
							mEditPw.setText("");
							mEditId.requestFocus();
						} else if (((LoginException) responce.getException()).state == LoginException.State.NotMatch) {
							mEditPw.setText("");
							mEditPw.requestFocus();
						}
					}
				}
			}
		});

	}
}