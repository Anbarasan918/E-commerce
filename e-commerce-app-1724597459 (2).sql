CREATE TABLE IF NOT EXISTS `1724587800` (

);

CREATE TABLE IF NOT EXISTS `user` (
	`id` int AUTO_INCREMENT NOT NULL UNIQUE,
	`user_id` numeric(10,0) NOT NULL UNIQUE,
	`name` varchar(255) NOT NULL,
	`email_id` varchar(255) NOT NULL UNIQUE,
	`password` varchar(255) NOT NULL,
	`mobile_number` int NOT NULL UNIQUE,
	`gender` varchar(35) NOT NULL,
	`age` int NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `ak_product` (
	`id` int AUTO_INCREMENT NOT NULL UNIQUE,
	`product_name` varchar(255) NOT NULL,
	`brand` varchar(255) NOT NULL,
	`inventory` numeric(10,0) NOT NULL,
	`category` int NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `ak_order` (
	`id` int AUTO_INCREMENT NOT NULL UNIQUE,
	`order_reference_id` numeric(10,0) NOT NULL UNIQUE,
	`user_id` int NOT NULL UNIQUE,
	`ordered_date` datetime NOT NULL,
	`price` numeric(10,0) NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `ak_category` (
	`id` int AUTO_INCREMENT NOT NULL UNIQUE,
	`name` varchar(50) NOT NULL,
	`description` varchar(255) NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `ak_order_details` (
	`id` int AUTO_INCREMENT NOT NULL UNIQUE,
	`product_id` int NOT NULL,
	`model_name` varchar(255) NOT NULL,
	`quantity` numeric(10,0) NOT NULL,
	`order_id` int NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `ak_cart` (
	`id` int AUTO_INCREMENT NOT NULL UNIQUE,
	`user_id` int NOT NULL,
	`cart_date` datetime NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `ak_cart_items` (
	`id` int AUTO_INCREMENT NOT NULL UNIQUE,
	`product_name` varchar(255) NOT NULL,
	`product_id` int NOT NULL,
	`brand` varchar(255) NOT NULL,
	`category` varchar(255) NOT NULL,
	`cart_id` int NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `1724591263` (

);



ALTER TABLE `ak_product` ADD CONSTRAINT `ak_product_fk4` FOREIGN KEY (`category`) REFERENCES `ak_category`(`id`);
ALTER TABLE `ak_order` ADD CONSTRAINT `ak_order_fk2` FOREIGN KEY (`user_id`) REFERENCES `user`(`id`);

ALTER TABLE `ak_order_details` ADD CONSTRAINT `ak_order_details_fk1` FOREIGN KEY (`product_id`) REFERENCES `ak_product`(`id`);

ALTER TABLE `ak_order_details` ADD CONSTRAINT `ak_order_details_fk4` FOREIGN KEY (`order_id`) REFERENCES `ak_order`(`id`);
ALTER TABLE `ak_cart` ADD CONSTRAINT `ak_cart_fk1` FOREIGN KEY (`user_id`) REFERENCES `user`(`id`);
ALTER TABLE `ak_cart_items` ADD CONSTRAINT `ak_cart_items_fk2` FOREIGN KEY (`product_id`) REFERENCES `ak_product`(`id`);

ALTER TABLE `ak_cart_items` ADD CONSTRAINT `ak_cart_items_fk5` FOREIGN KEY (`cart_id`) REFERENCES `ak_cart`(`id`);
