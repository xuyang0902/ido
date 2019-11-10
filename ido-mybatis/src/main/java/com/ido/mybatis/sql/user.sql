CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `age` int(4) NOT NULL ,
  `create_time` datetime NOT NULL ,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ;