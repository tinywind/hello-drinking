package kr.co.hellodrinking.map;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import transmission.PostInfo;

public class PostsModel {
	private List<PostsListener> mListeners;
	private List<PostInfo> mModel;
	
	public PostsModel(){
		mListeners = new ArrayList<PostsListener>();
		mModel = new ArrayList<PostInfo>();
	}
	
	public void setModel(List<PostInfo> model){
		mModel = model;
		notifyAllToListeners();
	}
	
	public List<PostInfo> getModel(){
		return mModel;
	}
	
	public void addPost(PostInfo post){
		mModel.add(post);
		notifyAddPostToListeners(post);
	}
	
	public void removePost(PostInfo post){
		mModel.remove(post);
		notifyRemovePostToListeners(post);
	}

	private void notifyRemovePostToListeners(PostInfo post) {
		Iterator<PostsListener> it = mListeners.iterator();
		while(it.hasNext()){
			PostsListener listener = it.next();
			listener.postRemoved(new ValueChangeEvent(this,post));
		}			
	}

	private void notifyAddPostToListeners(PostInfo post) {
		Iterator<PostsListener> it = mListeners.iterator();
		while(it.hasNext()){
			PostsListener listener = it.next();
			listener.postAdded(new ValueChangeEvent(this,post));
		}		
	}

	private void notifyAllToListeners() {
		Iterator<PostsListener> it = mListeners.iterator();
		while(it.hasNext()){
			PostsListener listener = it.next();
			listener.modelChanged(new ValueChangeEvent(this));
		}
	}
	
	public void addListener(PostsListener listener){mListeners.add(listener);}
	public void deleteListener(PostsListener listener){mListeners.remove(listener);}

}
