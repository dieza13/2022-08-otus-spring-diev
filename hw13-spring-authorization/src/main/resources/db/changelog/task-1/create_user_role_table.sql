create table user_role
(
   role_id long not null,
   user_id long not null,
   CONSTRAINT fk_user_role_role_id FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE,
   CONSTRAINT fk_user_role_user_id FOREIGN KEY (user_id) REFERENCES usr(id) ON DELETE CASCADE
);

CREATE INDEX ind_user_role_role_id ON user_role(role_id);
CREATE INDEX ind_user_role_user_id ON user_role(user_id);
