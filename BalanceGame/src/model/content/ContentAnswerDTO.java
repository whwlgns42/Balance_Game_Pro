package model.content;

public class ContentAnswerDTO {
	
	private int idx;
    private int user_idx; // User 테이블의 idx를 참조
    private int quest_idx; // Question 테이블의 idx를 참조
    private String content;

    private int answerCnt_A;
    private int answerCnt_B;
     
    
//    private Date reg_date;
    
    
	public int getIdx() {
		return idx;
	}
	public int getAnswerCnt_A() {
		return answerCnt_A;
	}
	public void setAnswerCnt_A(int answerCnt_A) {
		this.answerCnt_A = answerCnt_A;
	}
	public int getAnswerCnt_B() {
		return answerCnt_B;
	}
	public void setAnswerCnt_B(int answerCnt_B) {
		this.answerCnt_B = answerCnt_B;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public int getUser_idx() {
		return user_idx;
	}
	public void setUser_idx(int user_idx) {
		this.user_idx = user_idx;
	}
	public int getQuest_idx() {
		return quest_idx;
	}
	public void setQuest_idx(int quest_idx) {
		this.quest_idx = quest_idx;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
//	public Date getReg_date() {
//		return reg_date;
//	}
//	public void setReg_date(Date reg_date) {
//		this.reg_date = reg_date;
//	}
    
    
    
}
