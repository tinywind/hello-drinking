package kr.android.hellodrinking.server.dao;


import java.util.ArrayList;

public interface DAO {
	ArrayList<Object> getList();//����Ʈ��ü�� �����´�.
	void ModifyList();//��������
	void setList();//��������
	void DeleteList();//��������
}
