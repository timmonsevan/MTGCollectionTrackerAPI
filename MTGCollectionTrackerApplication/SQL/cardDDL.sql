USE `mtg_collection_tracker_db`;

DROP TABLE IF EXISTS `card`;

CREATE TABLE `card` (
	`id` bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `mana_cost` VARCHAR(255) DEFAULT NULL,
    `multiverse_id` BIGINT DEFAULT NULL,
    `name` VARCHAR(255) DEFAULT NULL,
    `quantity` int DEFAULT NULL,
    `rarity` VARCHAR(255) DEFAULT NULL,
    `set` VARCHAR(255) DEFAULT NULL,
    `set_name` VARCHAR(255) DEFAULT NULL,
    `text` VARCHAR(1000) DEFAULT NULL,
    `type` VARCHAR(255) DEFAULT NULL
)