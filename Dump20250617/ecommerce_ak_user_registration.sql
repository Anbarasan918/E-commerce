-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: localhost    Database: ecommerce
-- ------------------------------------------------------
-- Server version	8.0.23

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ak_user_registration`
--

DROP TABLE IF EXISTS `ak_user_registration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ak_user_registration` (
  `id` bigint NOT NULL,
  `user_id` varbinary(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `mobile_number` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `age` varbinary(255) DEFAULT NULL,
  `confirm_password` varchar(255) DEFAULT NULL,
  `new_password` varchar(255) DEFAULT NULL,
  `emailId` varchar(255) DEFAULT NULL,
  `confirmPassword` varchar(255) DEFAULT NULL,
  `newPassword` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `user_id` (`user_id`),
  UNIQUE KEY `mobile_number` (`mobile_number`),
  UNIQUE KEY `phone_number` (`mobile_number`),
  UNIQUE KEY `emailId` (`emailId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ak_user_registration`
--

LOCK TABLES `ak_user_registration` WRITE;
/*!40000 ALTER TABLE `ak_user_registration` DISABLE KEYS */;
INSERT INTO `ak_user_registration` VALUES (2,_binary '¨\Ì\0sr\0java.lang.Integer‚†§˜Åá8\0I\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp\0\0\0\ﬁ','anbarasan','$2a$10$7t86Nscl7HKYuy/VgRW1EuLUf/vKB/goRE0oi8Kux1q1SkMuccKqy','9751958489','Male',_binary '¨\Ì\0sr\0java.lang.Integer‚†§˜Åá8\0I\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp\0\0\0','anbutamil','anbutamil','anbu@gmail.com',NULL,NULL),(652,_binary '¨\Ì\0sr\0java.lang.Integer‚†§˜Åá8\0I\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp\0\0*','santhip','$2a$10$6je8ThWBrcTeJcCN8a4fnOJZh5VL9OPF0w.ek.DJ1W4GKwZM/DDa.','9222222223','male',_binary '¨\Ì\0sr\0java.lang.Integer‚†§˜Åá8\0I\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp\0\0\0 ',NULL,NULL,'santhip@gmail.com','anbusanthip','anbusanthip');
/*!40000 ALTER TABLE `ak_user_registration` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-17  0:55:31
