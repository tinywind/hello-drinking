package kr.co.hellodrinking.activity;

import kr.co.hellodrinking.R;
import android.app.Activity;
import android.os.Bundle;

public abstract class FrameActivity extends Activity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame);
        
        // R.id.frame_linear_content 에 invoke 시키는 과정이 필요
        // loadContent() 애서 그 내용을 명시
    }
    
    abstract void loadContent();
}
