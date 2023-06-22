DROP TABLE IF EXISTS member;
DROP TABLE IF EXISTS geoji;
CREATE TABLE `geoji` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `username` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
    `email` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL,
    `birth` date COLLATE utf8mb4_bin DEFAULT NULL,
    `password` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
    `oauth` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL, -- need check
    `nickname` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL,
    `thumbnail` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
--     `grade_id` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
    `created_at` timestamp COLLATE utf8mb4_bin DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp COLLATE utf8mb4_bin DEFAULT NULL,
    `deleted` TinyInt(1) COLLATE utf8mb4_bin DEFAULT 0,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
