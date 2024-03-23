CREATE DATABASE  IF NOT EXISTS `gledanjepodaci3` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `gledanjepodaci3`;
-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: gledanjepodaci3
-- ------------------------------------------------------
-- Server version	8.2.0

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
-- Table structure for table `pretplata`
--

DROP TABLE IF EXISTS `pretplata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pretplata` (
  `IDPre` int NOT NULL AUTO_INCREMENT,
  `DatumVremePoc` datetime NOT NULL,
  `Cena` int DEFAULT NULL,
  `IDKor` int NOT NULL,
  `IDPak` int NOT NULL,
  PRIMARY KEY (`IDPre`),
  KEY `FK_IDKor_Pretplata_idx` (`IDKor`),
  KEY `FK_IDPak_Pretplata_idx` (`IDPak`),
  CONSTRAINT `FK_IDKor_Pretplata` FOREIGN KEY (`IDKor`) REFERENCES `korisnik` (`IDKor`) ON UPDATE CASCADE,
  CONSTRAINT `FK_IDPak_Pretplata` FOREIGN KEY (`IDPak`) REFERENCES `paket` (`IDPak`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pretplata`
--

LOCK TABLES `pretplata` WRITE;
/*!40000 ALTER TABLE `pretplata` DISABLE KEYS */;
INSERT INTO `pretplata` VALUES (1,'2023-11-07 13:13:00',1500,1,2),(2,'2023-12-24 15:13:00',2000,3,3),(3,'2024-01-15 03:05:00',1500,10,2),(4,'2024-01-25 12:15:00',1000,7,1),(5,'2024-02-01 00:00:00',2000,6,3),(6,'2024-02-07 02:24:00',1500,1,2),(9,'2023-02-16 14:15:00',1500,5,2),(10,'2023-02-16 00:05:00',1500,2,2),(11,'2024-01-19 17:28:00',1000,4,1),(12,'2024-02-07 13:08:40',1000,9,1),(13,'2024-02-11 14:28:26',1500,3,2),(14,'2024-02-11 15:46:36',2150,15,5),(15,'2024-02-12 13:20:36',2150,2,5),(16,'2024-01-12 09:09:09',2150,2,5);
/*!40000 ALTER TABLE `pretplata` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-02-12 13:43:35