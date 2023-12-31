package model.UserComment;

public class UserCommentDTO {
    private int idx;
    private int quest_idx; // questions 테이블의 idx를 참조
    private int user_idx; // users 테이블의 idx를 참조
    private String user_comment;
    private String searchCondition;
    private String userName;
    private String userId;
    
    
    
    
    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSearchCondition() {
        return searchCondition;
    }

    public void setSearchCondition(String searchCondition) {
        this.searchCondition = searchCondition;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public int getQuest_idx() {
        return quest_idx;
    }

    public void setQuest_idx(int quest_idx) {
        this.quest_idx = quest_idx;
    }

    public int getUser_idx() {
        return user_idx;
    }

    public void setUser_idx(int user_idx) {
        this.user_idx = user_idx;
    }

    public String getUser_comment() {
        return user_comment;
    }

    public void setUser_comment(String user_comment) {
        this.user_comment = user_comment;
    }
}