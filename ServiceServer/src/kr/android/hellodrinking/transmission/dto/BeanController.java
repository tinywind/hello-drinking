package kr.android.hellodrinking.transmission.dto;


import java.io.Serializable;

public class BeanController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1021001077959076563L;
	public enum Request {Login, Register};

	public Request request;
	public UserBean user;
	public PostBean post;
	
	public BeanController(Request request){
		this.request = request;
	}
	
	public BeanController(Request request, UserBean user){
		this.request = request;
		this.user = user;
	}
	
	public BeanController(Request request, PostBean post){
		this.request = request;
		this.post = post;
	}	
}
