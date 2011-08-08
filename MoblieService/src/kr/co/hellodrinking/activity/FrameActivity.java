package kr.co.hellodrinking.activity;

import kr.co.hellodrinking.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public abstract class FrameActivity extends Activity implements OnClickListener{
	protected LayoutInflater inflater;
	protected ViewGroup viewgroup;
	protected LinearLayout buttonPosts, buttonMap, buttonAR;
	
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.frame);
        
        inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewgroup = (ViewGroup) findViewById(R.id.frame_linear_contents);
        
        buttonPosts = (LinearLayout) findViewById(R.id.frame_linear_posts);
        buttonMap = (LinearLayout) findViewById(R.id.frame_linear_map);
        buttonAR = (LinearLayout) findViewById(R.id.frame_linear_ar);
        
        buttonPosts.setOnClickListener(this);
        buttonMap.setOnClickListener(this);
        buttonAR.setOnClickListener(this);
        
        loadContent();
    }

	@Override
	public void onClick(View view) {
		if(view.getId() == R.id.frame_linear_posts){
			startActivity(new Intent(this, PostsActivity.class));
		}else if(view.getId() == R.id.frame_linear_map){
			startActivity(new Intent(this, MapActivity.class));			
		}else if(view.getId() == R.id.frame_linear_ar){
			startActivity(new Intent(this, ARActivity.class));			
		}
	}	
    
    protected abstract void loadContent();
}
