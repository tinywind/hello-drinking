package kr.android.hellodrinking.transmission.dto;

import java.io.Serializable;

public class UserBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4369714086586240329L;

	private String id = null;
	private String password = null;
	private String name = null;
	private String age = null;
	private String sex = null;
	private String phone = null;
	private String job = null;
	private String imageFilePath = null;

	public UserBean() {
		super();
	}

	public UserBean(String id, String password) {
		super();
		this.id = id;
		this.password = password;
	}

	public UserBean(String id, String password, String name, String age, String sex, String phone, String job, String imageFilePath) {
		super();
		this.id = id;
		this.password = password;
		this.name = name;
		this.age = age;
		this.sex = sex;
		this.phone = phone;
		this.job = job;
		this.imageFilePath = imageFilePath;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getImageFilePath() {
		return imageFilePath;
	}

	public void setImageFilePath(String imageFilePath) {
		this.imageFilePath = imageFilePath;
	}
}
