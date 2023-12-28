package controller;

import java.util.ArrayList;

import model.UserComment.UserCommentDAO;
import model.content.ContentAnswerDAO;
import model.crawlling.Crawlling;
import model.question.QuestionDAO;
import model.question.QuestionDTO;
import model.user.UserDAO;
import model.user.UserDTO;
import view.CommonView;
import view.UserView;

public class Ctrl {
//시작
	private ContentAnswerDAO answerDAO;
	private QuestionDAO questionDAO;
	private UserCommentDAO commentDAO;
	private UserDAO userDAO;
	private UserDTO loginINFO;
	private UserView userView;
	private CommonView commonView;
	public Ctrl() {
		answerDAO = new ContentAnswerDAO();
		questionDAO = new QuestionDAO();
		commentDAO = new UserCommentDAO();
		userDAO = new UserDAO();
		loginINFO = null;
	}

	public void start() {
		ArrayList<QuestionDTO> crawResults = Crawlling.crwalling();
		for (QuestionDTO questionData : crawResults) { // TODO 컨트롤에서 데이터 확인후 나중에 삭제해주세요
			System.out.println(questionData);
		}
	

		while (true) {
//			한글코딩
//
//			비로그인
//			1.로그인
//			3.문제풀기
//			4.지문출력
//			userView.printUserMenuLogout();
			int action = commonView.inputAction();
//			userView.printUserMenu();
	
			if (action == 0) {
				break;
			} else if (action == 1) {
//				   로그인 선택시
//			      뷰에게 아이디,비밀번호 받기 (뷰)
//			      모델에게 selectOne    (유저모델)
//			      실패시 실패 뷰      (뷰)
//			      성공시 성공 뷰      (뷰)
//			         로그인 정보 저장   (컨트롤)
			} else if (action == 2) {
//			    문제풀기 선택시         
//	            밸런스 게임   - > 문제 끝내기 선택   
//	            모델에게 selectOne으로 랜덤으로 받아와 (질문모델)
//	      문제를 뷰로 출력 (뷰)
//	            뷰에게 사용자가 답변을 선택받으면   (뷰)
//	           모델에게 선택 값을 insert      (답변모델)
//	            결과를 보여주기 위해      (뷰)
//	            모델에게 해당 질문의 사용자 답변들을 모두 취합해 (뷰)
//	            뷰에게 퍼센트로 나타낸다 -> 답변개수/총개수 *100   (뷰)
//	            모델에게 해당질문의 댓글들을 받아온다 ->    (댓글모델)
//	            밑에 다른 사용자들이 달아둔 댓글들을 뷰로 출력하고 (뷰)
//	            댓글을 달거나 다음문제 풀기 선택지를 준다(뷰)
//	            만약 다음 문제 풀기 선택을 하면 다음문제로 넘어간다(뷰)
//	            댓글을 단다면 (뷰)
//	            뷰에게 댓글을 입력받고 (뷰)
//	           모델에게 insert를 한다   (댓글모델)
//	            다시 모델에게 해당질문의 댓글들을 받아온다 -> 입력한 댓글과 전에 있던 댓글 확인 가능   (댓글모델)
//	            밑에 다른 사용자들이 달아둔 댓글들을 뷰로 출력하고     (뷰)
//	           댓글을 달거나 다음문제 풀기 선택지를 준다(뷰)
//
			} else if (action == 3) {
//				   지문출력 선택시
//			      1. 모든 지문 출력(뷰)
//			           2. 내가 풀었던 지문 출력 [로그인](뷰)
//			         1.모든 지문 출력 선택시
//			            모델에게 selectAll로 지문을 받아옴(질문모델)
//			            뷰에게 지문리스트 전달해 출력(뷰)
//			            사용자에게 보고싶은 지문 번호 받아오기(뷰)
//			            해당 지문리스트에 받아온 번호의 인덱스를 넣어 pk얻은뒤
//			            모델에게 selectOne 으로 지문 답변 A B개수 받아오기(답변모델)
//			            질문과 통계 뷰에서 출력(뷰)
//			            모델에게 질문에 대한 댓글을 받아온다(댓글 모델)(반복)
//			            댓글을 출력(뷰)
//			            1.댓글 추가,
//			            2.성별,
//			            3.나이
//			            4.끝내기 출력(뷰)
//			            
//			            입력값 받아오기(뷰)
//			               댓글을 선택 
//			                        뷰에게 댓글을 입력받고(뷰)
//			                       모델에게 insert를 한다(댓글 모델)
//			               2성별을 선택
//			                  1.남자
//			                     모델에서 남자 답변개수 받아오기
//			                     받아온 데이터 뷰 출력
//			                     목록으로 돌아가기
//			                  2.여자
//			                     모델에서 여자 답변개수 받아오기
//			                     받아온 데이터 뷰 출력
//			                     목록으로 돌아가기
//			                  
//			               3나이를 선택
//			                  1.20대
//			                     답변모델에서 나이 답변 개수데이터를 받아오기
//			                     받아온 데이터 뷰 출력
//			                     목록으로 돌아가기
//			                  2.30대
//			                     답변모델에서 나이 답변 개수데이터를 받아오기
//			                     받아온 데이터 뷰 출력
//			                     목록으로 돌아가기
//			               4끝내기
//			            
//			         2. 내가 풀었던 지문 출력 [로그인](뷰)
//			            로그인 상태 확인 > (컨트롤)
//			            모델에게 유저 pk로 selectAll 지문을 받아옴(질문모델) >>이너조인 사용
//			            뷰에게 지문리스트 전달해 출력(뷰)
//			            사용자에게 보고싶은 지문 번호 받아오기(뷰)
//			            해당 지문리스트에 받아온 번호의 인덱스를 넣어 pk얻은뒤
//			            모델에게 selectOne 으로 지문 답변 A B개수 받아오기(답변모델)
//			            질문과 통계 뷰에서 출력(뷰)
//			            모델에게 질문에 대한 댓글을 받아온다(댓글 모델)(반복)
//			            댓글을 출력(뷰)
//			            1.댓글 추가,
//			            2.성별,
//			            3.나이
//			            4.끝내기 출력(뷰)
//			               
//
//			               입력값 받아오기(뷰)
//			               댓글을 선택 
//			                        뷰에게 댓글을 입력받고(뷰)
//			                       모델에게 insert를 한다(댓글 모델)
//			               2성별을 선택
//			                  1.남자
//			                     모델에서 남자 답변개수 받아오기
//			                     받아온 데이터 뷰 출력
//			                     목록으로 돌아가기
//			                  2.여자
//			                     모델에서 여자 답변개수 받아오기
//			                     받아온 데이터 뷰 출력
//			                     목록으로 돌아가기
//			                  
//			               3나이를 선택
//			                  1.20대
//			                     답변모델에서 나이 답변 개수데이터를 받아오기
//			                     받아온 데이터 뷰 출력
//			                     목록으로 돌아가기
//			                  2.30대
//			                     답변모델에서 나이 답변 개수데이터를 받아오기
//			                     받아온 데이터 뷰 출력
//			                     목록으로 돌아가기
//			               4끝내기
			}

		}
	}

}
