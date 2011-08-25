package kr.android.hellodrinking.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.nhn.android.maps.NMapLocationManager;
import com.nhn.android.maps.NMapLocationManager.OnLocationChangeListener;
import com.nhn.android.maps.maplib.NGeoPoint;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import kr.android.hellodrinking.R;
import kr.android.hellodrinking.ar.POI;
import kr.android.hellodrinking.transmission.Request;
import kr.android.hellodrinking.transmission.dto.PostBean;
import kr.android.hellodrinking.transmission.dto.ResponceBeanPackege;
import kr.android.hellodrinking.utillity.Calculations;

public class PostsActivity extends FrameActivity implements OnLocationChangeListener {
	private ListView mListPosts;
	private final int DEFAULT_SEARCH_DISTANCE = 100000;

	private ImageButton mButtonRefresh;
	private ImageButton mButtonPost;
	private EditText mEditDistance;

	protected void loadContent() {
		mInflater.inflate(R.layout.posts, mViewgroup);
		mButtonPosts.setClickable(false);
		mListPosts = (ListView) findViewById(R.id.posts_listview);

		mButtonPost = (ImageButton) findViewById(R.id.posts_button_post);
		mButtonRefresh = (ImageButton) findViewById(R.id.posts_button_refresh);
		mEditDistance = (EditText) findViewById(R.id.posts_edit_distance);

		mEditDistance.setText(DEFAULT_SEARCH_DISTANCE + "");

		mButtonPost.setOnClickListener(new OnClickListener() {
			public void onClick(View paramView) {
				startActivity(new Intent(PostsActivity.this, PostActivity.class));
			}
		});

		mButtonRefresh.setOnClickListener(new OnClickListener() {
			public void onClick(View paramView) {
				refreshListPosts();
			}
		});

		mMapLocationManager.setOnLocationChangeListener(this);
		refreshListPosts();
	}

	@SuppressWarnings("unchecked")
	private void refreshListPosts() {
		List<PostBean> posts = null;
		Request request = new Request();
		try {
			ResponceBeanPackege responce = request.getPosts(myLocation.getLongitude(), myLocation.getLatitude(),
					Integer.parseInt(mEditDistance.getText().toString()));
			posts = (List<PostBean>) responce.getObject();
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(this, "연결이 옳바르지 않습니다.", Toast.LENGTH_SHORT).show();
			return;
		}
		mListPosts.setAdapter(new PostAdapter(this, R.layout.posts_row, posts));
	}

	class PostAdapter extends BaseAdapter {
		Context context;
		int id;
		List<PostBean> posts;

		PostAdapter(Context context, int id, List<PostBean> posts) {
			super();
			this.context = context;
			this.id = id;
			this.posts = posts;
		}

		@Override
		public int getCount() {
			return posts.size();
		}

		@Override
		public Object getItem(int position) {
			return posts.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final PostBean post = posts.get(position);
			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(id, parent, false);
				ImageView image = (ImageView) convertView.findViewById(R.id.posts_row_image);
				TextView textId = (TextView) convertView.findViewById(R.id.posts_row_text_id);
				TextView textComment = (TextView) convertView.findViewById(R.id.posts_row_text_comment);
				TextView textGeoPoint = (TextView) convertView.findViewById(R.id.posts_row_text_geopoint);

				textId.setText(post.getId());
				textComment.setText(post.getComment());
				textGeoPoint.setText(post.getLongitude() + "," + post.getLatitude());

				File cacheDir = getCacheDir();
				try {
					if (!cacheDir.isDirectory() || !cacheDir.exists())
						cacheDir.mkdir();
				} catch (Exception e) {
					Toast.makeText(PostsActivity.this, "Create Folder Error about Cache Directory", Toast.LENGTH_SHORT).show();
				}

				File file = new File(post.getImageFilePath());
				if (!file.getName().equals("")) {
					String path = file.getName();
					int index = path.lastIndexOf('\\');
					String name = path.substring(index + 1);

					File imagefile = new File(cacheDir, name);
					FileOutputStream fileWriter = null;

					try {
						fileWriter = new FileOutputStream(imagefile);
						fileWriter.write(post.getBuffer());
						fileWriter.flush();

						FileInputStream fis = new FileInputStream(imagefile);
						Bitmap bitmap = BitmapFactory.decodeStream(fis);
						image.setImageBitmap(bitmap);
						post.setImageFilePath(imagefile.getAbsolutePath());
					} catch (IOException e) {
						e.printStackTrace();
						if (imagefile.exists())
							imagefile.delete();
					} finally {
						try {
							fileWriter.close();
						} catch (IOException e) {
							System.err.println(e.getMessage());
						}
					}
				}
			}

			convertView.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent(PostsActivity.this, UserInfoDialog.class);
					POI poi = new POI(post.getLatitude(), post.getLongitude());
					poi.setComment(post.getComment());
					poi.setName(post.getId());
					poi.setImageFilePath(post.getImageFilePath());
					intent.putExtra("kr.android.POI", poi);
					startActivity(intent);
				}
			});

			return convertView;
		}
	}

	@Override
	public boolean onLocationChanged(NMapLocationManager paramNMapLocationManager, NGeoPoint paramNGeoPoint) {
		if (myLocation.getLatitude() != 0 && myLocation.getLongitude() != 0) {
			for (int index = 0; index < mListPosts.getChildCount(); index++) {
				PostBean post = (PostBean) mListPosts.getItemAtPosition(index);
				View view = mListPosts.getChildAt(index);
				TextView textDistance = (TextView) view.findViewById(R.id.posts_row_text_distance);
				textDistance.setText(Calculations.distanceBetweenTwoGeoPoints(myLocation, new NGeoPoint(post.getLongitude(), post.getLatitude()))
						+ " m");
			}
		}

		return false;
	}

	@Override
	public void onLocationUpdateTimeout(NMapLocationManager paramNMapLocationManager) {
	}
}