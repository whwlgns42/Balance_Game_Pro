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
    	
    	//찬우님 댓글모델에서 질문 pk로 댓글 selectAll해서 나오게 해주세요
    	// 일단 pk 따로 생각을 해야함 질문 pk와 댓글 pk가 같도록 이걸 묶어서 출력해야함
    	// 질문 pk를 받아온다고 생각하고 댓글 pk를 테이블끼리 묶어야하나? 그래서 출력하면 질문에 맞는 댓글이 나오게
    	// 아니 그러면 여러개 댓글을 달면 댓글 pk는 계속 변해서 pk가 동일하게는 못맞춰줌
    	// 어케 그 질문에 그 댓글을 할수 있을까?
    	// 질문 테이블에서 pk가져오고 댓글에서 질문pk 이너조인 해서 받아온다
    	
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
    	
    	
//        ArrayList<UserCommentDTO> datas = new ArrayList<>();
//        conn = JDBCUtil.connect();
//        try {
//            pstmt = conn.prepareStatement(SELECTALL);
//            ResultSet rs = pstmt.executeQuery();
//
//            while (rs.next()) {
//                UserCommentDTO comment = new UserCommentDTO();
//                comment.setIdx(rs.getInt("IDX"));
//                comment.setQuest_idx(rs.getInt("QUEST_IDX"));
//                comment.setUser_idx(rs.getInt("USER_IDX"));
//                comment.setUser_comment(rs.getString("USER_COMMENT"));
//                datas.add(comment);
//            }
//            rs.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            JDBCUtil.disconnect(pstmt, conn);
//        }
//        return datas;
//    }

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