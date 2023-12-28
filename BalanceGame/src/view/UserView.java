package view;

import java.util.ArrayList;

import model.UserComment.UserCommentDTO;
import model.content.ContentAnswerDTO;
import model.question.QuestionDTO;
import model.user.UserDTO;

public class UserView extends View {
	
	//사용자 메뉴
	public void printUserMenu() {
		System.out.println("1.회원가입");
		System.out.println("2.로그인");
		System.out.println("3.게임하기");
		System.out.println("4.지문보기");
		System.out.println("5.문제제출");
		System.out.println("6.로그아웃");
		System.out.println("7.회원탈퇴");
		System.out.println("0.게임종료");
	}
	
	//문제보여주기 
	public void printQuestions(ArrayList<QuestionDTO> datas) {
		for(QuestionDTO data : datas) {
			System.out.println(data);
		}
	}
	//로그인
	public UserDTO signIn() {
		UserDTO dto = new UserDTO();
		System.out.print("아이디입력>> ");
		System.out.print("비밀번호입력>> ");
		return dto;
	}
	//로그인 후 게임선택 
	public void loginGameMenu(UserDTO user) {
		if(user != null) {
			System.out.print("1.댓글달기");
		}           
		System.out.println("0.돌아가기");
	}
	//로그인 후 목록보기
	public void loginListMenu(UserDTO user) {
		if(user != null) {
			System.out.println("1.내 목록 보기");
		}
		System.out.println("2.전체목록보기");
		System.out.println("0.돌아가기");
	}
	//댓글보여주기
	public void printComment(ArrayList<UserCommentDTO> datas) {
		for(UserCommentDTO data : datas) {
			System.out.println(data);
		}
	}
	//문제보여주기
	public void selectOne(QuestionDTO data) {
		System.out.println(data);
	}
	//답변보여주기
	public void selectOne(ContentAnswerDTO data) {
		System.out.println(data);
	}
	//다음으로 넘어가기
	public void printNext() {
		System.out.print("다음으로 넘어가시겠습니까?");
		System.out.println("1.네");
		System.out.println("2.아니오");
	}
	//댓글입력
	public UserCommentDTO writeComment() {
		UserCommentDTO data = new UserCommentDTO();
		
		System.out.println("댓글입력해주세요");
		sc.nextLine();
		data.setUser_comment(sc.nextLine());
		return data;
	}
}