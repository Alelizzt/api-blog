INSERT INTO blog_system.roles (id, name) VALUES
	 (1,'ROLE_USER'),
	 (2,'ROLE_ADMIN');
INSERT INTO blog_system.users (email,name,password,username) VALUES
	 ('a2@gmail.com','Administrator','$2a$10$9R1gSdfPu9IuFwSYHdLF5.ZTMxlg/HcIprAPtLGI4LJ87GI89QIru','admin');
INSERT INTO blog_system.users_roles (user_id,rol_id) VALUES
	 (1,2);
