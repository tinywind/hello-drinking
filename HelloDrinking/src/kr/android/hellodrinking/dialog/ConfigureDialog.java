package kr.android.hellodrinking.dialog;

import kr.android.hellodrinking.HelloDrinkingApplication;
import kr.android.hellodrinking.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ConfigureDialog extends Activity {
	private EditText mEditIp, mEditPort;
	private Button mButtonSave;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.configure);

		mEditIp = (EditText) findViewById(R.id.configure_edit_ip);
		mEditIp.setText(HelloDrinkingApplication.mServerIp);

		mEditPort = (EditText) findViewById(R.id.configure_edit_port);
		mEditPort.setText(HelloDrinkingApplication.mServerPort + "");

		mButtonSave = (Button) findViewById(R.id.configure_button_save);
		mButtonSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				HelloDrinkingApplication.mServerIp = mEditIp
						.getText().toString();
				HelloDrinkingApplication.mServerPort = Integer
						.parseInt(mEditPort.getText().toString());
				setResult(RESULT_OK);
				finish();
			}
		});
	}
}
