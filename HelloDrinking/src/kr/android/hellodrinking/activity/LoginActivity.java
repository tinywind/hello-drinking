package kr.android.hellodrinking.activity;

import kr.android.hellodrinking.R;
import kr.android.hellodrinking.transmission.Request;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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

		ImageButton button = (ImageButton) findViewById(R.id.main_login_button);
		button.setOnClickListener(this);

		Button joinbutton = (Button) findViewById(R.id.login_button_join);
		joinbutton.setOnClickListener(this);
	}

	public void onClick(View view) {
		if (view.getId() == R.id.main_login_button) {
			if (mEditId.getTextSize() < 1) {
				Toast.makeText(this, "Enter Id", Toast.LENGTH_SHORT).show();
				return;
			}
			if (mEditPw.getTextSize() < 1) {
				Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
				return;
			}
			
			Request request = new Request("10.0.0.1",18080);

		} else if (view.getId() == R.id.login_button_join) {
			Intent intent = new Intent(this, MemberJoinActivity.class);
			startActivity(intent);
		}
	}
}
