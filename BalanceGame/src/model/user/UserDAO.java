package model.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Util.JDBCUtil;

public class UserDAO {
    private Connection conn;
    private PreparedStatement pstmt;
    
    // TODO SELECT_ALL : 유저의 모든 정보를 조회
    private static final String SELECT_ALL = "SELECT IDX, ID, PW, NAME, GRADE, GENDER , AGE, REG_DATE FROM USERS";
    
    // TODO SELECT_ONE : 유저 로그인 계정 조회 (로그인)
    private static final String SELECT_ONE = "SELECT IDX, ID, PW, NAME, GRADE, GENDER , AGE, REG_DATE FROM USERS WHERE ID=?";
    
    // TODO USER_SELECT : 유저의 번호로 유저 정보 조회 (댓글 정보 들고오기위해 사용 )
    private static final String USER_SELECT = "SELECT IDX, ID, PW, NAME, GRADE, GENDER , AGE, REG_DATE FROM USERS WHERE IDX=?";
    
    // TODO INSERT : 회원가입시 유전의 정보를 저장하는 쿼리 (추후 구현 예정)
    private static final String INSERT = "INSERT INTO USERS (IDX, ID, PW, NAME, GRADE, GENDER, AGE) VALUES ((SELECT NVL(MAX(IDX),0) + 1 FROM USERS),?,?,?,?,?,?)";
    
    // TODO INSERT : 유저의 개인정보를 수정하는 쿼리 (추후 구현 예정)
    private static final String UPDATE = "UPDATE USERS SET ID=?, PW=?, NAME=?, GRADE=?, GENDER=?, AGE=? WHERE IDX=?";
    
    // TODO DELETE : 회원 탈퇴하는 쿼리
    private static final String DELETE = "DELETE FROM USERS WHERE IDX=?";


	public ArrayList<UserDTO> selectAll(UserDTO uDTO) { // 전체 검색
		ArrayList<UserDTO> datas = new ArrayList<UserDTO>();
		conn = JDBCUtil.connect();
		try {
			pstmt = conn.prepareStatement(SELECT_ALL);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				UserDTO data = new UserDTO();
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

		return datas;
	}

	public UserDTO selectOne(UserDTO uDTO) { // 단일 검색
		UserDTO data = null;
		if(uDTO.getSearchCondition().equals("로그인")) {
			conn = JDBCUtil.connect();
			try {
				pstmt = conn.prepareStatement(SELECT_ONE);
				pstmt.setString(1, uDTO.getId());
				ResultSet rs = pstmt.executeQuery();

				if (rs.next()) {
					data = new UserDTO();
					data.setIdx(rs.getInt("IDX"));
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
		}else if(uDTO.getSearchCondition().equals("유저조회")) {
			conn = JDBCUtil.connect();
			try {
				pstmt = conn.prepareStatement(USER_SELECT);
				pstmt.setInt(1, uDTO.getIdx());
				ResultSet rs = pstmt.executeQuery();

				if (rs.next()) {
					data = new UserDTO();
					data.setIdx(rs.getInt("IDX"));
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
		}
	
		return data;

	}

	private boolean insert(UserDTO uDTO) { // 회원가입 (추후 구현 예정)
		conn = JDBCUtil.connect();

		try {
			pstmt = conn.prepareStatement(INSERT);
			pstmt.setString(1, uDTO.getId());
			pstmt.setString(2, uDTO.getPw());
			pstmt.setString(3, uDTO.getName());
			pstmt.setString(4, uDTO.getGrade());
			pstmt.setString(5, uDTO.getGender());
			pstmt.setInt(6, uDTO.getAge());

			int result = pstmt.executeUpdate();
			if (result <= 0) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
		return true;
	}

	public boolean update(UserDTO uDTO) { // 개인정보 변경 (추후 구현 예정)
		conn = JDBCUtil.connect();
		try {
			pstmt = conn.prepareStatement(UPDATE);
			pstmt.setString(1, uDTO.getId());
			pstmt.setString(2, uDTO.getPw());
			pstmt.setString(3, uDTO.getName());
			pstmt.setString(4, uDTO.getGrade());
			pstmt.setString(5, uDTO.getGender());
			pstmt.setInt(6, uDTO.getAge());
			int result = pstmt.executeUpdate();
			if (result <= 0) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
		return true;
	}

	public boolean delete(UserDTO uDTO) { // 회원탈퇴
		conn = JDBCUtil.connect();
		try {
			pstmt = conn.prepareStatement(DELETE);
			pstmt.setInt(1, uDTO.getIdx());
			int result = pstmt.executeUpdate();
			if (result <= 0) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
		return true;
	}

}
