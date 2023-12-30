package view;

import java.util.InputMismatchException;
import java.util.Scanner;

public class View {
	Scanner sc;

	public View() {
		sc = new Scanner(System.in);
	}

	// 유효성검사
	public int validation(int minNum, int maxNum) {
		int answer = 0;
		while (true) {
			try {
				System.out.print("번호입력>> ");
				answer = sc.nextInt();
				if (answer >= minNum && answer <= maxNum) {
					break;
				}
				System.out.println("유효한 값을 입력해주세요");
			} catch (InputMismatchException e) {
				System.out.println("숫자를 입력해주세요");
				sc.nextLine();
			}
		}
		return answer;
	}
}