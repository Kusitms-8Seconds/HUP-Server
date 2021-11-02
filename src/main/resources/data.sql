INSERT INTO USER (USER_ID, LOGIN_ID, EMAIL, USERNAME, PASSWORD, PHONE_NUMBER, ACTIVATED) VALUES (100, 'admin', 'admin@naver.com', 'adminName',
'$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', '010-1111-1111', 1);

INSERT INTO AUTHORITY (AUTHORITY_NAME) values ('ROLE_USER');
INSERT INTO AUTHORITY (AUTHORITY_NAME) values ('ROLE_ADMIN');

INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_NAME) values (100, 'ROLE_USER');
INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_NAME) values (100, 'ROLE_ADMIN');