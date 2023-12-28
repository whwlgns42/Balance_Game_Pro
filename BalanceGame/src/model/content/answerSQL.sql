CREATE TABLE user_answers (
    idx NUMBER PRIMARY KEY,
    user_idx NUMBER NOT NULL, --REFERENCES users(idx),
    quest_idx NUMBER NOT NULL,-- REFERENCES questions(idx),
    content VARCHAR2(255) NOT NULL,
    reg_date DATE DEFAULT SYSDATE
);