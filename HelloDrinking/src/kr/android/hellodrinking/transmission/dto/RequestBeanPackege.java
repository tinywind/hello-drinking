package kr.android.hellodrinking.transmission.dto;


import java.io.Serializable;

public class RequestBeanPackege implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1021001077959076563L;
	public enum Request {Login, Register, UserModify, GetUser};

	public Request request;
	public UserBean user;
	public PostBean post;
	
	public RequestBeanPackege(Request request){
		this.request = request;
	}
	
	public RequestBeanPackege(Request request, UserBean user){
		this.request = request;
		this.user = user;
	}
	
	public RequestBeanPackege(Request request, PostBean post){
		this.request = request;
		this.post = post;
	}	
}
