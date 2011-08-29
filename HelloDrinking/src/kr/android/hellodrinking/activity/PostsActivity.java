package kr.android.hellodrinking.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.io.File;
import java.util.List;
import com.nhn.android.maps.maplib.NGeoPoint;
import kr.android.hellodrinking.dialog.UserInfoDialog;
import kr.android.hellodrinking.transmission.dto.PostBean;
import kr.android.hellodrinking.utillity.Calculations;
import kr.android.hellodrinking.utillity.GraphicUtils;
import kr.android.hellodrinking.HelloDrinkingApplication;
import kr.android.hellodrinking.R;

public class PostsActivity extends FrameActivity {
	private ListView mListViewPosts;

	@Override
	protected void loadContent() {
		mInflater.inflate(R.layout.posts, mViewgroup);
		mButtonPosts.setClickable(false);
		mListViewPosts = (ListView) findViewById(R.id.posts_listview);
		mMapLocationManager.setOnLocationChangeListener(this);

		if (((HelloDrinkingApplication) getApplication()).getListPosts().size() < 1) {
			((HelloDrinkingApplication) getApplication()).refreshListPosts(myLocation, Integer.parseInt(mEditDistance.getText().toString()));
		}
		presentPosts();
	}

	@Override
	protected void presentPosts() {
		List<PostBean> posts = ((HelloDrinkingApplication) getApplication()).getListPosts();
		PostAdapter adapter = new PostAdapter(this, R.layout.posts_row, posts);
		mListViewPosts.setAdapter(adapter);
		presentLocationChanged();
	}

	@Override
	protected void presentLocationChanged() {
		if (myLocation.getLatitude() != 0 && myLocation.getLongitude() != 0) {
			for (int index = 0; index < mListViewPosts.getChildCount(); index++) {
				PostBean post = (PostBean) mListViewPosts.getItemAtPosition(index);
				View view = mListViewPosts.getChildAt(index);
				TextView textDistance = (TextView) view.findViewById(R.id.posts_row_text_distance);
				textDistance.setText(Calculations.distanceBetweenTwoGeoPoints(myLocation, new NGeoPoint(post.getLongitude(), post.getLatitude()))
						+ " m");
			}
		}
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

				File file = new File(post.getImageFilePath());
				if (!file.getName().equals("")) {
					File imagefile = GraphicUtils.createImageFile(PostsActivity.this, post.getBuffer(), file);
					Bitmap bitmap = GraphicUtils.createBitmapFromImageFile(imagefile);
					image.setImageBitmap(bitmap);
					post.setImageFilePath(imagefile.getAbsolutePath());
				}
			}

			convertView.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent(PostsActivity.this, UserInfoDialog.class);
					intent.putExtra("kr.android.hellodrinking.POST", post);
					startActivity(intent);
				}
			});

			return convertView;
		}
	}
}
