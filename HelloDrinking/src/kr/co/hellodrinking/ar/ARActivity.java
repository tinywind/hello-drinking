package kr.co.hellodrinking.ar;

import kr.co.hellodrinking.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class ARActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.ar);
	}
}