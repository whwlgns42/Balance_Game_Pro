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
	private static final String SELECTONE = "SELECT * FROM (SELECT * FROM QUESTIONS ORDER BY DBMS_RANDOM.VALUE) WHERE ROWNUM = 1";
	private static final String INSERT = "INSERT INTO QUESTIONS(IDX, TITLE, CONTENT_A, CONTENT_B, WRITER)  VALUES((SELECT NVL(MAX(IDX), 0) + 1 FROM QUESTIONS),?,?,?,?)";
	private static final String UPDATE = "";
	private static final String DELETE = "";

	public ArrayList<UserDTO> selectAll(QuestionDTO questionDTO) { // 전체 검색
		conn = JDBCUtil.connect();

		return null;
	}

	public QuestionDTO selectOne(QuestionDTO questionDTO) { // 문제생성 
		System.out.println(questionDTO.getSearchCondition());
		QuestionDTO data = null;
		if (questionDTO.getSearchCondition().equals("문제생성")) {
			conn = JDBCUtil.connect();
			try {
				pstmt = conn.prepareStatement(SELECTONE);
				ResultSet rs = pstmt.executeQuery();

				if (rs.next()) {
					data = new QuestionDTO();
					data.setQid(rs.getInt("IDX"));
					data.setTitle(rs.getString("TITLE"));
					data.setContent_A(rs.getString("CONTENT_A"));
					data.setContent_B(rs.getString("CONTENT_B"));
					data.setWriter(rs.getInt("WRITER"));
					data.setReg_date(rs.getDate("REG_DATE"));
				}
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				JDBCUtil.disconnect(pstmt, conn);
			}
		}
		return data;
	}

	// 크롤링한 문제 추가하기
	public boolean insert(QuestionDTO questionDTO) {
		conn = JDBCUtil.connect();
		System.out.println("fff");

		try {
			pstmt = conn.prepareStatement(INSERT);
			pstmt.setString(1, questionDTO.getTitle());
			pstmt.setString(2, questionDTO.getContent_A());
			pstmt.setString(3, questionDTO.getContent_B());
			pstmt.setInt(4, questionDTO.getWriter());

			int result = pstmt.executeUpdate();
			System.out.println(result);
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

//	public boolean update(UserDTO uDTO) { // TODO 미구현
//		conn = JDBCUtil.connect();
//		try {
//			pstmt = conn.prepareStatement(UPDATE);
//			pstmt.setString(1, uDTO.getId());
//			pstmt.setString(2, uDTO.getPw());
//			pstmt.setString(3, uDTO.getName());
//			pstmt.setString(4, uDTO.getGrade());
//			pstmt.setString(5, uDTO.getGender());
//			pstmt.setInt(6, uDTO.getAge());
//			int result = pstmt.executeUpdate();
//			if (result <= 0) {
//				return false;
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return false;
//		} finally {
//			JDBCUtil.disconnect(pstmt, conn);
//		}
//		return true;
//	}

//	public boolean delete(UserDTO uDTO) { // TODO 미구현
//		conn = JDBCUtil.connect();
//		try {
//			pstmt = conn.prepareStatement(DELETE);
//			pstmt.setInt(1, uDTO.getUid());
//			int result = pstmt.executeUpdate();
//			if (result <= 0) {
//				return false;
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return false;
//		} finally {
//			JDBCUtil.disconnect(pstmt, conn);
//		}
//		return true;
//	}
}
