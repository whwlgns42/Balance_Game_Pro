package model.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Util.JDBCUtil;

public class UserDAO {

	Connection conn;
	PreparedStatement pstmt;

	final String SELECTONE = "SELECT * FROM PRODUCT WHERE Id=?" + "";

	
	
	public ArrayList<UserDTO> selectAll(UserDTO uDTO){
		ArrayList<UserDTO> datas = new ArrayList<UserDTO>();
		conn = JDBCUtil.connect();
		return datas;
	}
	
	public UserDTO selectOne(UserDTO udto) {
		UserDTO data = null;
		conn = JDBCUtil.connect();

		try {
			pstmt = conn.prepareStatement(SELECTONE);
			pstmt.setString(1, udto.getId());
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				data = new UserDTO();
				data.setId(rs.getString("Id"));
				data.setPw(rs.getString("Pw"));
				data.setName(rs.getString("Name"));
				data.setGrade(rs.getString("Grade"));
				data.setGender(rs.getString("Gender"));
				data.setAge(rs.getInt("Age"));

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
