package kr.co.hellodrinking.activity;

import kr.co.hellodrinking.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SocketTestActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.socket_test);
        
        final EditText resultview = (EditText) findViewById(R.id.socket_test_resultview);
        Button button = (Button) findViewById(R.id.socket_test_button);
        
        button.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				
			}
		});
    }
}