DROP TABLE EMPLOYEE ;

CREATE TABLE EMPLOYEE (
  ID VARCHAR2(100)  PRIMARY KEY,
  NAME VARCHAR2(100) NOT NULL );

INSERT INTO EMPLOYEE (ID, NAME) VALUES ('123e4567-e89b-12d3-a456-556642440001','Employee1');
INSERT INTO EMPLOYEE (ID, NAME) VALUES ('123e4567-e89b-12d3-a456-556642440002','Employee2');
