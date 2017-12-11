create table mvc_board(
bId NUMBER(4) PRIMARY KEY,
bName VARCHAR2 (20),
bTitle VARCHAR2 (100),
bContent VARCHAR2 (300),
bDate DATE DEFAULT SYSDATE,
bHit NUMBER(4) DEFAULT 0,
bGroup  NUMBER(4),
bStep  NUMBER(4),
bIndent  NUMBER(4)
);

create sequence mvc_board_seq;