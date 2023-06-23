DROP TABLE IF EXISTS member;
DROP TABLE IF EXISTS geoji;
DROP TABLE IF EXISTS grade;

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
