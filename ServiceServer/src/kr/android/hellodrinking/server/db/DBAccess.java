package kr.android.hellodrinking.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kr.android.hellodrinking.transmission.dto.UserBean;
import kr.android.hellodrinking.transmission.exception.GetUserException;
import kr.android.hellodrinking.transmission.exception.LoginException;
import kr.android.hellodrinking.transmission.exception.Message;
import kr.android.hellodrinking.transmission.exception.UserModifyException;

public class DBAccess {
	private Connection mConnection;

	public DBAccess(Connection conn) {
		mConnection = conn;
	}

	public Exception register(String id, String name, String password, String age, String sex, String phone, String job, String image) {
		PreparedStatement pstmt = null;
		try {
			pstmt = mConnection.prepareStatement("insert into userinfo values(?,?,?,?,?,?,?,?)");
			pstmt.setString(1, id);
			pstmt.setString(2, name);
			pstmt.setString(3, password);
			pstmt.setString(4, age);
			pstmt.setString(5, sex);
			pstmt.setString(6, phone);
			pstmt.setString(7, job);
			pstmt.setString(8, image);
			pstmt.executeUpdate();
			mConnection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			return e;
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public Exception setImageAtUserInfo(String id, String image) {
		PreparedStatement pstmt = null;
		try {
			pstmt = mConnection.prepareStatement("UPDATE userinfo SET image=? WHERE id=?");
			pstmt.setString(1, image);
			pstmt.setString(2, id);
			pstmt.executeUpdate();
			mConnection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			return e;
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	

	public Exception setImageAtPostInfo(String id, String image) {
		PreparedStatement pstmt = null;
		try {
			pstmt = mConnection.prepareStatement("UPDATE postinfo SET image=? WHERE id=?");
			pstmt.setString(1, image);
			pstmt.setString(2, id);
			pstmt.executeUpdate();
			mConnection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			return e;
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public Exception addLoginHistory(String id, boolean isSuccessed) {
		PreparedStatement pstmt = null;
		try {
			pstmt = mConnection.prepareStatement("INSERT INTO loginhistory VALUES(SYSDATE,?,?)");
			pstmt.setString(1, id);
			pstmt.setString(2, isSuccessed + "");
			pstmt.executeUpdate();
			mConnection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			return e;
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public Exception getUserPassword(String id) {
		PreparedStatement pstmt = null;
		ResultSet result = null;
		try {
			pstmt = mConnection.prepareStatement("SELECT password FROM userinfo WHERE id=?");
			pstmt.setString(1, id);
			result = pstmt.executeQuery();

			if (result.next()) {
				String pw = result.getString("password");
				return new Message(pw);
			} else {
				return new LoginException("Not Exists ID : " + id, LoginException.State.NotFoundId);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return e;
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public Exception userModify(String id, String name, String password, String age, String sex, String phone, String job, String image) {
		PreparedStatement pstmt = null;
		try {
			pstmt = mConnection.prepareStatement("UPDATE userinfo SET name=?, password=?, age=?, sex=?, phone=?, job=?, image=? WHERE id=?");
			pstmt.setString(1, name);
			pstmt.setString(2, password);
			pstmt.setString(3, age);
			pstmt.setString(4, sex);
			pstmt.setString(5, phone);
			pstmt.setString(6, job);
			pstmt.setString(7, image);
			pstmt.setString(8, id);
			int result = pstmt.executeUpdate();
			mConnection.commit();
			if (result < 1)
				return new UserModifyException("Id와 암호가 일치하지 않습니다.", UserModifyException.State.NotFoundId);
		} catch (SQLException e) {
			e.printStackTrace();
			return e;
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public Exception getUser(String id) {
		PreparedStatement pstmt = null;
		ResultSet result = null;
		try {
			pstmt = mConnection.prepareStatement("SELECT * FROM userinfo WHERE id=?");
			pstmt.setString(1, id);
			result = pstmt.executeQuery();

			if (result.next()) {
				UserBean user = new UserBean();
				user.setId(result.getString("id"));
				user.setName(result.getString("name"));
				user.setPassword(result.getString("password"));
				user.setAge(result.getString("age"));
				user.setSex(result.getString("sex"));
				user.setPhone(result.getString("phone"));
				user.setJob(result.getString("job"));
				user.setImageFilePath(result.getString("image"));
				return new Message(user);
			} else {
				return new GetUserException("Not Exists ID : " + id, GetUserException.State.NotFoundId);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return e;
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public Exception post(String id, String comment, String imageFilePath, double longitude, double latitude) {
		PreparedStatement pstmt = null;
		try {
			pstmt = mConnection.prepareStatement("INSERT INTO postinfo VALUES(post_seq.NEXTVAL,?,?,?,?,?)");
			pstmt.setString(1, id);
			pstmt.setString(2, comment);
			pstmt.setString(3, imageFilePath);
			pstmt.setDouble(4, longitude);
			pstmt.setDouble(5, latitude);
			pstmt.executeUpdate();
			mConnection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			return e;
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}