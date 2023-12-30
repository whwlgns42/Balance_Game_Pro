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

	public void comment(ContentAnswerDTO answerDTO, QuestionDTO questionDTO) {
		userView.printAnswerResult(answerDTO, questionDTO);
		UserCommentDTO commentDTO = new UserCommentDTO();
		commentDTO.setQuest_idx(questionDTO.getQid());
		ArrayList<UserCommentDTO> comments = commentDAO.selectAll(commentDTO);
		for (UserCommentDTO data : comments) {
			UserDTO user = new UserDTO();
			user.setIdx(data.getUser_idx());
			user.setSearchCondition("유저조회");
			user = userDAO.selectOne(user);
			if (user == null) {
				data.setUserId("탈퇴한 사용자");
				data.setUserName("탈퇴한 사용자");
			} else {
				data.setUserId(user.getId());
				data.setUserName(user.getName());
			}
		}
//모델에게 해당질문의 댓글들을 받아온다 ->    (댓글모델)
		userView.printComment(comments);
//밑에 다른 사용자들이 달아둔 댓글들을 뷰로 출력하고 (뷰)
	}

	public void commentInsert(QuestionDTO questionDTO) {
		// 뷰에게 댓글을 입력받고 (뷰)
		UserCommentDTO commentDTO = userView.writeComment();
		commentDTO.setQuest_idx(questionDTO.getQid());
		commentDTO.setUser_idx(loginINFO.getIdx());
//   		모델에게 insert를 한다   (댓글모델)
		if (!commentDAO.insert(commentDTO)) {
			userView.printFalse();
		}
	}

	public ContentAnswerDTO answerCnt(QuestionDTO questionDTO) {
		ContentAnswerDTO answerDTO = new ContentAnswerDTO();
		answerDTO.setQuest_idx(questionDTO.getQid());
		answerDTO.setSearchCondition("답변개수");
		answerDTO = answerDAO.selectOne(answerDTO);
		return answerDTO;

	}

	public void start() {
		// 크롤링 데이터 넣기
//		ArrayList<QuestionDTO> crawResults = Crawlling.crwalling();
//		for (QuestionDTO questionData : crawResults) { // TODO 컨트롤에서 데이터 확인후 나중에 삭제해주세요
//			System.out.println(questionData);
//			questionData.setWriter(0);
//			questionDAO.insert(questionData);
//		}

		while (true) {
			// 1.게임하기
			// 2.지문보기
			// 3.로그인
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
				ArrayList<Integer> successList = new ArrayList<Integer>();
				QuestionDTO dto = new QuestionDTO();
				dto.setSearchCondition("문제전체조회");
				int total = questionDAO.selectAll(dto).size();
				Boolean start = true;
				while (start) {
					// 문제풀기 선택시
					// 밸런스 게임 - > 문제 끝내기 선택

					if (successList.size() >= total) {
						userView.finish();
						break;
					}

					QuestionDTO questionDTO = new QuestionDTO();
					questionDTO.setSearchCondition("문제생성");
					// 모델에게 selectOne으로 랜덤으로 받아와 (질문모델)
					questionDTO = questionDAO.selectOne(questionDTO);

					Boolean flag = false;
					for (int data : successList) {
						if (data == questionDTO.getQid()) {
							flag = true;
						}
					}
					if (flag) {
						continue;
					}
					successList.add(questionDTO.getQid());

					// 문제를 뷰로 출력 (뷰)
					userView.selectOne(questionDTO);

					ContentAnswerDTO data = new ContentAnswerDTO();

					data.setContent(commonView.qustionAction());

					// 뷰에게 사용자가 답변을 선택받으면 (뷰)
					data.setQuest_idx(questionDTO.getQid());// 질문 pk 저장
					if (loginINFO != null) {
						data.setUser_idx(loginINFO.getIdx());// 유저 pk 저장
					} else {
						data.setUser_idx(-1);
					}
					// 모델에게 선택 값을 insert (답변모델)
					if (!answerDAO.insert(data)) {
						userView.printFalse();
						continue;
					}
					// 결과를 보여주기 위해
					// 모델에게 해당 질문의 사용자 답변들을 모두 취합해 (답변모델)
					ContentAnswerDTO answerDTO = answerCnt(questionDTO);

					// 뷰에게 퍼센트로 나타낸다 -> 답변개수/총개수 *100 (뷰)
					// 결과 확인

					while (true) {
						comment(answerDTO, questionDTO);

						// 댓글을 달거나 다음문제 풀기 선택지를 준다(뷰)
						action = userView.inputNext();
						if (action == 1) {
							// 만약 다음 문제 풀기 선택을 하면 다음문제로 넘어간다(뷰)
							break;
						} else if (action == 2) {
							start = false;
							break;
						} else if (action == 3) {
							if (loginINFO == null) {
								// 로그인이 필요한 기능입니다
								userView.printLogin(loginINFO);
								continue;
							}
							// 댓글을 단다면 (뷰)
							commentInsert(questionDTO);
						}
					}

				}

			} else if (action == 2) {

				// 지문출력 선택시
				// 1. 내가 풀었던 지문 출력 [로그인](뷰)
				// 2. 모든 지문 출력(뷰)
				
				userView.loginListMenu(loginINFO);
				action = commonView.loginListAction();
				if (action == 0) {
					continue;
				} else if (action == 1) {
					// 1. 내가 풀었던 지문 출력 [로그인](뷰)
					// 로그인 상태 확인 > (컨트롤)
					if (loginINFO == null) {
						userView.printLogin(loginINFO);
						continue;
					}
					// 모델에게 유저 pk로 selectAll 지문을 받아옴(질문모델)

					ContentAnswerDTO answerDTO = new ContentAnswerDTO();
					answerDTO.setSearchCondition("내답변");
					answerDTO.setUser_idx(loginINFO.getIdx());
					ArrayList<ContentAnswerDTO> cDtos = answerDAO.selectAll(answerDTO);
					if(cDtos==null) {
						System.out.println("푼 문제가 없습니다");
						continue;
					}
					ArrayList<QuestionDTO> datas = new ArrayList<QuestionDTO>();
					for (int i = 0; i < cDtos.size(); i++) {
						QuestionDTO questionDTO = new QuestionDTO();
						questionDTO.setSearchCondition("문제보기");
						questionDTO.setQid(cDtos.get(i).getQuest_idx());
						datas.add(questionDAO.selectOne(questionDTO));
					}

					// 뷰에게 지문리스트 전달해 출력(뷰)
					userView.printQuestions(datas);

					// 사용자에게 보고싶은 지문 번호 받아오기(뷰)
					action = commonView.questions(datas);

					// 해당 지문리스트에 받아온 번호의 인덱스를 넣어 pk얻은뒤
					// 모델에게 selectOne 으로 지문 답변 A B개수 받아오기(답변모델)
					QuestionDTO questionDTO = new QuestionDTO();
					questionDTO.setQid(datas.get(action - 1).getQid());
					questionDTO.setSearchCondition("문제보기");
					questionDTO = questionDAO.selectOne(questionDTO);

					answerDTO = answerCnt(questionDTO);

					while (true) {
						comment(answerDTO, questionDTO);
						action = userView.inputPrint();
						if (action == 0) {// 0.끝내기 출력(뷰)
							// 끝내기
							break;
						} else if (action == 1) {// 1.댓글 추가
							if (loginINFO == null) {
								// 로그인이 필요한 기능입니다
								userView.printLogin(loginINFO);
								continue;
							}
							// 댓글을 단다면 (뷰)
							commentInsert(questionDTO);
						}
					}

				} else if (action == 2) {
					// 2.모든 지문 출력 선택시
					// 모델에게 selectAll로 지문을 받아옴(질문모델)
					QuestionDTO questionDTO = new QuestionDTO();
					questionDTO.setSearchCondition("문제전체조회");
					ArrayList<QuestionDTO> datas = questionDAO.selectAll(questionDTO);
					// 뷰에게 지문리스트 전달해 출력(뷰)
					userView.printQuestions(datas);

					// 사용자에게 보고싶은 지문 번호 받아오기(뷰)
					action = commonView.questions(datas);

					// 해당 지문리스트에 받아온 번호의 인덱스를 넣어 pk얻은뒤
					questionDTO = datas.get(action - 1);
					// 모델에게 selectOne 으로 지문 답변 A B개수 받아오기(답변모델)

					ContentAnswerDTO answerDTO = answerCnt(questionDTO);
//질문과 통계 뷰에서 출력(뷰)

					while (true) {
						comment(answerDTO, questionDTO);
						action = userView.inputPrint();
						if (action == 0) {// 0.끝내기 출력(뷰)
							// 끝내기
							break;
						} else if (action == 1) {// 1.댓글 추가
							if (loginINFO == null) {
								// 로그인이 필요한 기능입니다
								userView.printLogin(loginINFO);
								continue;
							}
							// 댓글을 단다면 (뷰)
							commentInsert(questionDTO);

						}
					}

				}

			} else if (action == 3) {
				// 로그인 선택시
				// 뷰에게 아이디,비밀번호 받기 (뷰)
				UserDTO dto = userView.signIn();
				dto.setSearchCondition("로그인");
				// 모델에게 selectOne (유저모델)
				dto = userDAO.selectOne(dto);
				if (dto == null) {
					// 실패시 실패 뷰 (뷰)
					userView.printFalse();
					continue;
				}
				// 성공시 성공 뷰 (뷰)
				userView.printTrue();
				// 로그인 정보 저장 (컨트롤)
				loginINFO = dto;

			} else if (action == 4) {
				// 4.로그아웃
				userView.printLogout(loginINFO);
				loginINFO = null;
			} else if (action == 5) {
				if (loginINFO == null) {
					userView.printLogin(loginINFO);
					continue;
				}
				// 5.회원 탈퇴
				userView.printTrue();
				userDAO.delete(loginINFO);
				loginINFO = null;
			}

		}
	}

}
