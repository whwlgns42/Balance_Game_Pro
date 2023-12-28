CREATE TABLE questions (
    idx NUMBER PRIMARY KEY,
    title VARCHAR2(255) NOT NULL,
    content_A VARCHAR2(255) NOT NULL,
    content_B VARCHAR2(255) NOT NULL,
    writer NUMBER NOT NULL,
    reg_date DATE DEFAULT SYSDATE
);
