package kr.co.hellodrinking.activity.map;

public interface PostsListener {
	public void dateChanged(ValueChangeEvent e) ;
	public void postAdded(ValueChangeEvent e);
	public void postRemoved(ValueChangeEvent e);
}
