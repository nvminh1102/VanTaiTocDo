
CREATE TABLE ADM_AUTHORITIES(
  ID int NOT NULL AUTO_INCREMENT,
  AUTHORITY varchar(200) not null,
  FID int not null,
  DESCRIPTION varchar(500),
  ORDER_ID int,
  AUTH_KEY varchar(500),
  CREATE_BY  varchar(50),
  UPDATE_BY varchar(50),
  GEN_DATE datetime DEFAULT CURRENT_TIMESTAMP,
  LAST_UPDATED datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (ID)
);

CREATE TABLE ADM_GROUP(
  ID int NOT NULL AUTO_INCREMENT,
  GROUP_NAME varchar(200) not null,
  STATUS int not null,
  AUTHORITY varchar(1000),
  DESCRIPTION varchar(500),
  CREATE_BY  varchar(50),
  UPDATE_BY varchar(50),
  GEN_DATE datetime DEFAULT CURRENT_TIMESTAMP,
  LAST_UPDATED datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (ID)
);

CREATE TABLE ADM_GROUP_AUTHORITIES(
  GROUP_ID int,
  AUTHORITY int,
  CREATE_BY  varchar(50),
  UPDATE_BY varchar(50),
  GEN_DATE datetime DEFAULT CURRENT_TIMESTAMP,
  LAST_UPDATED datetime DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE ADM_GROUP_USER(
  GROUP_ID int,
  USER_ID int,
  CREATE_BY  varchar(50),
  GEN_DATE datetime DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE ADM_LOG_ACCESS(
  ID int NOT NULL AUTO_INCREMENT,
  USER_ID int not null,
  MODULE_ID varchar(500),
  IP varchar(100),
  ACTIONS varchar(500),
  GEN_DATE datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (ID)
);

CREATE TABLE ADM_PARAMETER(
  ID int NOT NULL AUTO_INCREMENT,
  P_KEY varchar(500),
  DESCRIPTION varchar(500),
  USER_ID int,
  LAST_USERID int,
  GEN_DATE datetime DEFAULT CURRENT_TIMESTAMP,
  LAST_UPDATE datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (ID)
);

CREATE TABLE ADM_USERS(
  ID int NOT NULL AUTO_INCREMENT,
  USER_NAME varchar(100),
  PASSWORD varchar(100),
  FULL_NAME varchar(100),
  DESCRIPTION varchar(200),
  LAST_ACCESS_TIME datetime,
  STATUS int,
  GEN_DATE datetime DEFAULT CURRENT_TIMESTAMP,
  LAST_UPDATEd datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (ID)
);

CREATE TABLE PERSISTENT_LOGINS(
  SERIES varchar(100),
  LAST_USED datetime,
  TOKEN varchar(100),
  USERNAME varchar(100)
);



-- Khởi tạo dữ liệu bảng ADM_GROUP

Insert into ADM_GROUP (ID,GROUP_NAME,STATUS,AUTHORITY,DESCRIPTION,CREATE_BY,UPDATE_BY,GEN_DATE,LAST_UPDATED) values ('1','Quản trị hệ thống','1','ROLE_SYSTEM_USER','Quản trị viên','admin','admin',now(),now());
Insert into ADM_GROUP (ID,GROUP_NAME,STATUS,AUTHORITY,DESCRIPTION,CREATE_BY,UPDATE_BY,GEN_DATE,LAST_UPDATED) values ('2','CSDL Khách  hàng','1','ROLE_ CUSTOMER_USER','Quản lý CSDL Khách  hàng','admin','admin',now(),now());

-- Khởi tạo dữ liệu bảng ADM_AUTHORITIES với quyền cho admin

-- Quyền 
Insert into ADM_AUTHORITIES (ID,AUTHORITY,FID,DESCRIPTION,ORDER_ID,AUTH_KEY,CREATE_BY,UPDATE_BY,GEN_DATE,LAST_UPDATED) values ('1','ROLE_SYSTEM_USER','0','Quản trị người dùng','1','ROLE_SYSTEM_USER','admin','admin',now(),now());
Insert into ADM_AUTHORITIES (ID,AUTHORITY,FID,DESCRIPTION,ORDER_ID,AUTH_KEY,CREATE_BY,UPDATE_BY,GEN_DATE,LAST_UPDATED) values ('2','ROLE_SYSTEM_USER_ADD','1','Thêm người dùng','1','ROLE_SYSTEM_USER_ADD','admin','admin',now(),now());
Insert into ADM_AUTHORITIES (ID,AUTHORITY,FID,DESCRIPTION,ORDER_ID,AUTH_KEY,CREATE_BY,UPDATE_BY,GEN_DATE,LAST_UPDATED) values ('3','ROLE_SYSTEM_USER_VIEW','1','Xem người dùng','1','ROLE_SYSTEM_USER_VIEW','admin','admin',now(),now());
Insert into ADM_AUTHORITIES (ID,AUTHORITY,FID,DESCRIPTION,ORDER_ID,AUTH_KEY,CREATE_BY,UPDATE_BY,GEN_DATE,LAST_UPDATED) values ('4','ROLE_SYSTEM_USER_EDIT','1','Sửa người dùng','1','ROLE_SYSTEM_USER_EDIT','admin','admin',now(),now());

Insert into ADM_AUTHORITIES (ID,AUTHORITY,FID,DESCRIPTION,ORDER_ID,AUTH_KEY,CREATE_BY,UPDATE_BY,GEN_DATE,LAST_UPDATED) values ('5','ROLE_SYSTEM_LOG','0','Quản trị log hệ thống','4','ROLE_SYSTEM_LOG','admin','admin',now(),now());
Insert into ADM_AUTHORITIES (ID,AUTHORITY,FID,DESCRIPTION,ORDER_ID,AUTH_KEY,CREATE_BY,UPDATE_BY,GEN_DATE,LAST_UPDATED) values ('6','ROLE_SYSTEM_LOG_VIEW','5','Xem log hệ thống','4','ROLE_SYSTEM_LOG_VIEW','admin','admin',now(),now());

Insert into ADM_AUTHORITIES (ID,AUTHORITY,FID,DESCRIPTION,ORDER_ID,AUTH_KEY,CREATE_BY,UPDATE_BY,GEN_DATE,LAST_UPDATED) values ('7','ROLE_SYSTEM_GROUP','0','Quản trị nhóm quyền','3','ROLE_SYSTEM_GROUP','admin','admin',now(),now());
Insert into ADM_AUTHORITIES (ID,AUTHORITY,FID,DESCRIPTION,ORDER_ID,AUTH_KEY,CREATE_BY,UPDATE_BY,GEN_DATE,LAST_UPDATED) values ('8','ROLE_SYSTEM_GROUP_VIEW','7','Xem danh sách nhóm quyền','3','ROLE_SYSTEM_GROUP_VIEW','admin','admin',now(),now());
Insert into ADM_AUTHORITIES (ID,AUTHORITY,FID,DESCRIPTION,ORDER_ID,AUTH_KEY,CREATE_BY,UPDATE_BY,GEN_DATE,LAST_UPDATED) values ('9','ROLE_SYSTEM_GROUP_ADD','7','Thêm nhóm quyền','3','ROLE_SYSTEM_GROUP_ADD','admin','admin',now(),now());
Insert into ADM_AUTHORITIES (ID,AUTHORITY,FID,DESCRIPTION,ORDER_ID,AUTH_KEY,CREATE_BY,UPDATE_BY,GEN_DATE,LAST_UPDATED) values ('10','ROLE_SYSTEM_GROUP_EDIT','7','Sửa nhóm quyền','3','ROLE_SYSTEM_GROUP_EDIT','admin','admin',now(),now());
Insert into ADM_AUTHORITIES (ID,AUTHORITY,FID,DESCRIPTION,ORDER_ID,AUTH_KEY,CREATE_BY,UPDATE_BY,GEN_DATE,LAST_UPDATED) values ('11','ROLE_SYSTEM_GROUP_DELETE','7','Xóa nhóm quyền','3','ROLE_SYSTEM_GROUP_DELETE','admin','admin',now(),now());


Insert into ADM_AUTHORITIES (ID,AUTHORITY,FID,DESCRIPTION,ORDER_ID,AUTH_KEY,CREATE_BY,UPDATE_BY,GEN_DATE,LAST_UPDATED) values ('12','ROLE_SYSTEM_USER_AUTHORITY','1','Phân quyền người dùng','1','ROLE_SYSTEM_USER_AUTHORITY','admin','admin',now(),now());

Insert into ADM_AUTHORITIES (ID,AUTHORITY,FID,DESCRIPTION,ORDER_ID,AUTH_KEY,CREATE_BY,UPDATE_BY,GEN_DATE,LAST_UPDATED) values ('34','ROLE_REPORT','0','Báo cáo thống kê','6','ROLE_REPORT','admin','admin',now(),now());

Insert into ADM_AUTHORITIES (ID,AUTHORITY,FID,DESCRIPTION,ORDER_ID,AUTH_KEY,CREATE_BY,UPDATE_BY,GEN_DATE,LAST_UPDATED) values ('40','ROLE_SYSTEM_PARAMETER','0','Tham số hệ thống','0','ROLE_SYSTEM_PARAMETER','admin','admin',now(),now());
Insert into ADM_AUTHORITIES (ID,AUTHORITY,FID,DESCRIPTION,ORDER_ID,AUTH_KEY,CREATE_BY,UPDATE_BY,GEN_DATE,LAST_UPDATED) values ('41','ROLE_SYSTEM_PARAMETER_VIEW','40','Xem danh sách tham số','1','ROLE_SYSTEM_PARAMETER_VIEW','admin','admin',now(),now());
Insert into ADM_AUTHORITIES (ID,AUTHORITY,FID,DESCRIPTION,ORDER_ID,AUTH_KEY,CREATE_BY,UPDATE_BY,GEN_DATE,LAST_UPDATED) values ('43','ROLE_SYSTEM_PARAMETER_UPDATE','40','Sửa tham số','2','ROLE_SYSTEM_PARAMETER_UPDATE','admin','admin',now(),now());
Insert into ADM_AUTHORITIES (ID,AUTHORITY,FID,DESCRIPTION,ORDER_ID,AUTH_KEY,CREATE_BY,UPDATE_BY,GEN_DATE,LAST_UPDATED) values ('50','ROLE_CATEGORY','0','Quản lý nội dung','0','ROLE_CATEGORY','admin','admin',now(),now());
Insert into ADM_AUTHORITIES (ID,AUTHORITY,FID,DESCRIPTION,ORDER_ID,AUTH_KEY,CREATE_BY,UPDATE_BY,GEN_DATE,LAST_UPDATED) values ('51','ROLE_CATEGORY_CONTENT','50','Chi tiết nội dung','1','ROLE_CATEGORY_CONTENT','admin','admin',now(),now());
Insert into ADM_AUTHORITIES (ID,AUTHORITY,FID,DESCRIPTION,ORDER_ID,AUTH_KEY,CREATE_BY,UPDATE_BY,GEN_DATE,LAST_UPDATED) values ('64','ROLE_SYSTEM_ACTION','0','Quản lý chức năng','0','ROLE_SYSTEM_ACTION','admin','admin',now(),now());
Insert into ADM_AUTHORITIES (ID,AUTHORITY,FID,DESCRIPTION,ORDER_ID,AUTH_KEY,CREATE_BY,UPDATE_BY,GEN_DATE,LAST_UPDATED) values ('65','ROLE_SYSTEM_ACTION_VIEW','64','Xem danh sách chức năng','1','ROLE_SYSTEM_ACTION_VIEW','admin','admin',now(),now());
Insert into ADM_AUTHORITIES (ID,AUTHORITY,FID,DESCRIPTION,ORDER_ID,AUTH_KEY,CREATE_BY,UPDATE_BY,GEN_DATE,LAST_UPDATED) values ('66','ROLE_SYSTEM_ACTION_ADD','64','Thêm mới chức năng','2','1','admin','admin',now(),now());
Insert into ADM_AUTHORITIES (ID,AUTHORITY,FID,DESCRIPTION,ORDER_ID,AUTH_KEY,CREATE_BY,UPDATE_BY,GEN_DATE,LAST_UPDATED) values ('67','ROLE_SYSTEM_ACTION_EDIT','64','Update chức năng    âsa','3','ROLE_SYSTEM_ACTION_ADD','admin','admin',now(),now());
Insert into ADM_AUTHORITIES (ID,AUTHORITY,FID,DESCRIPTION,ORDER_ID,AUTH_KEY,CREATE_BY,UPDATE_BY,GEN_DATE,LAST_UPDATED) values ('68','ROLE_SYSTEM_ACTION_DELETE','64','Xóa chức năng','4','ROLE_SYSTEM_ACTION_DELETE','admin','admin',now(),now());





-- Khởi tạo dữ liệu bảng quan  hệ giữa GROUP và AUTHORITIES

Insert into ADM_GROUP_AUTHORITIES (GROUP_ID,AUTHORITY,CREATE_BY,UPDATE_BY,GEN_DATE,LAST_UPDATED) values ('1','1','admin',null,now(),now());
Insert into ADM_GROUP_AUTHORITIES (GROUP_ID,AUTHORITY,CREATE_BY,UPDATE_BY,GEN_DATE,LAST_UPDATED) values ('1','2','admin',null,now(),now());
Insert into ADM_GROUP_AUTHORITIES (GROUP_ID,AUTHORITY,CREATE_BY,UPDATE_BY,GEN_DATE,LAST_UPDATED) values ('1','3','admin',null,now(),now());
Insert into ADM_GROUP_AUTHORITIES (GROUP_ID,AUTHORITY,CREATE_BY,UPDATE_BY,GEN_DATE,LAST_UPDATED) values ('1','4','admin',null,now(),now());
Insert into ADM_GROUP_AUTHORITIES (GROUP_ID,AUTHORITY,CREATE_BY,UPDATE_BY,GEN_DATE,LAST_UPDATED) values ('1','5','admin',null,now(),now());
Insert into ADM_GROUP_AUTHORITIES (GROUP_ID,AUTHORITY,CREATE_BY,UPDATE_BY,GEN_DATE,LAST_UPDATED) values ('1','6','admin',null,now(),now());
Insert into ADM_GROUP_AUTHORITIES (GROUP_ID,AUTHORITY,CREATE_BY,UPDATE_BY,GEN_DATE,LAST_UPDATED) values ('1','7','admin',null,now(),now());
Insert into ADM_GROUP_AUTHORITIES (GROUP_ID,AUTHORITY,CREATE_BY,UPDATE_BY,GEN_DATE,LAST_UPDATED) values ('1','8','admin',null,now(),now());
Insert into ADM_GROUP_AUTHORITIES (GROUP_ID,AUTHORITY,CREATE_BY,UPDATE_BY,GEN_DATE,LAST_UPDATED) values ('1','9','admin',null,now(),now());
Insert into ADM_GROUP_AUTHORITIES (GROUP_ID,AUTHORITY,CREATE_BY,UPDATE_BY,GEN_DATE,LAST_UPDATED) values ('1','10','admin',null,now(),now());
Insert into ADM_GROUP_AUTHORITIES (GROUP_ID,AUTHORITY,CREATE_BY,UPDATE_BY,GEN_DATE,LAST_UPDATED) values ('1','11','admin',null,now(),now());
Insert into ADM_GROUP_AUTHORITIES (GROUP_ID,AUTHORITY,CREATE_BY,UPDATE_BY,GEN_DATE,LAST_UPDATED) values ('1','12','admin',null,now(),now());
Insert into ADM_GROUP_AUTHORITIES (GROUP_ID,AUTHORITY,CREATE_BY,UPDATE_BY,GEN_DATE,LAST_UPDATED) values ('1','34','admin',null,now(),now());
Insert into ADM_GROUP_AUTHORITIES (GROUP_ID,AUTHORITY,CREATE_BY,UPDATE_BY,GEN_DATE,LAST_UPDATED) values ('1','40','admin',null,now(),now());
Insert into ADM_GROUP_AUTHORITIES (GROUP_ID,AUTHORITY,CREATE_BY,UPDATE_BY,GEN_DATE,LAST_UPDATED) values ('1','41','admin',null,now(),now());
Insert into ADM_GROUP_AUTHORITIES (GROUP_ID,AUTHORITY,CREATE_BY,UPDATE_BY,GEN_DATE,LAST_UPDATED) values ('1','43','admin',null,now(),now());
Insert into ADM_GROUP_AUTHORITIES (GROUP_ID,AUTHORITY,CREATE_BY,UPDATE_BY,GEN_DATE,LAST_UPDATED) values ('1','64','admin',null,now(),now());
Insert into ADM_GROUP_AUTHORITIES (GROUP_ID,AUTHORITY,CREATE_BY,UPDATE_BY,GEN_DATE,LAST_UPDATED) values ('1','65','admin',null,now(),now());
Insert into ADM_GROUP_AUTHORITIES (GROUP_ID,AUTHORITY,CREATE_BY,UPDATE_BY,GEN_DATE,LAST_UPDATED) values ('1','66','admin',null,now(),now());
Insert into ADM_GROUP_AUTHORITIES (GROUP_ID,AUTHORITY,CREATE_BY,UPDATE_BY,GEN_DATE,LAST_UPDATED) values ('1','67','admin',null,now(),now());
Insert into ADM_GROUP_AUTHORITIES (GROUP_ID,AUTHORITY,CREATE_BY,UPDATE_BY,GEN_DATE,LAST_UPDATED) values ('1','68','admin',null,now(),now());

-- Khởi tạo dữ liệu bảng USER

Insert into ADM_USERS (ID,USER_NAME,PASSWORD,FULL_NAME,DESCRIPTION,LAST_ACCESS_TIME,STATUS,GEN_DATE,LAST_UPDATED) values ('1','admin','$2a$10$DGAZCNqFCO81s2517.WVUO2L7jvY/Omcc1zkgKF4Gk0WnfBELIes6','Admin','Không xóa acc này',now(),'1',now(),now());
Insert into ADM_USERS (ID,USER_NAME,PASSWORD,FULL_NAME,DESCRIPTION,LAST_ACCESS_TIME,STATUS,GEN_DATE,LAST_UPDATED) values ('2','thangdt','$2a$10$7aqwMUcpHNCE3swYESy6NumOFszWlNJNoYvPKO.A9kwwuAgAGrmz.','Đặng Trường Thăng',null,null,'1',now(),now());

-- Khởi tạo dữ liệu bảng quan hệ  GROUP - USER

Insert into ADM_GROUP_USER (GROUP_ID,USER_ID,CREATE_BY,GEN_DATE) values ('1','1','admin',now());
Insert into ADM_GROUP_USER (GROUP_ID,USER_ID,CREATE_BY,GEN_DATE) values ('1','2','admin',now());


commit;

--Các bản lưu thông tin vt_
-- bảng lưu thông tin khách hàng, Người gửi, người nhận, nhà xe, nhà cung cấp
CREATE TABLE vt_partner(
	ID int NOT NULL AUTO_INCREMENT, 
	USER_NAME varchar(200),  -- Tên liên lạc
	FULL_NAME varchar(200), -- tên đầy đủ
	address varchar(500), 	-- Địa chỉ
	tax_code varchar(200),  -- Mã số thuế
	MOBILE varchar(20), 
	EMAIL varchar(50), 
	type_partner int, -- Loại đối tượng. 1:Khách hàng; 2:người gửi; 3:Người nhận; 4:Nhà xe; ...
	gen_date  datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,  
	created_by varchar(200),
	last_update  datetime DEFAULT CURRENT_TIMESTAMP,  
	updated_by varchar(200),
    PRIMARY KEY (ID)
);



-- Bảng lưu thông tin phiếu biên nhận
CREATE TABLE vt_receipt(
	ID int NOT NULL AUTO_INCREMENT, 
	receipt_code varchar(200), -- Số biên nhận
	delivery_partner_id int,  -- id người gửi
	receive_partner_id int,  -- id người nhận hàng
	name_Stock varchar(200), -- Kho hàng
	date_receipt  datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,  -- Ngày lập Phiếu thu
	date_push_Stock  datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,  -- Ngày nhập kho
	payment_type int, 	-- Hình thức thanh toán: 1: trả trước; 2:trả sau; 3:Công nợ
	payer varchar(200), -- Người thanh toán
	tax_code varchar(200),  -- Mã số thuế
	nha_xe varchar(200),  -- Nhà xe
	bien_so varchar(50),  -- Biển số xe
	employee varchar(200),  -- Nhân viên nhà xe nhận hàng
	status int,  -- Trạng thái biên nhận: 1: Nhận hàng, 2: Nhập kho, 3: Đang giao, 4: Đã giao hàng
	file_attach varchar(200), -- lưu đường dẫn bản scan phiếu biên nhận nếu có
	gen_date  datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,  
	created_by varchar(200),
	last_update  datetime DEFAULT CURRENT_TIMESTAMP,  
	updated_by varchar(200),
    PRIMARY KEY (ID)
);


-- Bảng lưu thông tin chi tiết phiếu biên nhận

CREATE TABLE vt_receipt_detail(
	id int NOT NULL AUTO_INCREMENT, 
	receipt_id int, -- id bảng thông tin phiếu biên nhận
	name varchar(200), -- Tên hàng
	unit varchar(50),  -- đơn vị tính
	numbers int, -- Số lượng
	weight int, -- trọng lượng
	sizes int, -- Kích thước
	cost int, -- thành tiền
	debt int, -- Số tiền còn phải trả
	document varchar(500), -- link file đính kèm
	note varchar(500), -- Ghi chú
	status int,  -- Trạng thái chi tiết mặt hàng: 1: Nhận hàng, 2: Nhập kho, 3: Đang giao, 4: Đã giao hàng
	gen_date  datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,  
	created_by varchar(200),
	last_update  datetime DEFAULT CURRENT_TIMESTAMP,  
	updated_by varchar(200),
    PRIMARY KEY (ID)
);

-- Bảng lưu thông tin phiếu nhận
CREATE TABLE vt_goods_receipt(
	id int NOT NULL AUTO_INCREMENT,
	receipt_code varchar(200), -- Số phiếu nhận hàng
	truck_partner_id int,  -- id nhà xe
	bien_so varchar(50),  -- Biển số xe
	date_delivery  datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,  -- Ngày chạy gửi hàng
	date_receive  datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,  -- Ngày lập biên nhận
	gen_date  datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,  
	created_by varchar(200),
	last_update  datetime DEFAULT CURRENT_TIMESTAMP,  
	updated_by varchar(200),
    PRIMARY KEY (ID)
);




-- Bảng lưu thông tin chi tiết phiếu nhận
CREATE TABLE vt_goods_receipt_detail(
	ID int NOT NULL AUTO_INCREMENT, 
	Goods_receipt_id int, -- id phiếu nhận
	receipt_id int,  -- id biên nhận
	file_attach varchar(200), -- lưu đường dẫn bản scan công nợ nếu có
	gen_date  datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,  
	created_by varchar(200),
	last_update  datetime DEFAULT CURRENT_TIMESTAMP,  
	updated_by varchar(200),
    PRIMARY KEY (ID)
);




-- Bảng lưu thông tin phiếu giao hàng
CREATE TABLE vt_goods_delivery(
	id int NOT NULL AUTO_INCREMENT,
	delivery_code varchar(200), -- Số phiếu giao hàng
	truck_partner_id int,  -- id nhà xe
	bien_so varchar(50),  -- Biển số xe
	date_delivery  datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,  -- Ngày chạy gửi hàng
	date_receive  datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,  -- Ngày lập biên nhận
	gen_date  datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,  
	created_by varchar(200),
	last_update  datetime DEFAULT CURRENT_TIMESTAMP,  
	updated_by varchar(200),
    PRIMARY KEY (ID)
);

-- Bảng lưu thông tin chi tiết phiếu giao hàng
CREATE TABLE vt_goods_delivery_detail(
	ID int NOT NULL AUTO_INCREMENT, 
	Goods_delivery_id int, -- id phiếu giao, giao hàng
	receipt_id int,  -- id biên nhận
	file_attach varchar(200), -- lưu đường dẫn bản scan công nợ nếu có
	gen_date  datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,  
	created_by varchar(200),
	last_update  datetime DEFAULT CURRENT_TIMESTAMP,  
	updated_by varchar(200),
    PRIMARY KEY (ID)
);



-- Bảng lưu thông tin phiếu thu
CREATE TABLE vt_phieu_thu(
	ID int NOT NULL AUTO_INCREMENT, 
	receipt_code varchar(200), -- Số phiếu thu
	delivery_partner_id int,  -- id người gửi
	receive_partner_id int,  -- id người nhận hàng
	name_Stock varchar(200), -- Kho hàng
	date_receipt  datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,  -- Ngày lập Phiếu thu
	date_push_Stock  datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,  -- Ngày nhập kho
	payer varchar(200), -- Người thanh toán
	employee varchar(200),  -- Nhân viên nhà xe nhận hàng
	status int,  -- Trạng thái biên nhận: 1: Nhận hàng, 2: Nhập kho, 3: Đang giao, 4: Đã giao hàng
	file_attach varchar(200), -- lưu đường dẫn bản scan phiếu biên nhận nếu có
	gen_date  datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,  
	created_by varchar(200),
	last_update  datetime DEFAULT CURRENT_TIMESTAMP,  
	updated_by varchar(200),
    PRIMARY KEY (ID)
);



-- Bảng lưu thông tin chi tiết phiếu thu
CREATE TABLE vt_phieu_thu_detail(
	ID int NOT NULL AUTO_INCREMENT, 
	phieu_thu_id int, -- id phiếu biên nhận
	receipt_id int,  -- id biên nhận
	file_attach varchar(200), -- lưu đường dẫn bản scan phiếu biên nhận nếu có
	gen_date  datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,  
	created_by varchar(200),
	last_update  datetime DEFAULT CURRENT_TIMESTAMP,  
	updated_by varchar(200),
    PRIMARY KEY (ID)
);




-- Bảng lưu thông tin phiếu công nợ
CREATE TABLE vt_cong_no(
	ID int NOT NULL AUTO_INCREMENT, 
	cong_no_code varchar(200), -- Số công nợ
	date_cong_no  datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,  -- Ngày lập Phiếu thu
	date_exp  datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,  -- Ngày hạn công nợ
	gen_date  datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,  
	created_by varchar(200),
	last_update  datetime DEFAULT CURRENT_TIMESTAMP,  
	updated_by varchar(200),
    PRIMARY KEY (ID)
);



-- Bảng lưu thông tin chi tiết công nợ
CREATE TABLE vt_cong_no_detail(
	ID int NOT NULL AUTO_INCREMENT, 
	cong_no_id int, -- id phiếu biên nhận
	receipt_id int,  -- id biên nhận
	file_attach varchar(200), -- lưu đường dẫn bản scan công nợ nếu có
	gen_date  datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,  
	created_by varchar(200),
	last_update  datetime DEFAULT CURRENT_TIMESTAMP,  
	updated_by varchar(200),
    PRIMARY KEY (ID)
);


-- Bảng lưu thông tin toa hàng
CREATE TABLE vt_toa_hang(
	ID int NOT NULL AUTO_INCREMENT, 
	toa_hang_code varchar(200), -- Số toa hàng
	from_where varchar(500), -- Nơi đi
	to_where varchar(500), -- Nơi đến
	bien_so varchar(50),  -- Biển số xe
	partner_id int, 	-- id lái xe
	nguoi_nhan  varchar(200), -- người nhận
	noi_nhan  varchar(500), -- Địa chỉ
	gen_date  datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,  
	created_by varchar(200),
	last_update  datetime DEFAULT CURRENT_TIMESTAMP,  
	updated_by varchar(200),
    PRIMARY KEY (ID)
);



-- Bảng lưu thông tin chi tiết công nợ
CREATE TABLE vt_toa_hang_detail(
	ID int NOT NULL AUTO_INCREMENT, 
	toa_hang_id int, -- id toa hàng
	receipt_id int,  -- id biên nhận
	file_attach varchar(200), -- lưu đường dẫn bản scan công nợ nếu có
	gen_date  datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,  
	created_by varchar(200),
	last_update  datetime DEFAULT CURRENT_TIMESTAMP,  
	updated_by varchar(200),
    PRIMARY KEY (ID)
);


