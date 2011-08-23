package kr.android.hellodrinking.activity;

import kr.android.hellodrinking.R;
import kr.android.hellodrinking.transmission.Request;
import kr.android.hellodrinking.transmission.dto.ResponceBeanPackege;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener {
	private EditText mEditId, mEditPw;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		mEditId = (EditText) findViewById(R.id.login_user_id);
		mEditPw = (EditText) findViewById(R.id.login_user_password);

		ImageButton button = (ImageButton) findViewById(R.id.login_login_button);
		button.setOnClickListener(this);

		View joinbutton = (View) findViewById(R.id.login_button_join);
		joinbutton.setOnClickListener(this);
	}

	public void onClick(View view) {
		if (view.getId() == R.id.login_login_button) {
			String id = mEditId.getText().toString().trim();
			String pw = mEditPw.getText().toString().trim();
			
			if (id.length() < 1) {
				Toast.makeText(this, "Enter Id", Toast.LENGTH_SHORT).show();
				return;
			}
			if (pw.length() < 1) {
				Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
				return;
			}

			Request request = new Request("192.168.17.134", 18080);
			ResponceBeanPackege responce = request.login(id, pw);
			request.close();
			if (responce.isSuccessed()) {
				Intent intent = new Intent(this, PostsActivity.class);
				startActivity(intent);
			} else {
				Toast.makeText(this, responce.getException().getMessage(), Toast.LENGTH_LONG).show();
			}
		} else if (view.getId() == R.id.login_button_join) {
			Intent intent = new Intent(this, MemberJoinActivity.class);
			startActivity(intent);
		}
	}
}