package model.user;

public class UserDTO {
	private int uid;
	private String id;
	private String pw;
	private String name;
	private String grade;
	private String gender;
	private int age;
//	private Date reg_date;
	
	
	
	
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
//	public Date getReg_date() {
//		return reg_date;
//	}
//	public void setReg_date(Date reg_date) {
//		this.reg_date = reg_date;
//	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	@Override
	public String toString() {
		return "[name=" + name + ", grade=" + grade + ", gender=" + gender + ", age=" + age + "]";
	}
	
	
	
	

}
