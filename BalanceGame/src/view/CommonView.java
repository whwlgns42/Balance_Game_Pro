package view;

public class CommonView extends View{
   
   private int answer;
   
   public int userMenuAction() {
      System.out.print("번호입력>> ");
      answer = sc.nextInt();
      answer = validation(answer, 1, 5);
      return answer;
   }
   public int userResultAction() {
      System.out.print("번호입력>> ");
      answer = sc.nextInt();
      answer = validation(answer, 1, 2);
      return answer;
   }
   
   
}