package kr.android.hellodrinking.transmission.dto;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;

public class PostBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6460998503969733840L;

	private int postNum = -1;
	private String id = "";
	private String comment = "";
	private String imageFilePath = "";
	private double longitude = -1, latitude = -1;
	private byte[] buffer = {};

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		if (id != null)
			this.id = id;
	}

	public String getImageFilePath() {
		return imageFilePath;
	}

	public void setImageFilePath(String imageFilePath) {
		if (imageFilePath != null) {
			this.imageFilePath = imageFilePath;
			convertToBytes();
		}
	}

	private void convertToBytes() {
		try {
			File file = new File(imageFilePath);
			if (!file.exists() || !file.isFile())
				return;

			byte[] buf = new byte[(int) file.length()];
			FileInputStream reader = new FileInputStream(file);
			reader.read(buf);
			this.buffer = buf;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setBuffer(byte[] buffer) {
		if (buffer != null)
			this.buffer = buffer;
	}

	public byte[] getBuffer() {
		return buffer;
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
