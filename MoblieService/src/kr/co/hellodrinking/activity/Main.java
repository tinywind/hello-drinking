package kr.co.hellodrinking.activity;

import kr.co.hellodrinking.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class Main extends Activity implements OnClickListener {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
<<<<<<< HEAD
=======
		
		ImageButton button = (ImageButton) findViewById(R.id.main_login_button);
		button.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		if(view.getId() == R.id.main_login_button){
			Toast.makeText(this, "click", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(this, SampleFrameActivity.class);
			startActivity(intent);			
		}
>>>>>>> branch 'master' of git@github.com:tinywind/HelloDrinking.git
	}
}
