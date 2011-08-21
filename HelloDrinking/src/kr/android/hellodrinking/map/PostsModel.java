package kr.android.hellodrinking.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import kr.android.hellodrinking.ar.POI;

public class PostsModel {
	private List<PostsListener> mListeners;
	private List<POI> mModel;

	public PostsModel() {
		mListeners = new ArrayList<PostsListener>();
		mModel = new ArrayList<POI>();
	}

	public PostsModel(Collection<POI> list) {
		this();
		
		Iterator<POI> it = list.iterator();
		while(it.hasNext()){
			addPost(it.next());
		}		
	}

	public void setModel(List<POI> model) {
		mModel = model;
		notifyAllToListeners();
	}

	public List<POI> getModel() {
		return mModel;
	}

	public void addPost(POI poi) {
		mModel.add(poi);
		notifyAddPostToListeners(poi);
	}

	public void removePost(POI poi) {
		mModel.remove(poi);
		notifyRemovePostToListeners(poi);
	}

	private void notifyRemovePostToListeners(POI poi) {
		Iterator<PostsListener> it = mListeners.iterator();
		while (it.hasNext()) {
			PostsListener listener = it.next();
			listener.postRemoved(new ValueChangeEvent(this, poi));
		}
	}

	private void notifyAddPostToListeners(POI poi) {
		Iterator<PostsListener> it = mListeners.iterator();
		while (it.hasNext()) {
			PostsListener listener = it.next();
			listener.postAdded(new ValueChangeEvent(this, poi));
		}
	}

	private void notifyAllToListeners() {
		Iterator<PostsListener> it = mListeners.iterator();
		while (it.hasNext()) {
			PostsListener listener = it.next();
			listener.modelChanged(new ValueChangeEvent(this));
		}
	}

	public void addListener(PostsListener listener) {
		mListeners.add(listener);
	}

	public void deleteListener(PostsListener listener) {
		mListeners.remove(listener);
	}

}
