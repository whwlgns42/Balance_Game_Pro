package view;

public class AdminView extends View {
	//관리자메뉴
	public void printAdminMenu() {
		System.out.println("1.유저관리");
		System.out.println("2.지문관리");
		System.out.println("3.문제승인");
		System.out.println("4.로그아웃");
		System.out.println("0.돌아가기");
	}
	//유저관리메뉴
	public void printUserManagement() {
		System.out.println("1.회원조회");
		System.out.println("2.회원강퇴");
		System.out.println("0.돌아가기");
	}
	//지문관리메뉴
	public void printListManagement() {
		System.out.println("1.문제생성");
		System.out.println("2.문제수정");
		System.out.println("3.문제조회");
		System.out.println("4.문제삭제");
		System.out.println("0.돌아가기");
	}
	//문제승인메뉴
	public void printCheckQuestion() {
		System.out.println("1.승인");
		System.out.println("2.거절");
		System.out.println("0.돌아가기");
	}
	
}
