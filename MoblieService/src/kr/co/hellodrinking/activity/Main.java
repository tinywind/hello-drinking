package kr.co.hellodrinking.activity;

import java.io.*;
import java.util.*;

import kr.co.hellodrinking.*;
import android.app.*;
import android.os.*;

public class Main extends Activity {
	private List<File> mImageFiles;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview);
		
		mImageFiles = new ArrayList<File>();
		
		
		File sdcardRoot = Environment.getExternalStorageDirectory();
		
		File[] children = sdcardRoot.listFiles();
		
		for(int index=0;index<children.length;index++){
			if(children[index].isDirectory()){
				//만약 디렉토리
			}else{
				//실제 파일
				//이미지파일이 맞나?
				mImageFiles.add(children[index]);
				
			}			
		}
		////////////////////////////////////////////
		
		//뿌려주기 :: 그리드뷰
		
		
	}
	
	///////////////////////////////
	///콜백 :: 경로받기
}
