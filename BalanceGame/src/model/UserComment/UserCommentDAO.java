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

    // TODO SELECT_ONE : 하나의 댓글 조회
    final String SELECT_ONE = "SELECT IDX, QUEST_IDX, USER_IDX, USER_COMMENT, REG_DATE FROM USER_COMMENTS WHERE IDX = ?";
    
   // TODO SELECT_ALL : 모든 댓글을 가져오는 쿼리
    final String SELECT_ALL = "SELECT IDX, QUEST_IDX, USER_IDX, USER_COMMENT, REG_DATE FROM USER_COMMENTS"; // 모든 댓글을 가져오는 쿼리
   
    // TODO INSERT: 유저가 댓글작성
    final String INSERT = "INSERT INTO USER_COMMENTS (IDX,QUEST_IDX, USER_IDX, USER_COMMENT) VALUES ((SELECT NVL(MAX(IDX),0) + 1 FROM USER_COMMENTS),?, ?, ?)";
   
    // TODO UPDATE : 댓글 정보 수정 (추후 구현 예정)
    final String UPDATE = "UPDATE USER_COMMENTS SET QUEST_IDX = ?, USER_IDX = ?, USER_COMMENT = ? WHERE IDX = ?";
   
    // TODO DELETE : 댓글삭제
    final String DELETE = "DELETE FROM USER_COMMENTS WHERE IDX = ?";
    
    // TODO SELECT_BY_QUESTION : 문제 번호로 댓글정보 조회하기
    final String SELECT_BY_QUESTION = "SELECT IDX, QUEST_IDX, USER_IDX, USER_COMMENT, REG_DATE FROM USER_COMMENTS WHERE QUEST_IDX = ?";

    // 댓글 추가하기
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
    // 댓글 수정하기
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
    // 댓글 삭제하기
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
    // 댓글 전체 출력하기 
    public ArrayList<UserCommentDTO> selectAll(UserCommentDTO udto) {
    	

        ArrayList<UserCommentDTO> datas = new ArrayList<>();
        conn = JDBCUtil.connect();
        try {
            pstmt = conn.prepareStatement(SELECT_BY_QUESTION);
            pstmt.setInt(1, udto.getQuest_idx());
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
    	
    // 댓글 하나 출력하기 (지금은 사용x)
    public UserCommentDTO selectOne(UserCommentDTO udto) {
        UserCommentDTO data = null;
        conn = JDBCUtil.connect();

        try {
            pstmt = conn.prepareStatement(SELECT_ONE);
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