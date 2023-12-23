INSERT INTO `roles` (`role_name`,`created_at`, `created_by`)
VALUES ('ROLE_ADMIN',CURDATE(),'DBA');

INSERT INTO `roles` (`role_name`,`created_at`, `created_by`)
VALUES ('ROLE_USER',CURDATE(),'DBA');

INSERT INTO `users` (`first_name`, `last_name`,`email`, `username`,`password`,`role_id`,`created_at`, `created_by`)
VALUES ('Michael', 'Chuks', 'admin@email.com','admin','$2y$12$sSNUEziVrdYzo1ZX2H39s.FcBTqnDiNtpSsFqW5nJf07TeZHCi.SG', 1 ,CURDATE(),'DBA');

INSERT INTO `users` (`first_name`, `last_name`,`email`, `username`,`password`,`role_id`,`created_at`, `created_by`)
VALUES ('John', 'Doe', 'johndoe@hotmail.com','johnDoe','$2y$12$sSNUEziVrdYzo1ZX2H39s.FcBTqnDiNtpSsFqW5nJf07TeZHCi.SG', 2 ,CURDATE(),'DBA');

INSERT INTO `users` (`first_name`, `last_name`,`email`, `username`,`password`,`role_id`,`created_at`, `created_by`)
VALUES ('Ella', 'Smith', 'ellasmith@yahoo.co.uk','ella123','$2y$12$sSNUEziVrdYzo1ZX2H39s.FcBTqnDiNtpSsFqW5nJf07TeZHCi.SG', 2 ,CURDATE(),'DBA');

INSERT INTO `users` (`first_name`, `last_name`,`email`, `username`,`password`,`role_id`,`created_at`, `created_by`)
VALUES ('Jermaine', 'Dusty', 'jermaine419@gmail.com','jerm419','$2y$12$sSNUEziVrdYzo1ZX2H39s.FcBTqnDiNtpSsFqW5nJf07TeZHCi.SG', 2 ,CURDATE(),'DBA');

INSERT INTO `tags` (`name`,`created_at`, `created_by`)
VALUES ('Web', CURDATE(),'DBA');

INSERT INTO `tags` (`name`,`created_at`, `created_by`)
VALUES ('Agency', CURDATE(),'DBA');

INSERT INTO `tags` (`name`,`created_at`, `created_by`)
VALUES ('Company', CURDATE(),'DBA');

INSERT INTO `tags` (`name`,`created_at`, `created_by`)
VALUES ('Creative', CURDATE(),'DBA');

INSERT INTO `tags` (`name`,`created_at`, `created_by`)
VALUES ('Html', CURDATE(),'DBA');

INSERT INTO `tags` (`name`,`created_at`, `created_by`)
VALUES ('Marketing', CURDATE(),'DBA');

INSERT INTO `tags` (`name`,`created_at`, `created_by`)
VALUES ('Social Media', CURDATE(),'DBA');

INSERT INTO `tags` (`name`,`created_at`, `created_by`)
VALUES ('Branding', CURDATE(),'DBA');

INSERT INTO `categories` (`name`,`created_at`, `created_by`)
VALUES ('Web', CURDATE(),'DBA');

INSERT INTO `categories` (`name`,`created_at`, `created_by`)
VALUES ('Agency', CURDATE(),'DBA');

INSERT INTO `categories` (`name`,`created_at`, `created_by`)
VALUES ('Company', CURDATE(),'DBA');

INSERT INTO `categories` (`name`,`created_at`, `created_by`)
VALUES ('Creative', CURDATE(),'DBA');

INSERT INTO `categories` (`name`,`created_at`, `created_by`)
VALUES ('Html', CURDATE(),'DBA');

INSERT INTO `categories` (`name`,`created_at`, `created_by`)
VALUES ('Marketing', CURDATE(),'DBA');

INSERT INTO `categories` (`name`,`created_at`, `created_by`)
VALUES ('Social Media', CURDATE(),'DBA');

INSERT INTO `categories` (`name`,`created_at`, `created_by`)
VALUES ('Branding', CURDATE(),'DBA');