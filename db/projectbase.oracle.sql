-- Bảng  quyền 
  CREATE TABLE ADM_AUTHORITIES 
   (	
    ID NUMBER(10,0) NOT NULL ENABLE, 
	AUTHORITY VARCHAR2(200) NOT NULL ENABLE, 
	FID NUMBER(10,0) NOT NULL ENABLE, 
	DESCRIPTION VARCHAR2(3000) NOT NULL ENABLE, 
	ORDER_ID NUMBER(10,0) NOT NULL ENABLE, 
	AUTH_KEY NUMBER(1,0) NOT NULL ENABLE, 
	CREATE_BY VARCHAR2(50) NOT NULL ENABLE, 
	UPDATE_BY VARCHAR2(50) NOT NULL ENABLE, 
	GEN_DATE DATE DEFAULT sysdate NOT NULL ENABLE, 
	LAST_UPDATED DATE DEFAULT sysdate NOT NULL ENABLE, 
	 CONSTRAINT PK_ADM_AUTHORITIES PRIMARY KEY (ID)
   ) ;
   COMMENT ON COLUMN ADM_AUTHORITIES.ID IS 'ID mã quyền';
   COMMENT ON COLUMN ADM_AUTHORITIES.AUTHORITY IS 'Mã quyền';
   COMMENT ON COLUMN ADM_AUTHORITIES.FID IS 'Father ID' ;
   COMMENT ON COLUMN ADM_AUTHORITIES.DESCRIPTION IS 'Mô tả quyền';
   COMMENT ON COLUMN ADM_AUTHORITIES.ORDER_ID IS 'Thứ tự sắp xếp';
   COMMENT ON COLUMN ADM_AUTHORITIES.AUTH_KEY IS 'Key  định danh quyền';
   
  	--  
	CREATE SEQUENCE  ADM_AUTHORITIES_SEQ  MINVALUE 1 MAXVALUE 99999999 INCREMENT BY 1 START WITH 121 CACHE 20 NOORDER  NOCYCLE ;

-- Bảng  nhóm quyền 
  CREATE TABLE ADM_GROUP 
   (	ID NUMBER(10,0) NOT NULL ENABLE, 
	GROUP_NAME VARCHAR2(100 BYTE) NOT NULL ENABLE, 
	STATUS NUMBER(5,0) NOT NULL ENABLE, 
	AUTHORITY VARCHAR2(1000 BYTE), 
	DESCRIPTION VARCHAR2(200 BYTE), 
	CREATE_BY VARCHAR2(50 BYTE), 
	UPDATE_BY VARCHAR2(50 BYTE), 
	GEN_DATE DATE DEFAULT sysdate, 
	LAST_UPDATED DATE DEFAULT sysdate, 
	 CONSTRAINT PK_ADM_GROUP PRIMARY KEY (ID)
   ) ;
   COMMENT ON COLUMN ADM_GROUP.ID IS '';
   COMMENT ON COLUMN ADM_GROUP.GROUP_NAME IS '';
   COMMENT ON COLUMN ADM_GROUP.STATUS IS '' ;
   COMMENT ON COLUMN ADM_GROUP.AUTHORITY IS '';
   COMMENT ON COLUMN ADM_GROUP.DESCRIPTION IS '';
	--
	CREATE SEQUENCE  ADM_GROUP_SEQ  MINVALUE 1 MAXVALUE 99999999 INCREMENT BY 1 START WITH 261 CACHE 20 NOORDER  NOCYCLE ;

