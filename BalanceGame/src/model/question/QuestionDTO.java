package model.question;

public class QuestionDTO {
	private int qid;
	private String content_A;
    private String content_B;
    private String writer; // 이 경우, 작성자는 User 테이블의 idx 컬럼을 참조할 수 있습니다.
//    private Date reg_date;
    
	public int getQid() {
		return qid;
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
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
//	public Date getReg_date() {
//		return reg_date;
//	}
//	public void setReg_date(Date reg_date) {
//		this.reg_date = reg_date;
//	}
    
    
	

}
