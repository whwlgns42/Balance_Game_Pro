package model.question;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Util.JDBCUtil;

public class QuestionDAO {

	private Connection conn;
	private PreparedStatement pstmt;

	private static final String SELECTALL = "SELECT * FROM QUESTIONS";
	private static final String SELECTONE = "SELECT * FROM (SELECT * FROM QUESTIONS ORDER BY DBMS_RANDOM.VALUE) WHERE ROWNUM = 1";
	private static final String SELECT_PK_ONE = "SELECT * FROM QUESTIONS WHERE IDX = ?";
	private static final String INSERT = "INSERT INTO QUESTIONS(IDX, TITLE, CONTENT_A, CONTENT_B, WRITER)  VALUES((SELECT NVL(MAX(IDX), 0) + 1 FROM QUESTIONS),?,?,?,?)";

	 // 문제 제목만 전체 조회하기
	public ArrayList<QuestionDTO> selectAll(QuestionDTO questionDTO) {
		ArrayList<QuestionDTO> datas = new ArrayList<QuestionDTO>();
		QuestionDTO questionData = null;
		if (questionDTO.getSearchCondition().equals("문제전체조회")) {
			conn = JDBCUtil.connect();
			try {
				pstmt = conn.prepareStatement(SELECTALL);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					questionData = new QuestionDTO();
					questionData.setTitle(rs.getString("TITLE"));
					questionData.setQid(rs.getInt("IDX"));
					questionData.setWriter(rs.getInt("WRITER"));
					questionData.setContent_A(rs.getString("CONTENT_A"));
					questionData.setContent_B(rs.getString("CONTENT_B"));
					datas.add(questionData);
				}
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		return datas;

	}

	// 문제생성
	public QuestionDTO selectOne(QuestionDTO questionDTO) { 
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
		}else if(questionDTO.getSearchCondition().equals("문제보기")) {
			conn = JDBCUtil.connect();
				try {
				pstmt = conn.prepareStatement(SELECT_PK_ONE);
				pstmt.setInt(1, questionDTO.getQid());
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

}