-- Bảng  người dùng 
  CREATE TABLE ADM_USERS 
   (	ID NUMBER(10,0) NOT NULL ENABLE, 
	USER_NAME VARCHAR2(20 BYTE) NOT NULL ENABLE, 
	PASSWORD VARCHAR2(100 BYTE) NOT NULL ENABLE, 
	FULL_NAME VARCHAR2(100 BYTE) NOT NULL ENABLE, 
	DESCRIPTION VARCHAR2(200 BYTE), 
	LAST_ACCESS_TIME DATE, 
	STATUS NUMBER(10,0) NOT NULL ENABLE, 
	GEN_DATE DATE DEFAULT sysdate, 
	LAST_UPDATED DATE DEFAULT sysdate, 
	TYPE NUMBER(2,0), 
	MSISDN VARCHAR2(20 BYTE), 
	 CONSTRAINT PK_ADM_USERS PRIMARY KEY (ID)
   ) ;
   COMMENT ON COLUMN ADM_USERS.ID IS '';
   COMMENT ON COLUMN ADM_USERS.GROUP_NAME IS '';
   COMMENT ON COLUMN ADM_USERS.STATUS IS '' ;
   COMMENT ON COLUMN ADM_USERS.AUTHORITY IS '';
   COMMENT ON COLUMN ADM_USERS.DESCRIPTION IS '';
  COMMENT ON COLUMN ADM_USERS.TYPE IS '0-Admin BTP 1-Admin STP   2-DGV';
  CREATE UNIQUE INDEX ADM_USERS_INDEX1 ON ADM_USERS (USER_NAME) ;
	---
	CREATE SEQUENCE  ADM_USERS_SEQ  MINVALUE 1 MAXVALUE 99999999 INCREMENT BY 1 START WITH 781 CACHE 20 NOORDER  NOCYCLE ;

-- Bảng  quan hệ giữa nhóm quyền và quyền 
  CREATE TABLE ADM_GROUP_AUTHORITIES 
   (	GROUP_ID NUMBER(10,0) NOT NULL ENABLE, 
	AUTHORITY NUMBER(10,0) NOT NULL ENABLE, 
	CREATE_BY VARCHAR2(50 BYTE), 
	UPDATE_BY VARCHAR2(50 BYTE), 
	GEN_DATE DATE DEFAULT sysdate, 
	LAST_UPDATED DATE DEFAULT sysdate, 
	 CONSTRAINT ADM_GROUP_AUTHORITIES_PK PRIMARY KEY (GROUP_ID, AUTHORITY)
   ) ;
    
  -- Bảng  quan hệ giữa nhóm quyền và người dùng
  CREATE TABLE ADM_GROUP_USER 
   (	GROUP_ID NUMBER(10,0) NOT NULL ENABLE, 
	USER_ID NUMBER(10,0) NOT NULL ENABLE, 
	CREATE_BY VARCHAR2(50 BYTE) NOT NULL ENABLE, 
	GEN_DATE DATE DEFAULT sysdate NOT NULL ENABLE, 
	 CONSTRAINT ADM_GROUP_USER_PK PRIMARY KEY (GROUP_ID, USER_ID)
   ) ;
   
-- Bảng  log truy cập  
  CREATE TABLE ADM_LOG_ACCESS 
   (	ID NUMBER(10,0) NOT NULL ENABLE, 
	USER_ID NUMBER(10,0) NOT NULL ENABLE, 
	MODULE_ID VARCHAR2(100 BYTE) NOT NULL ENABLE, 
	IP VARCHAR2(100 BYTE) NOT NULL ENABLE, 
	ACTIONS VARCHAR2(200 BYTE) NOT NULL ENABLE, 
	GEN_DATE DATE DEFAULT sysdate NOT NULL ENABLE, 
	 CONSTRAINT PK_ADM_LOG_ACCESS PRIMARY KEY (ID)
   ) ;
	--
	CREATE SEQUENCE  ADM_LOG_ACCESS_SEQ  MINVALUE 1 MAXVALUE 99999999 INCREMENT BY 1 START WITH 93855 CACHE 20 NOORDER  NOCYCLE ;


	--  Bảng quản lý các tham số hệ thống 
  CREATE TABLE ADM_PARAMETER 
   (	ID VARCHAR2(20 BYTE) NOT NULL ENABLE, 
	KEY VARCHAR2(50 BYTE), 
	VALUE VARCHAR2(200 BYTE), 
	DESCRIPTION VARCHAR2(500 BYTE), 
	USER_ID NUMBER(10,0), 
	GEN_DATE DATE, 
	LAST_USERID NUMBER(10,0), 
	LAST_UPDATE DATE, 
	 CONSTRAINT ADM_PARAMETER_PK PRIMARY KEY (ID)
   ) ;
   --
   CREATE SEQUENCE  ADM_PARAM_SEQ  MINVALUE 0 MAXVALUE 9999999 INCREMENT BY 1 START WITH 121 CACHE 20 NOORDER  NOCYCLE ;

  


