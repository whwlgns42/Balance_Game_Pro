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
		userView.printQuestionResult();//질문 결과 출력
		userView.printAnswerResult(answerDTO, questionDTO);
		UserCommentDTO commentDTO = new UserCommentDTO();
		commentDTO.setQuest_idx(questionDTO.getQid());//질문에 대한 댓글 가져오기
		ArrayList<UserCommentDTO> comments = commentDAO.selectAll(commentDTO);
		for (UserCommentDTO data : comments) {//댓글에 대한 유저 닉네임 가져오기
			UserDTO user = new UserDTO();
			user.setIdx(data.getUser_idx());
			user.setSearchCondition("유저조회");
			user = userDAO.selectOne(user);
			if (user == null) {//유저가 null로 나오면 탈퇴한 사용자
				data.setUserId("탈퇴한 사용자");
				data.setUserName("탈퇴한 사용자");
			} else {
				data.setUserId(user.getId());
				data.setUserName(user.getName());
			}
		}
		//모델에게 해당질문의 댓글들을 받아온다 ->    (댓글모델)
		userView.printCommentResult();
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
		answerDTO = answerDAO.selectOne(answerDTO);//결과 출력 때 사용할 답변개수 가져오기
		return answerDTO;

	}
	public void crwalling() {
		ArrayList<QuestionDTO> crawResults = Crawlling.crwalling();
		for (QuestionDTO questionData : crawResults) { // TODO 컨트롤에서 데이터 확인후 나중에 삭제해주세요
			System.out.println(questionData);
			questionData.setWriter(0);
			questionDAO.insert(questionData);
		}
	}

	public void start() {
		// 크롤링 데이터 넣기
		//crwalling();

		while (true) {
			// 1.게임하기
			// 2.지문보기
			// 3.로그인
			// 4.로그아웃[로그인]
			// 5.회원 탈퇴[로그인]
			userView.loginStatus(loginINFO);
			userView.printMenu();
			userView.printUserMenu();
			if (loginINFO == null) {//로그인 안되어 있을때 뷰
				userView.printLoginUserMenu();
			} else {//로그인 되어 있을때 뷰
				userView.printLogoutUserMenu();
			}
			userView.printLine();
			int action = commonView.userMenuAction();

			if (action == 0) {
				break;
			} else if (action == 1) {// 게임하기 선택시
				ArrayList<Integer> successList = new ArrayList<Integer>();//푼 문제 pk모음
				QuestionDTO dto = new QuestionDTO();
				dto.setSearchCondition("문제전체조회");
				int total = questionDAO.selectAll(dto).size();
				Boolean start = true;
				while (start) {
					
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
						//푼 문제 리스트에서 방금 뽑은 질문의 pk와 비교해 해당 문제를 풀었다면 다시뽑기를 한다 
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

					data.setContent(commonView.qustionAction());//답변 입력

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

					ContentAnswerDTO answerDTO = new ContentAnswerDTO();//내가 답변했던 질문들을 받아오기위해 DTO를 선언해
					answerDTO.setSearchCondition("내답변");
					answerDTO.setUser_idx(loginINFO.getIdx());//유저의 IDX를 넣어
					ArrayList<ContentAnswerDTO> cDtos = answerDAO.selectAll(answerDTO);//중복이 제거된 내가 푼 답변 리스트를 가져온다
					if(cDtos==null) {
						userView.printEmptyData();
						continue;
					}
					ArrayList<QuestionDTO> datas = new ArrayList<QuestionDTO>();//문제를 가져와 리스트에 저장할 변수
					for (int i = 0; i < cDtos.size(); i++) {
						QuestionDTO questionDTO = new QuestionDTO();//문제를 가져오기 위한 DTO
						questionDTO.setSearchCondition("문제보기");
						questionDTO.setQid(cDtos.get(i).getQuest_idx());
						datas.add(questionDAO.selectOne(questionDTO));//문제를 가져와 저장한다
					}
					//문제를 여러게 가져오는데 selectAll이 아닌이유 
					//내가 푼 답변리스트에 있는 질문 pk 들로 질문을 조회를 할때 인자가 DTO이기 때문에
					//selectOne으로 답변리스트에서 질문 pk로 조회를 해서 질문을 저장한다 
					
					// 뷰에게 지문리스트 전달해 출력(뷰)
					userView.printQuestions(datas);

					// 사용자에게 보고싶은 지문 번호 받아오기(뷰)
					action = commonView.questions(datas);

				
					QuestionDTO questionDTO = new QuestionDTO();
					questionDTO.setQid(datas.get(action - 1).getQid());
					// 해당 지문리스트에 받아온 번호의 인덱스를 넣어 pk얻은뒤
					questionDTO.setSearchCondition("문제보기");
					questionDTO = questionDAO.selectOne(questionDTO);
					// 모델에게 selectOne 으로 지문 답변 A B개수 받아오기(답변모델)
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
								break;
							}
							// 댓글을 단다면 (뷰)
							commentInsert(questionDTO);

						}
					}

				}

			} else if (action == 3) {
				// 로그인 선택시
				if(loginINFO!=null) {
					userView.noNumber();
					continue;
				}
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
				userView.loginSuccess();
				// 로그인 정보 저장 (컨트롤)
				loginINFO = dto;

			} else if (action == 4) {
				if(loginINFO==null) {
					userView.noNumber();
					continue;
				}
				// 4.로그아웃
				userView.printLogout(loginINFO);
				loginINFO = null;
			} else if (action == 5) {
				if (loginINFO == null) {
					userView.noNumber();
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


//컨트롤러 발표를 시작하겠습니다
//콘솔과 함께 발표를 하겠습니다
//클라이언트를 실행하게 되면 해당 메뉴가 출려 되게 됩니다
//로그인 되었을 땐 해당 메뉴가 나오고
//로그인을 하였을 땐 이런 메뉴가 나오게 됩니다
//게임하기를 선택하면 게임을 시작하게 됩니다
//이때 게임하기를 할때 가장 중요하게 생각 했던점이 질문의 순서는 랜덤으로 나오면서 
//랜덤으로 나온 질문이 중복되지 않게 만드는 것이 중요했습니다
//그래서 푼 문제를 저장하고 랜덤으로 뽑은 질문이 푼 문제 리스트에 있는지 확인후 
//있으면 다시 뽑고 없으면 문제를 출력하게됩니다
//그리고 답변을 입력 받으면 질문pk랑 유저 pk를 답변 dto 넣어 데이터를 저장하게 됩니다
//
//그 뒤 다른 사용자들 고른 답변을 통계를 내어 A답변은 얼마 B답변은 얼마를 출력하기위해
//해당 질문의 사용자 답변을 모두 취합해 통계를 내어 출력 하게 됩니다
//결과를 출력을 할때 질문의 댓글도 출력을 해야하기 때문에 질문에 대한 댓글을 가져오게 됩니다
//그리고 해당 댓글을 작성한 유저 닉네임을 가져오기 위해 댓글에 저장되어있는 유저 pk로 유저DAO로
//해당 유저를 찾아 댓글을 출력할때 유저 닉네임으로 출력하게 만들어 보았습니다 
//하지만 유저를 찾지 못하면 탈퇴한 사용자기 때문에 탈퇴한 사용자를 출력하게 하였습니다
//
//그리고 댓글을 달았을때 유저에게 댓글이 잘 들어간것을 확인 시켜주기위해 
//반복문으로 댓글을 갱신해 주었습니다 
//이제 여기서 1을 입력하면 반복문을 돌아 다시 문제를 뽑고 
//2를 입력하면 게임을 종료하기 위해 스타트 변수를 펄스로 그리고 반복문을 탈출합니다
//
//이제 사용자가 지문 출력을 선택하게 되면
//내가 풀었던 지문
//모든 지문 보기
//메뉴가 나오는데
//이때 내가 풀었던 지문을 선택하면
//해당 유저가 푼 지문만 가져 와야 하기 때문에
//답변 DAO에서 해당유저의 답변의 질문 pk을 중복 안되게 받아오고
//그 질문 pk로 질문의 데이터를 리스트에 저장하여 출력하게 됩니다
//그리고 보고싶은 지문의 번호를 받아와 똑같이 ab개수를 받아와 출력을 하게 됩니다
//그리고 댓글을 입력할수있게 구성하였습니다
//
//다음 모든 질문 출력시에는 문제 전체를 가져와 출력을하고
//위와 같이 질문과 퍼센트 댓글을 출력하게 됩니다
//
//다음 로그인시 로그아웃 상태에서 접근을 막기위해 확인을 하고
//로그인을 하게 됩니다
//
//로그아웃을 유저 dto를 널로 바꿔주어 로그아웃 상태로 만들어줍니다
//
//회원 탈퇴시에 유저를 지우고 로그아웃을 시켜줍니다 
