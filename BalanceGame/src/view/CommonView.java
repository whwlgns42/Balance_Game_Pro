package view;

import java.util.ArrayList;

import model.question.QuestionDTO;

public class CommonView extends View{
   
   private int answer;
   
   //유저처음메뉴
   public int userMenuAction() {
      System.out.print("번호입력>> ");
      answer = validation(1, 5, sc.nextInt());
      return answer;
   }
   //전체결과보기 메뉴
   public int userResultAction() {
      System.out.print("번호입력>> ");
      answer = validation(1, 2,sc.nextInt());
      return answer;
   }
   //답 보기 메뉴
   public String qustionAction() {
	   System.out.println("답을 입력해주세요");
	   answer = validation(1, 2, sc.nextInt());
	   String answer1 = answer==1 ? "A":"B";
	   return answer1;
   }
   //댓글메뉴
   public int commentUpdateAction() {
	   System.out.print("번호입력>> ");
	   answer = validation(0, 2, sc.nextInt());
	   return answer;
   }
   //로그인 후 목록보기
   public int loginListAction() {
	   System.out.print("번호입력>> ");
	   answer = validation(0, 2, sc.nextInt());
	   return answer;
   }
   //다음으로 넘어가기
   public int inputNextAction() {
	   System.out.print("번호입력>> ");
	   answer = validation(1, 3, sc.nextInt());
	   return answer;
   }
   //사용자메뉴
   public int adminMenuAction() {
	   System.out.print("번호입력>> ");
	   answer = validation(0, 4, sc.nextInt());
	   return answer;
   }
   //유저관리메뉴
   public int userManagementAction() {
	   System.out.print("번호입력>> ");
	   answer = validation(0, 2, sc.nextInt());
	   return answer;
   }
   //지문관리메뉴
   public int listManagementAction() {
	   System.out.print("번호입력>> ");
	   answer = validation(0, 4, sc.nextInt());
	   return answer;
   }
 
   public int questions(ArrayList<QuestionDTO> datas) {
		System.out.print("번호입력>> ");
		int answer = validation(1, datas.size(), sc.nextInt());
		return answer;
   }
}