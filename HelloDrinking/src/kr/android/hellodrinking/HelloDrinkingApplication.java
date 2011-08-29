package kr.android.hellodrinking;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import kr.android.hellodrinking.transmission.Request;
import kr.android.hellodrinking.transmission.dto.PostBean;
import kr.android.hellodrinking.transmission.dto.ResponceBeanPackege;
import kr.android.hellodrinking.transmission.dto.UserBean;
import kr.android.hellodrinking.utillity.GraphicUtils;
import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.nhn.android.maps.maplib.NGeoPoint;

public class HelloDrinkingApplication extends Application {
	public static String mServerIp = "";
	public static int mServerPort = 0;
	public static final int DEFAULT_SEARCH_DISTANCE = 10000;

	private String id = "";
	private UserBean user = null;
	private List<PostBean> mListPosts;
	private Drawable mUserImage = null;

	public HelloDrinkingApplication() {
		super();
		mListPosts = new ArrayList<PostBean>();
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

	public void setUser(UserBean user) {
		this.user = user;
		setId(user.getId());

		File file = new File(user.getImageFilePath());
		if (!file.getName().equals("")) {
			File imagefile = GraphicUtils.createImageFile(this, user.getBuffer(), file);
			Bitmap bitmap = GraphicUtils.createBitmapFromImageFile(imagefile);
			user.setImageFilePath(imagefile.getAbsolutePath());
			setUserImage(new BitmapDrawable(bitmap));
		}

	}

	public UserBean getUser() {
		return user;
	}

	public void setUserImage(Drawable mUserImage) {
		this.mUserImage = mUserImage;
	}

	public Drawable getUserImage() {
		return mUserImage;
	}
}
