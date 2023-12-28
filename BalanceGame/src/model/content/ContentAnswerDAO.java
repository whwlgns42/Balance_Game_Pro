package model.content;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Util.JDBCUtil;

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
//	final String DELETE = "DELETE FROM content_answers WHERE idx = ?";
//	final String UPDATE = "DELETE FROM content_answers WHERE idx = ?";

	public boolean insert(ContentAnswerDTO cdto) {
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
//	public boolean update(ContentAnswerDTO cdto) {
//		return false;
//	}
//	public boolean delete(ContentAnswerDTO cdto) {
//		return false;
//	}

	public ArrayList<ContentAnswerDTO> selectAll(ContentAnswerDTO cdto) {
		ArrayList<ContentAnswerDTO> datas = new ArrayList<ContentAnswerDTO>();
		conn = JDBCUtil.connect();
		try {
			pstmt = conn.prepareStatement(SELECTALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ContentAnswerDTO data = new ContentAnswerDTO();
				data.setIdx(rs.getInt("idx"));
				data.setUser_idx(rs.getInt("user_id"));
				data.setQuest_idx(rs.getInt("quest_id"));
				data.setContent(rs.getString("answer"));
				datas.add(data);
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}

		return datas;
	}

	public ContentAnswerDTO selectOne(ContentAnswerDTO cdto) {
		ContentAnswerDTO data = new ContentAnswerDTO();
	    
	    conn = JDBCUtil.connect();
	    try {
	        pstmt = conn.prepareStatement(SELECTONE);
	        pstmt.setInt(1, cdto.getIdx());
	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            data.setIdx(rs.getInt("idx"));
	            data.setUser_idx(rs.getInt("user_id"));
	            data.setQuest_idx(rs.getInt("quest_id"));
	            data.setContent(rs.getString("answer"));
	        }
	        rs.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        JDBCUtil.disconnect(pstmt, conn);
	    }

	    return data;
	}

}
