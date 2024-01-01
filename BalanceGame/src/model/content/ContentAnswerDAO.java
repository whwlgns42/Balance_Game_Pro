package model.content;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Util.JDBCUtil;

//
//
public class ContentAnswerDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	// TODO SELECTALL : 유저가 문제의 답변 전체 조회
	final String SELECT_ALL = "SELECT IDX, USER_IDX, QUEST_IDX, CONTENT, REG_DATE FROM USER_ANSWERS";

	// TODO INSERT : 문제를 풀때 유저의 정보와 문제번호, 문제의 답변을 저장
	final String INSERT = "INSERT INTO USER_ANSWERS (IDX,USER_IDX, QUEST_IDX, CONTENT) VALUES ((SELECT NVL(MAX(IDX),0) + 1 FROM USER_ANSWERS),?, ?, ?)";

	// TODO SELECT_ONE : 문제번호와 유저의 정보로 이용한 유저가 문제를 풀었는지 안풀었는지 확인 여부
	final String SELECT_ONE = "SELECT IDX, USER_IDX, QUEST_IDX, CONTENT, REG_DATE FROM USER_ANSWERS WHERE USER_IDX = ? AND QUEST_IDX=?";

	// TODO MY_ANSWER : 유저테이블, 문제답변 테이블을 조인해서 내가 풀었던 문제 중복 제거한 후 나의 답변 조회
	final String MY_ANSWER = "SELECT DISTINCT USER_ANSWERS.QUEST_IDX FROM USERS JOIN USER_ANSWERS ON users.IDX = USER_ANSWERS.USER_IDX WHERE USERS.IDX = ? ";

	// TODO AGE_SELECT : 유저테이블, 유저답변테이블을 조인해서 A라는 답변을단 유저의 나이대별 조회 (추후 구현 예정)
	final String AGE_SELECT = "SELECT COUNT(CONTENT) AS RESULT_A FROM USER_ANSWERS JOIN USERS ON USERS.IDX = USER_ANSWERS.USER_IDX WHERE CONTENT = 'A' AND USERS.age BETWEEN ? AND ? AND USER_ANSWERS.QUEST_IDX = ?";

	// TODO SELECT_COUNT : 문제번호마다 A답변과 B답변의 총 개수 조회하기 
	final String SELECT_COUNT = "SELECT COUNT(CASE WHEN CONTENT = 'A' THEN 1 END) AS COUNT_A, COUNT(CASE WHEN CONTENT = 'B' THEN 1 END) AS COUNT_B FROM USER_ANSWERS WHERE QUEST_IDX=?";

	public boolean insert(ContentAnswerDTO cdto) { 	// TODO INSERT : 문제를 풀때 유저의 정보와 문제번호, 문제의 답변을 저장
		conn = JDBCUtil.connect();
		try {
			pstmt = conn.prepareStatement(INSERT);
			pstmt.setInt(1, cdto.getUser_idx());
			pstmt.setInt(2, cdto.getQuest_idx());
			pstmt.setString(3, cdto.getContent());

			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
	}

	public ArrayList<ContentAnswerDTO> selectAll(ContentAnswerDTO cdto) { // TODO 이용자가 풀었던 문제에대한 답변 정보 전체 조회
		ArrayList<ContentAnswerDTO> datas = new ArrayList<ContentAnswerDTO>();
		if (cdto.getSearchCondition().equals("전체출력")) {
			conn = JDBCUtil.connect();
			try {
				pstmt = conn.prepareStatement(SELECT_ALL);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					ContentAnswerDTO data = new ContentAnswerDTO();
					data.setIdx(rs.getInt("IDX"));
					data.setUser_idx(rs.getInt("USER_IDX"));
					data.setQuest_idx(rs.getInt("QUEST_IDX"));
					data.setContent(rs.getString("CONTENT"));
					datas.add(data);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			} finally {
				JDBCUtil.disconnect(pstmt, conn);
			}
		} else if (cdto.getSearchCondition().equals("내답변")) { // TODO 이용자가 어떤 답변은 했었는지 조회
			conn = JDBCUtil.connect();
			try {
				pstmt = conn.prepareStatement(MY_ANSWER);
				pstmt.setInt(1, cdto.getUser_idx());
				rs = pstmt.executeQuery();
				while (rs.next()) {
					ContentAnswerDTO data = new ContentAnswerDTO();
					data.setQuest_idx(rs.getInt("QUEST_IDX"));
					datas.add(data);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			} finally {
				JDBCUtil.disconnect(pstmt, conn);
			}
		}
		if (datas.isEmpty()) {
			datas = null;
		}
		return datas;
	}

	public ContentAnswerDTO selectOne(ContentAnswerDTO cdto) { // TODO 이용자가 문제를 풀었던건지 조회하기
		ContentAnswerDTO data = null;
		if (cdto.getSearchCondition().equals("질문탐색")) {
			data = new ContentAnswerDTO();
			conn = JDBCUtil.connect();
			try {
				pstmt = conn.prepareStatement(SELECT_ONE);
				pstmt.setInt(1, cdto.getUser_idx());
				pstmt.setInt(2, cdto.getQuest_idx());
				rs = pstmt.executeQuery();

				if (rs.next()) {
					data.setIdx(rs.getInt("IDX"));
					data.setUser_idx(rs.getInt("USER_IDX"));
					data.setQuest_idx(rs.getInt("QUEST_IDX"));
					data.setContent(rs.getString("CONTENT"));
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				JDBCUtil.disconnect(pstmt, conn);
			}
		} else if (cdto.getSearchCondition().equals("나이대별조회")) { // TODO 추후 구현 예정
			data = new ContentAnswerDTO();
			conn = JDBCUtil.connect();
			try {
				pstmt = conn.prepareStatement(AGE_SELECT);
				pstmt.setInt(1, cdto.getMinAge()); 
				pstmt.setInt(2, cdto.getMaxAge());
				pstmt.setInt(3, cdto.getQuest_idx());
				rs = pstmt.executeQuery();

				if (rs.next()) {
					data.setAnswerCntA(rs.getInt("RESULT_A"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				JDBCUtil.disconnect(pstmt, conn);
			}
		} else if (cdto.getSearchCondition().equals("답변개수")) {
			data = new ContentAnswerDTO();
			conn = JDBCUtil.connect();
			try {
				pstmt = conn.prepareStatement(SELECT_COUNT);
				pstmt.setInt(1, cdto.getQuest_idx());
				rs = pstmt.executeQuery();

				if (rs.next()) {
					data.setAnswerCntA(rs.getInt("COUNT_A"));
					data.setAnswerCntB(rs.getInt("COUNT_B"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				JDBCUtil.disconnect(pstmt, conn);
			}
		}

		return data;
	}
}