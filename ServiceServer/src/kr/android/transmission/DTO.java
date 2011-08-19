package kr.android.transmission;

import java.io.Serializable;

public class DTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1021001077959076563L;

	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	public int getOption() {
		return option;
	}
	public void setOption(int option) {
		this.option = option;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getLog() {
		return log;
	}
	public void setLog(String log) {
		this.log = log;
	}
	public boolean isImage() {
		return isImage;
	}
	public void setImage(boolean isImage) {
		this.isImage = isImage;
	
	}

	public String getPostNum() {
		return postNum;
	}
	public void setPostNum(String postNum) {
		this.postNum = postNum;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	/**
	 * 
	 */
	
	int option;
	/*
	case 0    ȸ����
	case 1    �α���
	case 2    �Խù� �˻�
	case 3    �Խù� ���
	case 4    �Խù� ����
	case 5    �Խù� ����
	case 6    �Խù� �� ����
	case 7    �Խù��� ���� ����ڷ� ���(�ϳ��� �Խù��� ���� ������ �����)
	case 8    ������ Ȯ��
	case 9    ������ ����
	case 10   �޼��� �߽�, ����
	case 11   �α׾ƿ�
	case 12   ȸ��Ż��
	*/
	boolean result;	//������ Ŭ���̾�Ʈ���� ������ ��� 
	public boolean isImage; //�����̹����� �ִ��� ����� ����	
	public String id;
	public String postNum;//�Խ��� ��ȣ
	public String name;
	public String password;
	public String age;
	public String sex;
	public String phoneNumber;
	public String job;
	public String log;
	public String imageName;
	public String comment;
}
