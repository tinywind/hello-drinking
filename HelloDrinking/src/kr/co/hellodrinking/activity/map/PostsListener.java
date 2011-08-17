package kr.co.hellodrinking.activity.map;

public interface PostsListener {
	public void modelChanged(ValueChangeEvent e) ;
	public void postAdded(ValueChangeEvent e);
	public void postRemoved(ValueChangeEvent e);
}
