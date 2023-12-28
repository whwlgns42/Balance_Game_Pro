package controller;

import java.util.ArrayList;

import model.UserComment.UserCommentDAO;
import model.content.ContentAnswerDAO;
import model.crawlling.Crawlling;
import model.question.QuestionDAO;
import model.question.QuestionDTO;
import model.user.UserDAO;
import model.user.UserDTO;

public class Ctrl {
//시작
	private ContentAnswerDAO answerDAO;
	private QuestionDAO questionDAO;
	private UserCommentDAO commentDAO;
	private UserDAO userDAO;
	private UserDTO loginINFO;
	
	
	public Ctrl(){
		answerDAO=new ContentAnswerDAO();
		questionDAO=new QuestionDAO();
		commentDAO=new UserCommentDAO();
		userDAO=new UserDAO();
		loginINFO=null;
		
	}
	public void start() {
		ArrayList<QuestionDTO> crawResults =  Crawlling.crwalling();
		for (QuestionDTO questionData : crawResults) { // TODO 컨트롤에서 데이터 확인후 나중에 삭제해주세요
			System.out.println(questionData);
		}
	
	}
	
}
