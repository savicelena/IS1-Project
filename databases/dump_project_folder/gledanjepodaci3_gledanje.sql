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
-- Table structure for table `gledanje`
--

DROP TABLE IF EXISTS `gledanje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gledanje` (
  `IDGle` int NOT NULL AUTO_INCREMENT,
  `DatumVremePoc` datetime NOT NULL,
  `SekundPoc` int NOT NULL,
  `OdgledanoSek` int NOT NULL,
  `IDKor` int NOT NULL,
  `IDVid` int NOT NULL,
  PRIMARY KEY (`IDGle`),
  KEY `FK_IDKor_Gledanje_idx` (`IDKor`),
  KEY `FK_IDVid_Gledanje_idx` (`IDVid`),
  CONSTRAINT `FK_IDKor_Gledanje` FOREIGN KEY (`IDKor`) REFERENCES `korisnik` (`IDKor`) ON UPDATE CASCADE,
  CONSTRAINT `FK_IDVid_Gledanje` FOREIGN KEY (`IDVid`) REFERENCES `video` (`IDVid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gledanje`
--

LOCK TABLES `gledanje` WRITE;
/*!40000 ALTER TABLE `gledanje` DISABLE KEYS */;
INSERT INTO `gledanje` VALUES (1,'2023-12-01 20:15:00',0,20,1,1),(2,'2024-02-02 15:20:00',15,50,10,2),(3,'2024-02-03 16:00:00',20,90,6,6),(4,'2024-02-07 14:10:19',6,39,7,5);
/*!40000 ALTER TABLE `gledanje` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-02-12 13:43:34
