DROP TABLE IF EXISTS member;
DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS trans_hashtag;
DROP TABLE IF EXISTS transaction;
DROP TABLE IF EXISTS geoji_category;
DROP TABLE IF EXISTS geoji;
DROP TABLE IF EXISTS grade;
DROP TABLE IF EXISTS transaction_type;
DROP TABLE IF EXISTS hashtag;
DROP TABLE IF EXISTS category;

CREATE TABLE `grade` (
        `id` bigint NOT NULL AUTO_INCREMENT,
        `grade_name` VARCHAR(255) NOT NULL,
        `grade_description` VARCHAR(255),
        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

CREATE TABLE `geoji` (
        `id` bigint NOT NULL AUTO_INCREMENT,
        `username` varchar(20) COLLATE utf8mb4_bin NOT NULL,
        `email` varchar(30) COLLATE utf8mb4_bin NOT NULL,
        `birth` date COLLATE utf8mb4_bin NOT NULL,
        `password` varchar(255) COLLATE utf8mb4_bin NOT NULL,
        `oauth_provider` VARCHAR(255) COLLATE utf8mb4_bin DEFAULT NULL,
        `oauth_id` VARCHAR(255) COLLATE utf8mb4_bin DEFAULT NULL,
        `oauth_token` VARCHAR(255) COLLATE utf8mb4_bin DEFAULT NULL,
        `nickname` varchar(30) COLLATE utf8mb4_bin NOT NULL,
        `thumbnail` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
        `created_at` timestamp COLLATE utf8mb4_bin DEFAULT CURRENT_TIMESTAMP,
        `updated_at` timestamp COLLATE utf8mb4_bin DEFAULT NULL,
        `deleted` TinyInt(1) COLLATE utf8mb4_bin DEFAULT 0,
        `grade_id` bigint DEFAULT 1,
        PRIMARY KEY (`id`),
        CONSTRAINT `fk_grade_id` FOREIGN KEY (`grade_id`) REFERENCES `grade` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

CREATE TABLE `transaction_type` (
        `id` bigint NOT NULL AUTO_INCREMENT,
        `type_name` VARCHAR(255) NOT NULL,
        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

CREATE TABLE `transaction` (
        `id` bigint NOT NULL AUTO_INCREMENT,
        `geoji_id` bigint NOT NULL,
        `type_id` bigint NOT NULL,
        `accounts` bigint DEFAULT 0,
        `money` bigint NOT NULL,
        `transaction_description` VARCHAR(255),
        `image` VARCHAR(255),
        `transaction_id` bigint DEFAULT NULL,
        `created_at` timestamp COLLATE utf8mb4_bin DEFAULT CURRENT_TIMESTAMP,
        `updated_at` timestamp COLLATE utf8mb4_bin DEFAULT NULL,
        PRIMARY KEY (`id`),
        CONSTRAINT `fk_transanction_id` FOREIGN KEY (`transaction_id`) REFERENCES `transaction` (`id`),
        CONSTRAINT `fk_transanction_type_id` FOREIGN KEY (`type_id`) REFERENCES `transaction_type` (`id`),
        CONSTRAINT `fk_goeji_id` FOREIGN KEY (`geoji_id`) REFERENCES `geoji` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

CREATE TABLE `hashtag` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `hashtag_name` VARCHAR(255),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

CREATE TABLE `trans_hashtag` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `hashtag_id` bigint NOT NULL,
  `transaction_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_hashtag_id` FOREIGN KEY (`hashtag_id`) REFERENCES `hashtag` (`id`),
  CONSTRAINT `fk_transaction_id` FOREIGN KEY (`transaction_id`) REFERENCES `transaction` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

CREATE TABLE `category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `category_name` VARCHAR(255),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

CREATE TABLE `geoji_category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `geoji_id` bigint NOT NULL,
  `categories` VARCHAR(255), -- 배열을 json으로 넣을거라 생각해 문자열로 설정했습니다.. id배열을 넣으려니 참조를 코드로 어떻게 나타내야될지 애매해요.
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_goeji_id_category` FOREIGN KEY (`geoji_id`) REFERENCES `geoji` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

CREATE TABLE `comment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `transaction_id` bigint NOT NULL,
  `body` VARCHAR(255),
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_transaction_id_comment` FOREIGN KEY (`transaction_id`) REFERENCES `transaction` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;