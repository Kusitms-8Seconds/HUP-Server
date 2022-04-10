INSERT INTO user (USER_ID, LOGIN_ID, EMAIL, USERNAME, PASSWORD, PHONE_NUMBER, ACTIVATED, EMAIL_AUTH_ACTIVATED, PICTURE, LOGIN_TYPE) VALUES (100, 'admin', 'admin@naver.com', 'adminName',
'$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', '010-1111-1111', 1, 1,
'https://firebasestorage.googleapis.com/v0/b/auctionapp-f3805.appspot.com/o/profile.png?alt=media&token=655ed158-b464-4e5e-aa56-df3d7f12bdc8', "eApp");

INSERT INTO authority (AUTHORITY_NAME) values ('ROLE_DISABLED_USER');
INSERT INTO authority (AUTHORITY_NAME) values ('ROLE_USER');
INSERT INTO authority (AUTHORITY_NAME) values ('ROLE_ADMIN');

INSERT INTO user_authority (USER_ID, AUTHORITY_NAME) values (100, 'ROLE_DISABLED_USER');
INSERT INTO user_authority (USER_ID, AUTHORITY_NAME) values (100, 'ROLE_USER');
INSERT INTO user_authority (USER_ID, AUTHORITY_NAME) values (100, 'ROLE_ADMIN');

INSERT INTO category (CATEGORY_ID, CATEGORY) values (1, 'eDigital');
INSERT INTO category (CATEGORY_ID, CATEGORY) values (2, 'eHouseHoldAppliance');
INSERT INTO category (CATEGORY_ID, CATEGORY) values (3, 'eFurnitureAndInterior');
INSERT INTO category (CATEGORY_ID, CATEGORY) values (4, 'eChildren');
INSERT INTO category (CATEGORY_ID, CATEGORY) values (5, 'eDailyLifeAndProcessedFood');
INSERT INTO category (CATEGORY_ID, CATEGORY) values (6, 'eChildrenBooks');
INSERT INTO category (CATEGORY_ID, CATEGORY) values (7, 'eSportsAndLeisure');
INSERT INTO category (CATEGORY_ID, CATEGORY) values (8, 'eMerchandiseForWoman');
INSERT INTO category (CATEGORY_ID, CATEGORY) values (9, 'eWomenClothing');
INSERT INTO category (CATEGORY_ID, CATEGORY) values (10, 'eManFashionAndMerchandise');
INSERT INTO category (CATEGORY_ID, CATEGORY) values (11, 'eGameAndHabit');
INSERT INTO category (CATEGORY_ID, CATEGORY) values (12, 'eBeauty');
INSERT INTO category (CATEGORY_ID, CATEGORY) values (13, 'ePetProducts');
INSERT INTO category (CATEGORY_ID, CATEGORY) values (14, 'eBookTicketAlbum');
INSERT INTO category (CATEGORY_ID, CATEGORY) values (15, 'ePlant');