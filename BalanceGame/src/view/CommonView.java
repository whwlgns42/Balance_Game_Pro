package view;

import java.util.ArrayList;

import model.question.QuestionDTO;

public class CommonView extends View{
   
   //유저처음메뉴
   public int userMenuAction() {
      return validation(0, 5);
   }
   //전체결과보기 메뉴
   public int userResultAction() {
      return validation(1, 2);
   }
   //답 보기 메뉴
   public String qustionAction() {
	   System.out.println("답을 입력해주세요");
	   String answer1 = validation(1, 2)==1 ? "A":"B";
	   return answer1;
   }
   //댓글메뉴
   public int commentUpdateAction() {
	   return validation(0, 2);
   }
   //로그인 후 목록보기
   public int loginListAction() {
	   return validation(0, 2);
   }
   //다음으로 넘어가기
   public int inputNextAction() {
	   return validation(1, 3);
   }
   //사용자메뉴
   public int adminMenuAction() {
	   return validation(0, 4);
   }
   //유저관리메뉴
   public int userManagementAction() {
	   return validation(0, 2);
   }
   //지문관리메뉴
   public int listManagementAction() {
	   return validation(0, 4);
   }
 
   public int questions(ArrayList<QuestionDTO> datas) {
		return validation(1, datas.size());
   }
}