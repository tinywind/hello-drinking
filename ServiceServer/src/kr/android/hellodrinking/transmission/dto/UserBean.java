package kr.android.hellodrinking.transmission.dto;

import java.io.Serializable;

public class UserBean extends Bean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4369714086586240329L;

	private String password = "";
	private String name = "";
	private String age = "";
	private String sex = "";
	private String phone = "";
	private String job = "";

	public UserBean() {
	}

	public UserBean(String id, String password) {
		setId(id);
		setPassword(password);
	}

	public UserBean(String id) {
		setId(id);
	}

	public UserBean(String id, String name, String password, String age, String sex, String phone, String job, String imageFilePath) {
		setId(id);
		setPassword(password);
		setName(name);
		setAge(age);
		setSex(sex);
		setPhone(phone);
		setJob(job);
		setImageFilePath(imageFilePath);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		if (password != null)
			this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name != null)
			this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		if (age != null)
			this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		if (sex != null)
			this.sex = sex;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		if (phone != null)
			this.phone = phone;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		if (job != null)
			this.job = job;
	}
}
