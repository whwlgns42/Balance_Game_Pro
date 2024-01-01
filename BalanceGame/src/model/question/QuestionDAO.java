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

	// TODO SELECT_ALL : 문제의 테이블의 정보를 모두 조회
	private static final String SELECT_ALL = "SELECT IDX, TITLE, CONTENT_A, CONTENT_B, WRITER, REG_DATE FROM QUESTIONS";
	
	// TODO SELECT_ONE : 가져올 문제테이블의 정보를 무작위로 정렬해서 가져와서 맨위에 있는 한개의 행의 데이터만 조회 (랜덤으로 한개의 문제 정보 가져오기)
	private static final String SELECT_ONE = "SELECT IDX, TITLE, CONTENT_A, CONTENT_B, WRITER, REG_DATE FROM (SELECT * FROM QUESTIONS ORDER BY DBMS_RANDOM.VALUE) WHERE ROWNUM = 1";
	
	// TODO SELECT_PK_ONE : 문제번호로 한개의 문제정보 조회하기
	private static final String SELECT_PK_ONE = "SELECT IDX, TITLE, CONTENT_A, CONTENT_B, WRITER, REG_DATE FROM QUESTIONS WHERE IDX = ?";
	
	// TODO INSERT :문제의 정보를 저장
	private static final String INSERT = "INSERT INTO QUESTIONS(IDX, TITLE, CONTENT_A, CONTENT_B, WRITER)  VALUES((SELECT NVL(MAX(IDX), 0) + 1 FROM QUESTIONS),?,?,?,?)";

	 // 문제의 테이블의 정보를 모두 조회
	public ArrayList<QuestionDTO> selectAll(QuestionDTO questionDTO) {
		ArrayList<QuestionDTO> datas = new ArrayList<QuestionDTO>();
		QuestionDTO questionData = null;
		if (questionDTO.getSearchCondition().equals("문제전체조회")) {
			conn = JDBCUtil.connect();
			try {
				pstmt = conn.prepareStatement(SELECT_ALL);
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

	// 가져올 문제테이블의 정보를 무작위로 정렬해서 가져와서 맨위에 있는 한개의 행의 데이터만 조회
	public QuestionDTO selectOne(QuestionDTO questionDTO) { 
		QuestionDTO data = null;
		if (questionDTO.getSearchCondition().equals("문제생성")) {
			conn = JDBCUtil.connect();
			try {
				pstmt = conn.prepareStatement(SELECT_ONE);
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
