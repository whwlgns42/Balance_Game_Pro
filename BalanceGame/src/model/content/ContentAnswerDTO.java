package model.content;

public class ContentAnswerDTO {
    private int idx;
    private int user_idx; // USERS 테이블의 IDX를 참조
    private int quest_idx; // QUESTIONS 테이블의 IDX를 참조
    private String content;
    private String searchCondition;
    private int answerCntA;
    private int answerCntB;
    private int genderCondition;
    private int minAge;
    private int maxAge;
    

    
   
    // 질문 출력해요 질문테이블에서 받아서 출력
    // 유저가 A선택 질문테이블 선택한 결과 이게 어디로 가냐 유저답변 테이블로 결과가 이동한다.
    // 결과를 성별별 나이별으로 종합을 해서 다시 출력해야함 
    // 유저답변 개수를 반환해야함 DAO 만들어줘야함.
    // 뷰로 줄때는 질문DTO를 줘야한다.
    // 질문을 데이터를 넘겨줄때 답변데이터 개수를 같이 넘겨서 줘야한다
    // 댓글 DTO에 카운트를 만들어서 한다.
    // 질문하나에 대해 답변개수들 반환 >> 질문pk 셀렉원으로 조건달고 선택지A인지 B인지 count함수로 (데이터베이스) 확인 개수 반환
    
    
    
    public int getGenderCondition() {
		return genderCondition;
	}

	public void setGenderCondition(int genderCondition) {
		this.genderCondition = genderCondition;
	}

	public int getMinAge() {
		return minAge;
	}

	public void setMinAge(int minAge) {
		this.minAge = minAge;
	}

	public int getMaxAge() {
		return maxAge;
	}

	public void setMaxAge(int maxAge) {
		this.maxAge = maxAge;
	}

	public int getIdx() {
        return idx;
    }

    public int getAnswerCntA() {
		return answerCntA;
	}

	public void setAnswerCntA(int answerCntA) {
		this.answerCntA = answerCntA;
	}

	public int getAnswerCntB() {
		return answerCntB;
	}

	public void setAnswerCntB(int answerCntB) {
		this.answerCntB = answerCntB;
	}

	public void setIdx(int idx) {
        this.idx = idx;
    }

    public int getUser_idx() {
        return user_idx;
    }

    public void setUser_idx(int user_idx) {
        this.user_idx = user_idx;
    }

    public int getQuest_idx() {
        return quest_idx;
    }

    public void setQuest_idx(int quest_idx) {
        this.quest_idx = quest_idx;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSearchCondition() {
        return searchCondition;
    }

    public void setSearchCondition(String searchCondition) {
        this.searchCondition = searchCondition;
    }
}