-- 회원기본
CREATE TABLE MEMBERS (
	MNO      INTEGER      NOT NULL COMMENT '회원일련번호', -- 회원일련번호
	EMAIL    VARCHAR(40)  NOT NULL COMMENT '이메일', -- 이메일
	PWD      VARCHAR(100) NOT NULL COMMENT '암호', -- 암호
	MNAME    VARCHAR(50)  NOT NULL COMMENT '이름', -- 이름
	CRE_DATE DATETIME     NOT NULL COMMENT '가입일', -- 가입일
	MOD_DATE DATETIME     NOT NULL COMMENT '마지막암호변경일' -- 마지막암호변경일
)
COMMENT '회원기본';

-- 회원기본
ALTER TABLE MEMBERS
	ADD CONSTRAINT PK_MEMBERS -- 회원기본 기본키
		PRIMARY KEY (
			MNO -- 회원일련번호
		);

-- 회원기본 유니크 인덱스
CREATE UNIQUE INDEX UIX_MEMBERS
	ON MEMBERS ( -- 회원기본
		EMAIL ASC -- 이메일
	);

 CREATE SEQUENCE  "DDMS"."MEMBERS_MNO"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
-- 데이터 준비	
INSERT INTO MEMBERS(EMAIL,PWD,MNAME,CRE_DATE,MOD_DATE, MNO)
VALUES ('s1@test.com','1111','홍길동',sysdate,sysdate, MEMBERS_MNO.nextval);

INSERT INTO MEMBERS(EMAIL,PWD,MNAME,CRE_DATE,MOD_DATE, MNO)
VALUES ('s2@test.com','1111','임꺽정',sysdate,sysdate, MEMBERS_MNO.nextval);

INSERT INTO MEMBERS(EMAIL,PWD,MNAME,CRE_DATE,MOD_DATE, MNO)
VALUES ('s3@test.com','1111','일지매',sysdate,sysdate, MEMBERS_MNO.nextval);

INSERT INTO MEMBERS(EMAIL,PWD,MNAME,CRE_DATE,MOD_DATE, MNO)
VALUES ('s4@test.com','1111','이몽룡',sysdate,sysdate, MEMBERS_MNO.nextval);

INSERT INTO MEMBERS(EMAIL,PWD,MNAME,CRE_DATE,MOD_DATE, MNO)
VALUES ('s5@test.com','1111','성춘향',sysdate,sysdate, MEMBERS_MNO.nextval);	

SELECT * FROM MEMBERS;

-- 프로젝트
CREATE TABLE PROJECTS (
  PNO      INTEGER      NOT NULL COMMENT '프로젝트일련번호', -- 프로젝트일련번호
  PNAME    VARCHAR(255) NOT NULL COMMENT '프로젝트명', -- 프로젝트명
  CONTENT  TEXT         NOT NULL COMMENT '설명', -- 설명
  STA_DATE DATETIME     NOT NULL COMMENT '시작일', -- 시작일
  END_DATE DATETIME     NOT NULL COMMENT '종료일', -- 종료일
  STATE    INTEGER      NOT NULL COMMENT '상태', -- 상태
  CRE_DATE DATETIME     NOT NULL COMMENT '생성일', -- 생성일
  TAGS     VARCHAR(255) NULL     COMMENT '태그' -- 태그
)
COMMENT '프로젝트';

-- 프로젝트
ALTER TABLE PROJECTS
  ADD CONSTRAINT PK_PROJECTS -- 프로젝트 기본키
    PRIMARY KEY (
      PNO -- 프로젝트일련번호
    );
CREATE SEQUENCE  "DDMS"."PROJECTS_PNO"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
-- 프로젝트멤버
CREATE TABLE PRJ_MEMBS (
  PNO      INTEGER  NOT NULL COMMENT '프로젝트일련번호', -- 프로젝트일련번호
  MNO      INTEGER  NOT NULL COMMENT '회원일련번호', -- 회원일련번호
  "LEVEL"    INTEGER  NOT NULL COMMENT '등급', -- 등급
  STATE    INTEGER  NOT NULL COMMENT '상태', -- 상태
  MOD_DATE DATETIME NOT NULL COMMENT '상태변경일' -- 상태변경일
)
COMMENT '프로젝트멤버';

-- 프로젝트멤버
ALTER TABLE PRJ_MEMBS
  ADD CONSTRAINT PK_PRJ_MEMBS -- 프로젝트멤버 기본키
    PRIMARY KEY (
      PNO, -- 프로젝트일련번호
      MNO  -- 회원일련번호
    );

-- 데이터 준비 
INSERT INTO PROJECTS(PNAME,CONTENT,STA_DATE,END_DATE,STATE,CRE_DATE,TAGS,PNO )
VALUES ('프로젝트1','내용...','2013-1-1','2013-2-1',0,sysdate,'java',PROJECTS_PNO.nextval);

INSERT INTO PROJECTS(PNAME,CONTENT,STA_DATE,END_DATE,STATE,CRE_DATE,TAGS,PNO)
VALUES ('프로젝트2','내용...','2013-2-1','2013-3-1',0,sysdate,'c',PROJECTS_PNO.nextval);

INSERT INTO PROJECTS(PNAME,CONTENT,STA_DATE,END_DATE,STATE,CRE_DATE,TAGS,PNO)
VALUES ('프로젝트3','내용...','2013-3-1','2013-4-1',0,sysdate,'c++',PROJECTS_PNO.nextval);

INSERT INTO PROJECTS(PNAME,CONTENT,STA_DATE,END_DATE,STATE,CRE_DATE,TAGS,PNO)
VALUES ('프로젝트4','내용...','2013-4-1','2013-5-1',0,sysdate,'java',PROJECTS_PNO.nextval);

INSERT INTO PROJECTS(PNAME,CONTENT,STA_DATE,END_DATE,STATE,CRE_DATE,TAGS,PNO)
VALUES ('프로젝트5','내용...','2013-5-1','2013-6-1',0,sysdate,'c',PROJECTS_PNO.nextval);