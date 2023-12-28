package model.UserComment;

public class UserCommentDTO {
	private int idx;
    private int quest_idx; // Question 테이블의 idx를 참조
    private int user_idx; // User 테이블의 idx를 참조
    private String user_comment;
//    private Date reg_date;	// 날짜 만들기

    
    
    public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public int getQuest_idx() {
		return quest_idx;
	}
	public void setQuest_idx(int quest_idx) {
		this.quest_idx = quest_idx;
	}
	public int getUser_idx() {
		return user_idx;
	}
	public void setUser_idx(int user_idx) {
		this.user_idx = user_idx;
	}
	public String getUser_comment() {
		return user_comment;
	}
	public void setUser_comment(String user_comment) {
		this.user_comment = user_comment;
	}
	
//	public Date getReg_date() {
//		return reg_date;
//	}
//	public void setReg_date(Date reg_date) {
//		this.reg_date = reg_date;
//	}
    
	
	
    
}
