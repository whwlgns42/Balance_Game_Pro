package controller;

import model.UserComment.UserCommentDAO;
import model.content.ContentAnswerDAO;
import model.question.QuestionDAO;
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
		System.out.println("ddddd");
	}
	
}
