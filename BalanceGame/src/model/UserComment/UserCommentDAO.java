package model.UserComment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Util.JDBCUtil;

public class UserCommentDAO {
	
	Connection conn;
	PreparedStatement pstmt;
	
	final String SELECTONE = "SELECT * FROM user_comments WHERE idx = ?";
	final String SELECTALL = "SELECT* FROM user_comments"; // 모든 댓글을 가져오는 쿼리
	final String INSERT = "INSERT INTO user_comments (quest_idx, user_idx, user_comment) VALUES (?, ?, ?)";
	final String UPDATE = "UPDATE user_comments SET quest_idx=?, user_idx=?, user_comment=? WHERE idx=?";
	final String DELETE = "DELETE FROM user_comments WHERE idx=?";

	public boolean insert(UserCommentDTO udto) {
		conn = JDBCUtil.connect();
		try {
			
			pstmt = conn.prepareStatement(INSERT);
			pstmt.setInt(1, udto.getQuest_idx());
			pstmt.setInt(2, udto.getUser_idx());
			pstmt.setString(3, udto.getUser_comment());

			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}

	}

	public boolean update(UserCommentDTO udto) {
		conn = JDBCUtil.connect();
		try {

			pstmt = conn.prepareStatement(UPDATE);
			pstmt.setInt(1, udto.getQuest_idx());
			pstmt.setInt(2, udto.getUser_idx());
			pstmt.setString(3, udto.getUser_comment());
			pstmt.setInt(4, udto.getIdx());

			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}

	}

	public boolean delete(UserCommentDTO udto) {
		conn = JDBCUtil.connect();
		try {

			pstmt = conn.prepareStatement(DELETE);
			pstmt.setInt(1, udto.getIdx());

			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}

	}

	public ArrayList<UserCommentDTO> selectAll(UserCommentDTO udto) {
		ArrayList<UserCommentDTO> datas = new ArrayList<>();
		conn = JDBCUtil.connect();
		try {
			pstmt = conn.prepareStatement(SELECTALL);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				UserCommentDTO comment = new UserCommentDTO();
				comment.setIdx(rs.getInt("idx"));
				comment.setQuest_idx(rs.getInt("quest_idx"));
				comment.setUser_idx(rs.getInt("user_idx"));
				comment.setUser_comment(rs.getString("user_comment"));
				datas.add(comment);

			}
			rs.close();
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}

		return datas;

	}

	public UserCommentDTO selectOne(UserCommentDTO udto) {
		UserCommentDTO data = null;
		conn = JDBCUtil.connect();

		try {
			pstmt = conn.prepareStatement(SELECTONE);
			pstmt.setInt(1, udto.getIdx());
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				data = new UserCommentDTO();
				data.setIdx(rs.getInt("idx"));
				data.setQuest_idx(rs.getInt("quest_idx"));
				data.setUser_idx(rs.getInt("user_idx"));
				data.setUser_comment(rs.getString("user_comment"));
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
