package kr.android.hellodrinking.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kr.android.hellodrinking.transmission.exception.LoginException;
import kr.android.hellodrinking.transmission.exception.Message;

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

}
