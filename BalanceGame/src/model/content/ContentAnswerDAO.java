package model.content;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ContentAnswerDAO {
	// 크롤링으로 질문을 받아와 질문모델에 넣어주고 모델에서 질문을 하고 사용자가 선택한 답변을 답변SQL에 담고 담은 데이터를 DAO에서 꺼내
	// 사용한다.
	//
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;

	final String SELECTALL = "SELECT * FROM content_answers";
//    final String SELECT_BY_GENDER = "SELECT * FROM content_answers WHERE gender = ?";
//    final String SELECT_BY_AGE = "SELECT * FROM content_answers WHERE age = ?";
//    final String SELECT_BY_USER_AND_QUEST = "SELECT * FROM content_answers WHERE user_id = ? AND quest_id = ?";
	final String INSERT = "INSERT INTO content_answers (user_id, quest_id, answer) VALUES (?, ?, ?)";
	final String SELECTONE = "SELECT * FROM content_answers WHERE idx = ?";
	final String DELETE = "DELETE FROM content_answers WHERE idx = ?";
//	final String UPDATE = "DELETE FROM content_answers WHERE idx = ?";
	
	public boolean insert(ContentAnswerDTO cdto) {
		return false;
	}
	public boolean update(ContentAnswerDTO cdto) {
		return false;
	}
	public boolean delete(ContentAnswerDTO cdto) {
		return false;
	}
	public ArrayList<ContentAnswerDTO> selectAll(ContentAnswerDTO cdto) {
		ArrayList<ContentAnswerDTO> datas = new ArrayList<ContentAnswerDTO>();
		
		return datas;
	}
	public boolean selectOne(ContentAnswerDTO cdto) {
		return false;
	}

	
	
}
