package model.question;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Util.JDBCUtil;
import model.user.UserDTO;

public class QuestionDAO {

	private Connection conn;
	private PreparedStatement pstmt;

	private static final String SELECTALL = "";
	private static final String SELECTONE = "SELECT * FROM user_comments WHERE QID = ?";
	private static final String INSERT = "INSERT INTO QUESTIONS VALUES((SELECT NVL(MAX(QID),0) + 1 FROM PRODUCT),?,?,?,?,?)";
	private static final String UPDATE = "";
	private static final String DELETE = "";

	public ArrayList<UserDTO> selectAll(QuestionDTO questionDTO) { // 전체 검색
		conn = JDBCUtil.connect();
		

		return null;
	}
	
	
//	public UserDTO selectOne(QuestionDTO questionDTO) { // 단일 검색
//		QuestionDTO data = null;
//		conn = JDBCUtil.connect();
//
//		try {
//			pstmt = conn.prepareStatement(SELECTONE);
//			pstmt.setInt(1, questionDTO.getQid());
//			ResultSet rs = pstmt.executeQuery();
//
//			if (rs.next()) {
//				data = new QuestionDTO();
//				data.setQid(rs.getInt("QID"));
//				data.setTitle(rs.getString("TITLE"));
//				data.setContent_A(rs.getString("CONTENT_A"));
//				data.setContent_A(rs.getString("CONTENT_A"));
//				data.setTitle(rs.getString("TITLE"));
//				data.setTitle(rs.getString("TITLE"));
//			}
//			rs.close();
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			JDBCUtil.disconnect(pstmt, conn);
//		}
//		return data;
//
//	}

	// 크롤링한 문제 추가하기
	public boolean insert(QuestionDTO questionDTO) { 
		conn = JDBCUtil.connect();

		try {
			pstmt = conn.prepareStatement(INSERT);
			pstmt.setInt(1, questionDTO.getQid());
			pstmt.setString(2, questionDTO.getTitle());
			pstmt.setString(3, questionDTO.getContent_A());
			pstmt.setString(4, questionDTO.getContent_B());
			pstmt.setInt(5, questionDTO.getWriter());

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
