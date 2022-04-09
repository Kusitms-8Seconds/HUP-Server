INSERT INTO user (USER_ID, LOGIN_ID, EMAIL, USERNAME, PASSWORD, PHONE_NUMBER, ACTIVATED, EMAIL_AUTH_ACTIVATED, PICTURE, LOGIN_TYPE) VALUES (100, 'admin', 'admin@naver.com', 'adminName',
'$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', '010-1111-1111', 1, 1,
'https://firebasestorage.googleapis.com/v0/b/auctionapp-f3805.appspot.com/o/profile.png?alt=media&token=655ed158-b464-4e5e-aa56-df3d7f12bdc8', "eApp");

INSERT INTO authority (AUTHORITY_NAME) values ('ROLE_DISABLED_USER');
INSERT INTO authority (AUTHORITY_NAME) values ('ROLE_USER');
INSERT INTO authority (AUTHORITY_NAME) values ('ROLE_ADMIN');

INSERT INTO user_authority (USER_ID, AUTHORITY_NAME) values (100, 'ROLE_DISABLED_USER');
INSERT INTO user_authority (USER_ID, AUTHORITY_NAME) values (100, 'ROLE_USER');
INSERT INTO user_authority (USER_ID, AUTHORITY_NAME) values (100, 'ROLE_ADMIN');

