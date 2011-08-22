package kr.android.hellodrinking.server.dao;

public class PostInfo {
String id;
String name;
String Image;
String NumberOfPeople;
String comment;
String date;
String matching;

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


}
