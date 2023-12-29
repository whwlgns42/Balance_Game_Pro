package view;

public class CommonView extends View{
   
   private int answer;
   
   public int userMenuAction() {
      System.out.print("번호입력>> ");
      answer = validation(1, 5, sc.nextInt());
      return answer;
   }
   public int userResultAction() {
      System.out.print("번호입력>> ");
      answer = validation(1, 2,sc.nextInt());
      return answer;
   }
   public int qustionAction() {
	   System.out.println("답을 입력해주세요");
	   answer = validation(1, 2, sc.nextInt());
	   return answer;
   }
   public int commentUpdateAction() {
	   System.out.print("번호입력>> ");
	   answer = validation(0, 2, sc.nextInt());
	   return answer;
   }
   public int loginListAction() {
	   System.out.print("번호입력>> ");
	   answer = validation(0, 2, sc.nextInt());
	   return answer;
   }
   public int inputNextAction() {
	   System.out.print("번호입력>> ");
	   answer = validation(1, 3, sc.nextInt());
	   return answer;
   }
   public int adminMenuAction() {
	   System.out.print("번호입력>> ");
	   answer = validation(0, 4, sc.nextInt());
	   return answer;
   }
   public int userManagementAction() {
	   System.out.print("번호입력>> ");
	   answer = validation(0, 2, sc.nextInt());
	   return answer;
   }
   public int listManagementAction() {
	   System.out.print("번호입력>> ");
	   answer = validation(0, 4, sc.nextInt());
	   return answer;
   }
 
}