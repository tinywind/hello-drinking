package kr.android.hellodrinking.ar;

import java.io.Serializable;

public class POI implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2635756434208656575L;

	private double latitude;
	private double longitude;
	private String name;
	private String description;
	private String link;
	private String imagefilepath;
	private String comment;

	public POI(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public POI(double latitude, double longitude, String name) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.name = name;
	}

	public POI(double latitude, double longitude, String name, String description) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.name = name;
		this.description = description;
	}

	public POI(double latitude, double longitude, String name, String description, String imagefilepath) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.name = name;
		this.description = description;
		this.imagefilepath = imagefilepath;
	}

	public POI(double latitude, double longitude, String name, String description, String link, String comment, String imagefilepath) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.name = name;
		this.link = link;
		this.comment = comment;
		this.description = description;
		this.imagefilepath = imagefilepath;
	}

	public double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageFilePath() {
		return this.imagefilepath;
	}

	public void setIconuri(String imagefilepath) {
		this.imagefilepath = imagefilepath;
	}

	public String getLink() {
		return this.link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}