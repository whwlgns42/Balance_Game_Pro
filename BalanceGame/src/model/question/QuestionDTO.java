package model.question;

import java.util.Date;

public class QuestionDTO {
	private int qid;
	private String title;
	private String content_A;
    private String content_B;
    private int writer; // 이 경우, 작성자는 User 테이블의 idx 컬럼을 참조할 수 있습니다.
    private Date reg_date;
    private String searchCondition;
    
    
    
	public String getSearchCondition() {
		return searchCondition;
	}
	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}
	public int getQid() {
		return qid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setQid(int qid) {
		this.qid = qid;
	}
	public String getContent_A() {
		return content_A;
	}
	public void setContent_A(String content_A) {
		this.content_A = content_A;
	}
	public String getContent_B() {
		return content_B;
	}
	public void setContent_B(String content_B) {
		this.content_B = content_B;
	}
	public int getWriter() {
		return writer;
	}
	public void setWriter(int writer) {
		this.writer = writer;
	}
	public Date getReg_date() {
		return reg_date;
	}
	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}
	@Override
	public String toString() {
		return "QuestionDTO [qid=" + qid + ", title=" + title + ", content_A=" + content_A + ", content_B=" + content_B
				+ ", writer=" + writer + "]";
	}
	
	
    
    
	

}
