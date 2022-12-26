create table role
(
   id long auto_increment primary key,
   name varchar(255)
);
CREATE INDEX ind_role_role ON role(name);
