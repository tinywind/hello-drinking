package kr.android.hellodrinking.transmission.dto;

import java.io.Serializable;

public class RequestBeanPackege implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1021001077959076563L;

	public enum Request {
		Login, Register, UserModify, GetUser, Post, GetPosts, GetPost
	};

	public Request request;
	public UserBean user;
	public PostBean post;
	public double longitude = 0, latitude = 0;
	public int state = 0;

	public RequestBeanPackege(Request request) {
		this.request = request;
	}

	public RequestBeanPackege(Request request, UserBean user) {
		this.request = request;
		this.user = user;
	}

	public RequestBeanPackege(Request request, PostBean post) {
		this.request = request;
		this.post = post;
	}

	public RequestBeanPackege(Request request, double longitude, double latitude, int state) {
		this.request = request;
		this.longitude = longitude;
		this.latitude = latitude;
		this.state = state;
	}
}
