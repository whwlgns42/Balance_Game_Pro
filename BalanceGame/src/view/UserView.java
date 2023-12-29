package view;

import java.util.ArrayList;

import model.UserComment.UserCommentDTO;
import model.content.ContentAnswerDTO;
import model.question.QuestionDTO;
import model.user.UserDTO;

public class UserView extends View {

	// 사용자 통합 메뉴
	public void printUserMenu() {
		System.out.println("1.게임하기");
		System.out.println("2.지문보기");
	}

	// 사용자 메뉴 (로그인 시)
	public void printLoginUserMenu() {
		System.out.println("3.로그인");
	}

	// 사용자 메뉴 (로그아웃 시)
	public void printLogoutUserMenu() {
		System.out.println("4.로그아웃");
		System.out.println("5.회원탈퇴");
	}

	// 결과출력
	public void printUserResult() {
		System.out.println("1.성별비율로 결과보기");
		System.out.println("2.나이비율로 결과보기");
	}

	public void printGenderResult() {
		System.out.println("========성별비율========");
	}

	public void printAgeResult() {
		System.out.println("========나이비율========");
	}

	// 성별비율
	public void printGenderResult(ArrayList<UserCommentDTO> datas) {
		System.out.println("남자비율");
	}

	// 문제보여주기
	public void printQuestions(ArrayList<QuestionDTO> datas) {
		for (int i=0;i<datas.size();i++) {
			System.out.println((i+1)+". " + datas.get(i).getTitle());
		}
	}

	// 로그인
	public UserDTO signIn() {
		UserDTO dto = new UserDTO();
		System.out.print("아이디입력>> ");
		dto.setId(sc.next());
		System.out.print("비밀번호입력>> ");
		dto.setPw(sc.next());
		return dto;
	}

	// 로그인 멘트
	public void printLogin(UserDTO user) {
		if (user == null) {
			System.out.println("로그인 먼저 진행해주세요");
		}
	}

	// 로그아웃 멘트
	public void printLogout(UserDTO user) {
		if (user != null) {
			System.out.println("로그인이 되어 있습니다");
		}
		else {
			System.out.println("로그아웃 되었습니다");
		}
	}
	
	

	// 댓글
	public void commentUpdate(UserDTO user) {
		if (user != null) {
			System.out.print("1.댓글수정");
			System.out.print("2.댓글삭제");
		}
		System.out.println("0.돌아가기");
	}

	// 데이터 없음
	public void printEmptyData() {
		System.out.println("데이터가 없습니다");
	}
							
	// 로그인 후 목록보기
	public void loginListMenu(UserDTO user) {
		if (user != null) {
			System.out.println("1.내 목록 보기");
		}
		System.out.println("2.전체목록보기");
		System.out.println("0.돌아가기");
	}

	// 댓글보여주기
	public void printComment(ArrayList<UserCommentDTO> datas) {
		for (UserCommentDTO data : datas) {
			System.out.println(data.getUser_comment());
		}
	}

	// 문제보여주기
	public void selectOne(QuestionDTO data) {
		System.out.println("지문: " + data.getTitle());
		System.out.println("1. " + data.getContent_A());
		System.out.println("2. " + data.getContent_B());
	}

	// 문제선택
	public QuestionDTO choiceQuestion(ArrayList<QuestionDTO> datas) {
		for (int i = 0; i < datas.size(); i++) {
			System.out.println((i + 1) + ". " + datas.get(i).getTitle());
		}
		int num;
		while (true) {
			System.out.println("보고싶은 문제를 선택해주세요");
			num = sc.nextInt();
			if (num < 0 || num >= datas.size()) {
				System.out.println("번호를 잘못입력하셨습니다 다시 입력해주세요");
				continue;
			}
			break;
		}
		return datas.get(num - 1);
	}

	// 답변보여주기
	public void selectOne(ContentAnswerDTO data) {
		System.out.println(data.getContent());
	}

	// 댓글입력
	public UserCommentDTO writeComment() {
		UserCommentDTO data = new UserCommentDTO();
		System.out.println("댓글입력해주세요");
		sc.nextLine();
		data.setUser_comment(sc.nextLine());
		return data;
	}

	// 비밀번호 입력
	public void inputPW() {
		System.out.println("비밀번호를 입력해주세요");
		sc.next();
	}

	// 결과확인
	public void printAnswerResult(ContentAnswerDTO cDto, QuestionDTO qDto) {
		int total = cDto.getAnswerCntA() + cDto.getAnswerCntB();
		System.out.println(qDto.getTitle());
		System.out.print(qDto.getContent_A() + " : ");
		System.out.println((cDto.getAnswerCntA()/total)*100+"%");
		System.out.print(qDto.getContent_B() + " : ");
		System.out.println((cDto.getAnswerCntB()/total)*100+"%");
	}

	// 성공시
	public void printTrue() {
		System.out.println("성공입니다");
	}

	// 실패시
	public void printFalse() {
		System.out.println("실패입니다");
	}

	// 다음문제
	public int inputNext() {
		System.out.println("다음으로 넘어가시겠습니까?");
		System.out.println("1.네");
		System.out.println("2.아니오");
		System.out.println("3.댓글달기");
		return sc.nextInt();
	}
	//질문보기 메뉴
	public int inputPrint() {
		System.out.println("1.댓글 추가");
		System.out.println("2.성별 보기");
		System.out.println("3.나이 보기");
		System.out.println("0.끝내기");
		return sc.nextInt();
	}
	

}