DROP TABLE IF EXISTS member;
DROP TABLE IF EXISTS members;

CREATE TABLE `members` (
        `id` bigint NOT NULL AUTO_INCREMENT,
        `email` varchar(30) COLLATE utf8mb4_bin NOT NULL,
        `profile_url` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
        `birth` date COLLATE utf8mb4_bin NOT NULL,
        `nickname` varchar(30) COLLATE utf8mb4_bin NOT NULL,
        `provider` VARCHAR(255) COLLATE utf8mb4_bin DEFAULT NULL,
        `provider_id` VARCHAR(255) COLLATE utf8mb4_bin DEFAULT NULL,
        `deleted` TinyInt(1) COLLATE utf8mb4_bin DEFAULT 0,
        `created_at` timestamp COLLATE utf8mb4_bin DEFAULT CURRENT_TIMESTAMP,
        `updated_at` timestamp COLLATE utf8mb4_bin DEFAULT NULL,
        `deleted_at` timestamp COLLATE utf8mb4_bin DEFAULT NULL,
        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
