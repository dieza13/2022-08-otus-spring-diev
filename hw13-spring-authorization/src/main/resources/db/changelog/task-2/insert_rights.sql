insert into user_role(user_id,role_id) values ((select id from usr where username = 'fedor'),(select id from role where name = 'ROLE_USER'));
insert into user_role(user_id,role_id) values ((select id from usr where username = 'fedor'),(select id from role where name ='ROLE_CHILD'));
insert into user_role(user_id,role_id) values ((select id from usr where username = 'seacat'),(select id from role where name ='ROLE_USER'));
insert into user_role(user_id,role_id) values ((select id from usr where username = 'seacat'),(select id from role where name ='ROLE_CHILD'));
insert into user_role(user_id,role_id) values ((select id from usr where username = 'postman'),(select id from role where name ='ROLE_ADULT'));
insert into user_role(user_id,role_id) values ((select id from usr where username = 'postman'),(select id from role where name ='ROLE_MANAGER'));