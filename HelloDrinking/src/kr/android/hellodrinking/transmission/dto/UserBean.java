package kr.android.hellodrinking.transmission.dto;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;

public class UserBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4369714086586240329L;

	private String id = "";
	private String password = "";
	private String name = "";
	private String age = "";
	private String sex = "";
	private String phone = "";
	private String job = "";
	private String imageFilePath = "";
	private byte[] buffer = {};

	public UserBean() {
	}

	public UserBean(String id, String password) {
		this.id = id;
		this.password = password;
	}

	public UserBean(String id) {
		this.id = id;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		if (id != null)
			this.id = id;
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
		this.buffer = buffer;
	}

	public byte[] getBuffer() {
		return buffer;
	}
}
