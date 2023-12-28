package view;

import java.util.Scanner;

public class View {
   Scanner sc;

   public View() {
      sc = new Scanner(System.in);
   }

   // 유효성검사
   public static int validation(int minNum, int maxNum, int answer) {
      try {
         while (true) {
            if (answer >= minNum && answer <= maxNum) {
               break;
            }
         }
      } catch (Exception e) {
         System.out.println("숫자를 입력하거나 유효한 값을 입력해주세요");
      }
      return answer;
   }

}