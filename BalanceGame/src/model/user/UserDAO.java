package model.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Util.JDBCUtil;

public class UserDAO {

	private Connection conn;
	private PreparedStatement pstmt;

	private static final String SELECTALL = "SELECT * FROM USERS";
	private static final String SELECTONE = "SELECT * FROM USERS WHERE Id=?" + "";
	private static final String INSERT = "INSERT INTO USERS VALUES((SELECT NVL(MAX(uDTO),0) + 1 FROM PRODUCT),?,?,?,?,?,?)";
	private static final String UPDATE = "UPDATE USERS SET CNT=CNT-1 WHERE uDTO=?";
	private static final String DELETE = "DELETE FROM USERS uDTO=?";

	public ArrayList<UserDTO> selectAll(UserDTO uDTO) { // 전체 검색
		ArrayList<UserDTO> datas = new ArrayList<UserDTO>();
		conn = JDBCUtil.connect();
		try {
			pstmt = conn.prepareStatement(SELECTALL);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				UserDTO data = new UserDTO();
				data.setId(rs.getString("Id"));
				data.setPw(rs.getString("Pw"));
				data.setName(rs.getString("Name"));
				data.setGrade(rs.getString("Grade"));
				data.setGender(rs.getString("Gender"));
				data.setAge(rs.getInt("Age"));

			}

			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}

		return datas;
	}

	public UserDTO selectOne(UserDTO uDTO) { // 단일 검색
		UserDTO data = null;
		conn = JDBCUtil.connect();

		try {
			pstmt = conn.prepareStatement(SELECTONE);
			pstmt.setString(1, uDTO.getId());
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				data = new UserDTO();
				data.setId(rs.getString("Id"));
				data.setPw(rs.getString("Pw"));
				data.setName(rs.getString("Name"));
				data.setGrade(rs.getString("Grade"));
				data.setGender(rs.getString("Gender"));
				data.setAge(rs.getInt("Age"));

			}
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
		return data;

	}

	private boolean insert(UserDTO uDTO) { // 추가
		conn = JDBCUtil.connect();

		try {
			pstmt = conn.prepareStatement(INSERT);
			pstmt.setString(1, uDTO.getId());
			pstmt.setString(2, uDTO.getPw());
			pstmt.setString(3, uDTO.getName());
			pstmt.setString(4, uDTO.getGrade());
			pstmt.setString(5, uDTO.getGender());
			pstmt.setInt(6, uDTO.getAge());

			int result = pstmt.executeUpdate();
			if (result <= 0) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
		return true;
	}

	public boolean update(UserDTO uDTO) { // 변경
		conn = JDBCUtil.connect();
		try {
			pstmt = conn.prepareStatement(UPDATE);
			pstmt.setString(1, uDTO.getId());
			pstmt.setString(2, uDTO.getPw());
			pstmt.setString(3, uDTO.getName());
			pstmt.setString(4, uDTO.getGrade());
			pstmt.setString(5, uDTO.getGender());
			pstmt.setInt(6, uDTO.getAge());
			int result = pstmt.executeUpdate();
			if (result <= 0) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
		return true;
	}

	public boolean delete(UserDTO uDTO) { // 삭제
		conn = JDBCUtil.connect();
		try {
			pstmt = conn.prepareStatement(DELETE);
			pstmt.setInt(1, uDTO.getUid());
			int result = pstmt.executeUpdate();
			if (result <= 0) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
		return true;
	}

}
