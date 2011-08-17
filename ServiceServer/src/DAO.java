

import java.util.ArrayList;

public interface DAO {
	ArrayList<Object> getList();//리스트객체를 가져온다.
	void ModifyList();//정보수정
	void setList();//정보삽입
	void DeleteList();//정보삭제
}
