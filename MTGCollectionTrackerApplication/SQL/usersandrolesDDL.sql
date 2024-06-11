USE `mtg_collection_tracker_db`;

DROP TABLE IF EXISTS `roles`;
DROP TABLE IF EXISTS `users`;


CREATE TABLE `users` (
  `user_id` varchar(50) NOT NULL,
  `pw` char(68) NOT NULL,
  `active` tinyint NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `users`
VALUES
('root','{bcrypt}$2a$10$PiL9mt05Dqk3VIOJzpNv8eUqcJs5zH4JZ/5OgBze3hN8TNHODq1P.',1),
('jeff','{bcrypt}$2a$10$ZRMXg4RuVad7OPfzVLbC3egy17M/9X6N9Xe41Wx1n7sRhHdK72qxS',1),
('ben','{bcrypt}$2a$10$kLAwG9DhdvuLmAJuFZZpBOxuTeIBJO/z34f3MmjewUIqgAm.tJSgK',1),
('taylor','{bcrypt}$2a$10$Y2i5Br6SMm50zbk4zPLs1O4.eJPsLpfIlDTTll8tqk7nAZoRs4W.6',1),
('user','{bcrypt}$2a$10$F1byqv/op1isUBqe4ogxAuLWZL8ohBI31uMtpYdphlZLGAsupXbL6', 1);

CREATE TABLE `roles` (
  `user_id` varchar(50) NOT NULL,
  `role` varchar(50) NOT NULL,
  UNIQUE KEY `authorities5_idx_1` (`user_id`,`role`),
  CONSTRAINT `authorities5_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `roles`
VALUES
('ben','ROLE_USER'),
('jeff','ROLE_USER'),
('jeff','ROLE_TESTER'),
('root','ROLE_USER'),
('root','ROLE_TESTER'),
('root','ROLE_ADMIN'),
('taylor','ROLE_USER'),
('taylor','ROLE_TESTER'),
('taylor','ROLE_ADMIN'),
('user', 'ROLE_ADMIN'),
('user', 'ROLE_TESTER'),
('user', 'ROLE_USER');
