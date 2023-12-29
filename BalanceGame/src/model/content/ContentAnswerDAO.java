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
        return data;
    }
}