package kr.android.hellodrinking.transmission.dto;

import java.io.Serializable;

public class PostBean extends Bean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6460998503969733840L;

	private int postNum = -1;
	private String comment = "";
	private double longitude = -1, latitude = -1;

	public PostBean(String id, double longitude, double latitude) {
		setId(id);
		setGeoPoint(longitude, latitude);
	}

	public PostBean(String id, String comment, String imageFilePath, double longitude, double latitude) {
		setId(id);
		setComment(comment);
		setGeoPoint(longitude, latitude);
		setImageFilePath(imageFilePath);
	}

	public void setComment(String comment) {
		if (comment != null)
			this.comment = comment;
	}

	public String getComment() {
		return comment;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setGeoPoint(double longitude, double latitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public void setPostNum(int postNum) {
		this.postNum = postNum;
	}

	public int getPostNum() {
		return postNum;
	}
}
