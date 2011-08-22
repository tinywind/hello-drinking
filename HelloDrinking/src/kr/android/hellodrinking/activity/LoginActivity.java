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

public class LoginActivity extends Activity {
	private EditText mEditId, mEditPw;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		mEditId = (EditText) findViewById(R.id.main_user_id);
		mEditPw = (EditText) findViewById(R.id.main_user_password);

		ImageButton button = (ImageButton) findViewById(R.id.main_login_button);
		button.setOnClickListener(onClickListener);
		
		Button joinbutton = (Button) findViewById(R.id.main_button_join);
		joinbutton.setOnClickListener(onClickListener);
	}

	OnClickListener onClickListener = new OnClickListener() {
		public void onClick(View view) {
			if (view.getId() == R.id.main_login_button) {
				Request fc = new Request();
				Intent intent = new Intent(LoginActivity.this, PostsActivity.class);

//				if (fc.logIn(mEditId.getText().toString(), mEditPw.getText()
//						.toString()))
					startActivity(intent);
//				else
//					Toast.makeText(LoginActivity.this,
//							"아이디가 없거나, 암호와 일치하지 않습니다.", Toast.LENGTH_SHORT)
//							.show();

			}else if (view.getId() == R.id.main_button_join){
				Intent intent = new Intent(LoginActivity.this, MemberJoinActivity.class);
				startActivity(intent);
			}
		}
	};
}
