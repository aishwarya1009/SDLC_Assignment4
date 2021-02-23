
#################  USER TABLE  #############################
CREATE TABLE `user`
(
    `id`  bigint(20) NOT NULL AUTO_INCREMENT,
    `version`          bigint(20)   NOT NULL,
    `email`            varchar(255) NOT NULL,
    `password`         varchar(255) NOT NULL,
    `first_name` varchar(255) not null,
    `last_name` varchar(255) not null,
    `role` varchar(255) not null,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_USER_EMAIL` (`email`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

#####################################
#           SEED DATA               #
#####################################
#################  INSERTING USER  #############################
INSERT INTO `user` (`version`,`email`,`password`, `first_name`,
                    `last_name`, `role`)
   VALUES (0, 'fname.lname@company.com', 'password', 'FName', 'LName', 'User');
