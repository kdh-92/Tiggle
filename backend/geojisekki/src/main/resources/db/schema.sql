DROP TABLE IF EXISTS member;
CREATE TABLE `member` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'test',
    `name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
    `password` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
