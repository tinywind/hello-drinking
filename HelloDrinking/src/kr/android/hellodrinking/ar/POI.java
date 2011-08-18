package kr.android.hellodrinking.ar;

import android.graphics.Bitmap;

public class POI {
	private double latitude;
	private double longitude;
	private double altitude;
	private String name;
	private String description;
	private String link;
	private String iconresource;
	private String iconuri;
	private String detailAction;

	public POI(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public POI(double latitude, double longitude, double altitude, String name) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.altitude = altitude;
		this.name = name;
	}

	public POI(double latitude, double longitude, double altitude, String name,	String description) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.altitude = altitude;
		this.name = name;
		this.description = description;
	}

	public POI(double latitude, double longitude, double altitude, String name,
			String description, String iconresource, String iconuri) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.altitude = altitude;
		this.name = name;
		this.description = description;
		this.iconresource = iconresource;
		this.iconuri = iconuri;
	}

	public POI(double latitude, double longitude, double altitude, String name,
			String description, String link, String detailAction,
			String iconresource, String iconuri) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.altitude = altitude;
		this.name = name;
		this.link = link;
		this.detailAction = detailAction;
		this.description = description;
		this.iconresource = iconresource;
		this.iconuri = iconuri;
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

	public double getAltitude() {
		return this.altitude;
	}

	public void setAltitude(double altitude) {
		this.altitude = altitude;
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

	public String getIconresource() {
		return this.iconresource;
	}

	public void setIconresource(String iconresource) {
		this.iconresource = iconresource;
	}

	public String getIconuri() {
		return this.iconuri;
	}

	public void setIconuri(String iconuri) {
		this.iconuri = iconuri;
	}

	public String getLink() {
		return this.link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDetailAction() {
		return this.detailAction;
	}

	public void setDetailAction(String detailAction) {
		this.detailAction = detailAction;
	}
}