package controller;

import java.util.ArrayList;

import model.UserComment.UserCommentDAO;
import model.UserComment.UserCommentDTO;
import model.content.ContentAnswerDAO;
import model.content.ContentAnswerDTO;
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
		userView = new UserView();
		commonView = new CommonView();
		loginINFO = null;
	}

	public void start() {
//		ArrayList<QuestionDTO> crawResults = Crawlling.crwalling();
//		for (QuestionDTO questionData : crawResults) { // TODO 컨트롤에서 데이터 확인후 나중에 삭제해주세요
//			System.out.println(questionData);
//			questionData.setWriter(0);
//			questionDAO.insert(questionData);
//		}

		while (true) {
//			한글코딩
//
//			비로그인
//			1.게임하기
//			2.지문보기
//		   	3.로그인
			// 4.로그아웃
			// 5.회원 탈퇴

			userView.printUserMenu();
			if (loginINFO == null) {
				userView.printLoginUserMenu();
			} else {
				userView.printLogoutUserMenu();
			}
			int action = commonView.userMenuAction();

			if (action == 0) {
				break;
			} else if (action == 1) {
				Boolean flag = true;
				while (flag) {
//			    문제풀기 선택시         
//	            밸런스 게임   - > 문제 끝내기 선택   
					
					QuestionDTO questionDTO = new QuestionDTO();
					questionDTO.setSearchCondition("문제생성");
//	            모델에게 selectOne으로 랜덤으로 받아와 (질문모델)
					questionDTO = questionDAO.selectOne(questionDTO);
//	     		 문제를 뷰로 출력 (뷰)
					userView.selectOne(questionDTO);

					ContentAnswerDTO data = new ContentAnswerDTO();

					data.setContent(commonView.qustionAction());

//	            뷰에게 사용자가 답변을 선택받으면   (뷰)
					data.setQuest_idx(questionDTO.getQid());// 질문 pk 저장
					if (loginINFO != null) {
						data.setUser_idx(loginINFO.getIdx());// 유저 pk 저장
					} else {
						data.setUser_idx(-1);
					}
//	           모델에게 선택 값을 insert      (답변모델)
					if (!answerDAO.insert(data)) {
						userView.printFalse();
						continue;
					}
//	            결과를 보여주기 위해  
//	            모델에게 해당 질문의 사용자 답변들을 모두 취합해 (답변모델)
					ContentAnswerDTO answerDTO = new ContentAnswerDTO();

					answerDTO.setSearchCondition("답변개수");
					answerDTO.setQuest_idx(questionDTO.getQid());
					answerDTO = answerDAO.selectOne(answerDTO);
					System.out.println(answerDTO.getAnswerCntA());
//	            뷰에게 퍼센트로 나타낸다 -> 답변개수/총개수 *100   (뷰)
					// 결과 확인

					while (true) {
						userView.printAnswerResult(answerDTO, questionDTO);
						UserCommentDTO commentDTO = new UserCommentDTO();
						commentDTO.setQuest_idx(questionDTO.getQid());
						ArrayList<UserCommentDTO> comments = commentDAO.selectAll(commentDTO);
//	            모델에게 해당질문의 댓글들을 받아온다 ->    (댓글모델)

						userView.printComment(comments);
//	            밑에 다른 사용자들이 달아둔 댓글들을 뷰로 출력하고 (뷰)

//	            댓글을 달거나 다음문제 풀기 선택지를 준다(뷰)
						action = userView.inputNext();
						if (action == 1) {
//	            만약 다음 문제 풀기 선택을 하면 다음문제로 넘어간다(뷰)
							break;
						} else if (action == 2) {
							flag = false;
							break;
						} else if (action == 3) {
							if (loginINFO == null) {
								// 로그인이 필요한 기능입니다
								userView.printLogin(loginINFO);
								continue;
							}
//	           				댓글을 단다면 (뷰)
							// 뷰에게 댓글을 입력받고 (뷰)
							commentDTO = userView.writeComment();
							commentDTO.setQuest_idx(questionDTO.getQid());
							commentDTO.setUser_idx(loginINFO.getIdx());
//			           		모델에게 insert를 한다   (댓글모델)
							if (!commentDAO.insert(commentDTO)) {
								userView.printFalse();
								continue;
							}
//				            다시 모델에게 해당질문의 댓글들을 받아온다 -> 입력한 댓글과 전에 있던 댓글 확인 가능   (댓글모델)
//				            밑에 다른 사용자들이 달아둔 댓글들을 뷰로 출력하고     (뷰)
//				           댓글을 달거나 다음문제 풀기 선택지를 준다(뷰)
						}
					}

				}

			} else if (action == 2) {

//				   지문출력 선택시
//			      1. 모든 지문 출력(뷰)
//		           2. 내가 풀었던 지문 출력 [로그인](뷰)
				userView.loginListMenu(loginINFO);
				action = commonView.loginListAction();
				if (action == 0) {
					continue;
				} else if (action == 1) {
//			         1. 내가 풀었던 지문 출력 [로그인](뷰)
//		            로그인 상태 확인 > (컨트롤)
					if (loginINFO == null) {
						userView.printLogin(loginINFO);
						continue;
					}
//		            모델에게 유저 pk로 selectAll 지문을 받아옴(질문모델)

					ContentAnswerDTO answerDTO = new ContentAnswerDTO();
					answerDTO.setSearchCondition("내답변");
					answerDTO.setUser_idx(loginINFO.getIdx());
					ArrayList<ContentAnswerDTO> cDtos = answerDAO.selectAll(answerDTO);

					ArrayList<QuestionDTO> datas = new ArrayList<QuestionDTO>();
					for (int i = 0; i < cDtos.size(); i++) {
						QuestionDTO questionDTO = new QuestionDTO();
						questionDTO.setSearchCondition("문제보기");
						questionDTO.setQid(cDtos.get(i).getQuest_idx());
						datas.add(questionDAO.selectOne(questionDTO));
					}

//		            뷰에게 지문리스트 전달해 출력(뷰)
					userView.printQuestions(datas);

//		            사용자에게 보고싶은 지문 번호 받아오기(뷰)
					action = commonView.questions(datas);

//		            해당 지문리스트에 받아온 번호의 인덱스를 넣어 pk얻은뒤
//		            모델에게 selectOne 으로 지문 답변 A B개수 받아오기(답변모델)
					QuestionDTO questionDTO = new QuestionDTO();
					questionDTO.setQid(action-1);
					answerDTO.setSearchCondition("답변개수");
					answerDTO.setQuest_idx(questionDTO.getQid());
					answerDAO.selectOne(answerDTO);

					while (true) {
//			            질문과 통계 뷰에서 출력(뷰)
						userView.printAnswerResult(answerDTO, questionDTO);
						UserCommentDTO commentDTO = new UserCommentDTO();
						commentDTO.setQuest_idx(questionDTO.getQid());

//			            모델에게 질문에 대한 댓글을 받아온다(댓글 모델)(반복)
						ArrayList<UserCommentDTO> comments = commentDAO.selectAll(commentDTO);
//			            댓글을 출력(뷰)
						userView.printComment(comments);
						action = userView.inputPrint();
						if (action == 0) {// 0.끝내기 출력(뷰)
//				               끝내기
							break;
						} else if (action == 1) {// 1.댓글 추가
							
							if (loginINFO == null) {
								// 로그인이 필요한 기능입니다
								userView.printLogin(loginINFO);
								continue;
							}
//	           				댓글을 단다면 (뷰)
							// 뷰에게 댓글을 입력받고 (뷰)
							commentDTO = userView.writeComment();
							commentDTO.setQuest_idx(questionDTO.getQid());
							commentDTO.setUser_idx(loginINFO.getIdx());
//			           		모델에게 insert를 한다   (댓글모델)
							if (!commentDAO.insert(commentDTO)) {
								userView.printFalse();
								continue;
							}
							
						} else if (action == 2) {// 2.성별

//				               2성별을 선택
//			                  1.남자
//			                     모델에서 남자 답변개수 받아오기
//			                     받아온 데이터 뷰 출력
//			                     목록으로 돌아가기
//			                  2.여자
//			                     모델에서 여자 답변개수 받아오기
//			                     받아온 데이터 뷰 출력
//			                     목록으로 돌아가기
//			                  
							
						} else if (action == 3) {// 3.나이
//				               3나이를 선택
//			                  1.20대
//			                     답변모델에서 나이 답변 개수데이터를 받아오기
//			                     받아온 데이터 뷰 출력
//			                     목록으로 돌아가기
//			                  2.30대
//			                     답변모델에서 나이 답변 개수데이터를 받아오기
//			                     받아온 데이터 뷰 출력
//			                     목록으로 돌아가기
						}
					}

				} else if (action == 2) {
//			         2.모든 지문 출력 선택시
//		            모델에게 selectAll로 지문을 받아옴(질문모델)
					QuestionDTO questionDTO=new QuestionDTO();
					questionDTO.setSearchCondition("문제전체조회");
					ArrayList<QuestionDTO>datas= questionDAO.selectAll(questionDTO);
//		            뷰에게 지문리스트 전달해 출력(뷰)
					userView.printQuestions(datas);
					
//		            사용자에게 보고싶은 지문 번호 받아오기(뷰)
					action= commonView.questions(datas);
					


//			            해당 지문리스트에 받아온 번호의 인덱스를 넣어 pk얻은뒤
					questionDTO=datas.get(action-1);
//			            모델에게 selectOne 으로 지문 답변 A B개수 받아오기(답변모델)
					ContentAnswerDTO answerDTO=new ContentAnswerDTO();
					answerDTO.setQuest_idx(questionDTO.getQid());
					answerDTO.setSearchCondition("답변개수");
					answerDTO = answerDAO.selectOne(answerDTO);
//			            질문과 통계 뷰에서 출력(뷰)
					
					
					while (true) {
//			            질문과 통계 뷰에서 출력(뷰)
						userView.printAnswerResult(answerDTO, questionDTO);
						
						UserCommentDTO commentDTO = new UserCommentDTO();
						commentDTO.setQuest_idx(questionDTO.getQid());

//			            모델에게 질문에 대한 댓글을 받아온다(댓글 모델)(반복)
						ArrayList<UserCommentDTO> comments = commentDAO.selectAll(commentDTO);
//			            댓글을 출력(뷰)
						userView.printComment(comments);
						action = userView.inputPrint();
						if (action == 0) {// 0.끝내기 출력(뷰)
//				               끝내기
							break;
						} else if (action == 1) {// 1.댓글 추가
							
							if (loginINFO == null) {
								// 로그인이 필요한 기능입니다
								userView.printLogin(loginINFO);
								continue;
							}
//	           				댓글을 단다면 (뷰)
							// 뷰에게 댓글을 입력받고 (뷰)
							commentDTO = userView.writeComment();
							commentDTO.setQuest_idx(questionDTO.getQid());
							commentDTO.setUser_idx(loginINFO.getIdx());
//			           		모델에게 insert를 한다   (댓글모델)
							if (!commentDAO.insert(commentDTO)) {
								userView.printFalse();
								continue;
							}
							
						} else if (action == 2) {// 2.성별
//				               2성별을 선택
							action= userView.genderPrint();
							if(action==1) {
//				                  1.남자
								ContentAnswerDTO contentAnswerDTO =new ContentAnswerDTO();
								contentAnswerDTO.setSearchCondition("성별");
								contentAnswerDTO.setGenderCondition("남");
//			                     모델에서 남자 답변개수 받아오기
								contentAnswerDTO = answerDAO.selectOne(contentAnswerDTO);
//			                     받아온 데이터 뷰 출력
								userView.printAnswerResult(contentAnswerDTO, questionDTO);

//			                     목록으로 돌아가기
								
							}

							else if(action==2) {
//				                  2.여자
//			                     모델에서 여자 답변개수 받아오기
//			                     받아온 데이터 뷰 출력
//			                     목록으로 돌아가기	
							}


							
						} else if (action == 3) {// 3.나이
//				               3나이를 선택
//			                  1.20대
//			                     답변모델에서 나이 답변 개수데이터를 받아오기
//			                     받아온 데이터 뷰 출력
//			                     목록으로 돌아가기
//			                  2.30대
//			                     답변모델에서 나이 답변 개수데이터를 받아오기
//			                     받아온 데이터 뷰 출력
//			                     목록으로 돌아가기
						}
					}

				}

			} else if (action == 3) {
//				   로그인 선택시
//			      뷰에게 아이디,비밀번호 받기 (뷰)
				UserDTO dto = userView.signIn();

//			      모델에게 selectOne    (유저모델)
				dto = userDAO.selectOne(dto);
				if (dto == null) {
//				      실패시 실패 뷰      (뷰)
					userView.printFalse();
					continue;
				}
//			      성공시 성공 뷰      (뷰)
				userView.printTrue();
//		         로그인 정보 저장   (컨트롤)
				loginINFO = dto;

			}else if(action==4) {
				// 4.로그아웃
				userView.printLogout(loginINFO);
				loginINFO=null;
			}
			else if(action==5) {
				if(loginINFO==null) {
					userView.printLogin(loginINFO);
					continue;
				}
				// 5.회원 탈퇴
				userView.printTrue();
				userDAO.delete(loginINFO);
			}

		}
	}

}
