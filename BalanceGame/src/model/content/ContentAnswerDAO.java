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
    
    final String SELECTALL = "SELECT * FROM USER_ANSWERS";
    final String INSERT = "INSERT INTO USER_ANSWERS (IDX,USER_IDX, QUEST_IDX, CONTENT) VALUES ((SELECT NVL(MAX(IDX),0) + 1 FROM USER_ANSWERS),?, ?, ?)";
    final String SELECTONE = "SELECT * FROM USER_ANSWERS WHERE IDX = ?";
    final String MY_ANSWER = "SELECT USER_ANSWERS.QUEST_IDX FROM USERS JOIN USER_ANSWERS ON users.IDX = USER_ANSWERS.USER_IDX WHERE USERS.IDX = ? ";
    final String AGE_SELECT = "SELECT COUNT(*) AS RESULT_A FROM USER_ANSWERS JOIN USERS ON USERS.IDX = USER_ANSWERS.USER_IDX WHERE CONTENT = 'A' AND USERS.age BETWEEN ? AND ? AND USER_ANSWERS.QUEST_IDX = ?";
    final String SELECT_COUNT = "SELECT\r\n"
    		+ "    COUNT(CASE WHEN CONTENT = 'A' THEN 1 END) AS COUNT_A,\r\n"
    		+ "    COUNT(CASE WHEN CONTENT = 'B' THEN 1 END) AS COUNT_B\r\n"
    		+ "FROM USER_ANSWERS";
    
    
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

    public ArrayList<ContentAnswerDTO> selectAll(ContentAnswerDTO cdto) {
        ArrayList<ContentAnswerDTO> datas = new ArrayList<ContentAnswerDTO>();
        if(cdto.getSearchCondition().equals("전체출력")) {
        	 conn = JDBCUtil.connect();
             try {
                 pstmt = conn.prepareStatement(SELECTALL);
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
        }else if(cdto.getSearchCondition().equals("내답변")) {
        	System.out.println("내답변 코드 들어옴");
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
       
        return datas;
    }

    public ContentAnswerDTO selectOne(ContentAnswerDTO cdto) {
    	ContentAnswerDTO data = new ContentAnswerDTO();
    	if(cdto == null) {
    		conn = JDBCUtil.connect();
            try {
                pstmt = conn.prepareStatement(SELECTONE);
                pstmt.setInt(1, cdto.getIdx());
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
    	}else if(cdto.getSearchCondition().equals("나이대별조회")) {
    		conn = JDBCUtil.connect();
            try {
                pstmt = conn.prepareStatement(AGE_SELECT);
                pstmt.setInt(1, cdto.getMinAge()); // 나이
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
    	}else if(cdto.getSearchCondition().equals("답변개수")) {
    		conn = JDBCUtil.connect();
            try {
                pstmt = conn.prepareStatement(SELECT_COUNT);
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