# ALTER TABLE transactions DROP FOREIGN KEY transactions_ibfk_1;
# ALTER TABLE transactions DROP FOREIGN KEY transactions_ibfk_2;
# ALTER TABLE transactions DROP FOREIGN KEY transactions_ibfk_3;
# ALTER TABLE comments DROP FOREIGN KEY comments_ibfk_1;
# ALTER TABLE comments DROP FOREIGN KEY comments_ibfk_2;
# ALTER TABLE comments DROP FOREIGN KEY comments_ibfk_3;
# ALTER TABLE reactions DROP FOREIGN KEY reactions_ibfk_1;
# ALTER TABLE reactions DROP FOREIGN KEY reactions_ibfk_2;
# ALTER TABLE reactions DROP FOREIGN KEY reactions_ibfk_3;

# DROP TABLE if exists comments;
# DROP TABLE if exists grades;
# DROP TABLE if exists members;
# DROP TABLE if exists reactions;
# DROP TABLE if exists tags;
# DROP TABLE if exists transactions;
# DROP TABLE if exists assets;
# DROP TABLE if exists categories;
# DROP TABLE if exists tx_tags;

CREATE TABLE `comments`
(
    `id`  bigint NOT NULL AUTO_INCREMENT,
    `tx_id`       bigint NOT NULL ,
    `parent_id`   bigint NULL ,
    `content`     varchar(255) NOT NULL ,
    `sender_id`   bigint NOT NULL ,
    `receiver_id` bigint NOT NULL ,
    `deleted` TinyInt(1) DEFAULT 0,
    `created_at` timestamp DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp DEFAULT NULL ,
    `deleted_at` timestamp DEFAULT NULL ,

    PRIMARY KEY (`id`)
);

CREATE TABLE `grades`
(
    `id`    bigint NOT NULL AUTO_INCREMENT,
    `name`        varchar(30) NOT NULL ,
    `image_url`       varchar(255) NOT NULL ,
    `description` varchar(100) NOT NULL ,

    PRIMARY KEY (`id`)
);

CREATE TABLE `members` (
   `id` bigint NOT NULL AUTO_INCREMENT,
   `email` varchar(100) NOT NULL,
   `profile_url` varchar(255) DEFAULT NULL,
   `birth` date NULL,
   `nickname` varchar(30) NOT NULL,
   `provider` VARCHAR(255) DEFAULT NULL,
   `provider_id` VARCHAR(255) DEFAULT NULL,
   `deleted` TinyInt(1) DEFAULT 0,
   `created_at` timestamp DEFAULT CURRENT_TIMESTAMP,
   `updated_at` timestamp DEFAULT NULL,
   `deleted_at` timestamp DEFAULT NULL,

   PRIMARY KEY (`id`)
);

CREATE TABLE `reactions`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `tx_id`       bigint NOT NULL ,
    `type`        enum('UP', 'DOWN') NOT NULL ,
    `sender_id`   bigint NOT NULL ,
    `receiver_id` bigint NOT NULL ,
    `created_at`  timestamp DEFAULT CURRENT_TIMESTAMP ,

    PRIMARY KEY (`id`)
);


CREATE TABLE `tags`
(
    `id`      bigint NOT NULL AUTO_INCREMENT,
    `name`    varchar(30) NOT NULL ,
    `defaults` tinyint NOT NULL ,

    PRIMARY KEY (`id`)
);


CREATE TABLE `transactions`
(
    `id`                   bigint NOT NULL AUTO_INCREMENT,
    `member_id`            bigint NOT NULL ,
    `parent_id`            bigint NULL ,
    `asset_id`             bigint NOT NULL ,
    `category_id`          bigint NOT NULL ,
    `type`                 ENUM('INCOME', 'OUTCOME', 'REFUND') NOT NULL ,
    `image_url`            varchar(255) NULL ,
    `amount`               int NOT NULL ,
    `date`                 date NOT NULL ,
    `content`              varchar(255) NOT NULL ,
    `reason`               varchar(255) NOT NULL ,
    `tag_names`            varchar(100) NULL ,
    `deleted`              tinyint DEFAULT 0 ,
    `created_at`           timestamp DEFAULT CURRENT_TIMESTAMP ,
    `updated_at`           timestamp DEFAULT NULL ,
    `deleted_at`           timestamp DEFAULT NULL ,

    PRIMARY KEY (`id`)
);

CREATE TABLE `assets`
(
    `id`       bigint NOT NULL AUTO_INCREMENT,
    `name`     varchar(100) NOT NULL ,
    `defaults`  tinyint DEFAULT 0 ,
    `deleted`              tinyint DEFAULT 0 ,
    `created_at`           timestamp DEFAULT CURRENT_TIMESTAMP ,
    `updated_at`           timestamp DEFAULT NULL ,
    `deleted_at`           timestamp DEFAULT NULL ,

    PRIMARY KEY (`id`)
);

CREATE TABLE `categories`
(
    `id`       bigint NOT NULL AUTO_INCREMENT,
    `name`     varchar(100) NOT NULL ,
    `type`     ENUM('INCOME', 'OUTCOME') NOT NULL ,
    `defaults`  tinyint DEFAULT 0 ,
    `deleted`              tinyint DEFAULT 0 ,
    `created_at`           timestamp DEFAULT CURRENT_TIMESTAMP ,
    `updated_at`           timestamp DEFAULT NULL ,
    `deleted_at`           timestamp DEFAULT NULL ,

    PRIMARY KEY (`id`)
);


CREATE TABLE `tx_tags`
(
    `id`        bigint NOT NULL AUTO_INCREMENT,
    `tx_id`     bigint NOT NULL ,
    `member_id` bigint NOT NULL ,
    `tag_names`  varchar(100) NOT NULL ,
    `deleted`              tinyint DEFAULT 0 ,
    `created_at`           timestamp DEFAULT CURRENT_TIMESTAMP ,
    `updated_at`           timestamp DEFAULT NULL ,
    `deleted_at`           timestamp DEFAULT NULL ,

    PRIMARY KEY (`id`)
);

ALTER TABLE transactions ADD FOREIGN KEY (member_id) REFERENCES members(id);
ALTER TABLE transactions ADD FOREIGN KEY (asset_id) REFERENCES assets(id);
ALTER TABLE transactions ADD FOREIGN KEY (category_id) REFERENCES categories(id);
ALTER TABLE comments ADD FOREIGN KEY (tx_id) REFERENCES transactions(id);
ALTER TABLE comments ADD FOREIGN KEY (sender_id) REFERENCES members(id);
ALTER TABLE comments ADD FOREIGN KEY (receiver_id) REFERENCES members(id);
ALTER TABLE reactions ADD FOREIGN KEY (tx_id) REFERENCES transactions(id);
ALTER TABLE reactions ADD FOREIGN KEY (sender_id) REFERENCES members(id);
ALTER TABLE reactions ADD FOREIGN KEY (receiver_id) REFERENCES members(id);
