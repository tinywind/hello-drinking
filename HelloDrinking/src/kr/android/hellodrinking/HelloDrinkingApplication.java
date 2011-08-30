package kr.android.hellodrinking;

import java.util.ArrayList;
import java.util.List;

import kr.android.hellodrinking.transmission.Request;
import kr.android.hellodrinking.transmission.dto.PostBean;
import kr.android.hellodrinking.transmission.dto.ResponceBeanPackege;
import android.app.Application;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.nhn.android.maps.maplib.NGeoPoint;

public class HelloDrinkingApplication extends Application {
	public static final int DEFAULT_SEARCH_DISTANCE = 10000;
	public static String mServerIp = "";
	public static int mServerPort = 0;

	private String id = "";
	private List<PostBean> mListPosts;

	public HelloDrinkingApplication() {
		super();
		mListPosts = new ArrayList<PostBean>();
	}

	public void setServerFromPreferences(){
		SharedPreferences settings = getSharedPreferences("Server", MODE_PRIVATE);
		HelloDrinkingApplication.mServerIp = settings.getString("ip", "10.0.2.2");
		HelloDrinkingApplication.mServerPort = settings.getInt("port", 18080);	
	}
	
	public void addPost(PostBean post) {
		mListPosts.add(post);
	}

	public void removePost(PostBean post) {
		mListPosts.remove(post);
	}

	public List<PostBean> getListPosts() {
		return mListPosts;
	}

	public void setListPosts(List<PostBean> posts) {
		mListPosts = posts;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	@SuppressWarnings("unchecked")
	public void refreshListPosts(NGeoPoint myLocation, int distance) {
		List<PostBean> posts = null;
		Request request = new Request();
		try {
			ResponceBeanPackege responce = request.getPosts(myLocation.getLongitude(), myLocation.getLatitude(), distance);
			posts = (List<PostBean>) responce.getObject();
			setListPosts(posts);
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(this, "연결이 옳바르지 않습니다.", Toast.LENGTH_SHORT).show();
			return;
		} finally {
			request.close();
		}
	}
}
