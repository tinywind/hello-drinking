package kr.android.hellodrinking.transmission.dto;

import java.io.Serializable;

import com.nhn.android.maps.maplib.NGeoPoint;

public class PostBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6460998503969733840L;
	private String id;
	private String name;
	private String Image;
	private String NumberOfPeople;
	private String comment;
	private String date;
	private String matching;
	private NGeoPoint point;

	public PostBean(String id, String filepath, double x, double y) {
		this.id = id;
		this.Image = filepath;
		setPoint(new NGeoPoint(x, y));
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return Image;
	}

	public void setImage(String image) {
		Image = image;
	}

	public String getNumberOfPeople() {
		return NumberOfPeople;
	}

	public void setNumberOfPeople(String numberOfPeople) {
		NumberOfPeople = numberOfPeople;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMatching() {
		return matching;
	}

	public void setMatching(String matching) {
		this.matching = matching;
	}

	public void setPoint(NGeoPoint point) {
		this.point = point;
	}

	public NGeoPoint getPoint() {
		return point;
	}

}
