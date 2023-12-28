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

    final String SELECTONE = "SELECT * FROM USER_COMMENTS WHERE IDX = ?";
    final String SELECTALL = "SELECT * FROM USER_COMMENTS"; // 모든 댓글을 가져오는 쿼리
    final String INSERT = "INSERT INTO USER_COMMENTS (QUEST_IDX, USER_IDX, USER_COMMENT) VALUES (?, ?, ?)";
    final String UPDATE = "UPDATE USER_COMMENTS SET QUEST_IDX = ?, USER_IDX = ?, USER_COMMENT = ? WHERE IDX = ?";
    final String DELETE = "DELETE FROM USER_COMMENTS WHERE IDX = ?";

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
                comment.setIdx(rs.getInt("IDX"));
                comment.setQuest_idx(rs.getInt("QUEST_IDX"));
                comment.setUser_idx(rs.getInt("USER_IDX"));
                comment.setUser_comment(rs.getString("USER_COMMENT"));
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
                data.setIdx(rs.getInt("IDX"));
                data.setQuest_idx(rs.getInt("QUEST_IDX"));
                data.setUser_idx(rs.getInt("USER_IDX"));
                data.setUser_comment(rs.getString("USER_COMMENT"));
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