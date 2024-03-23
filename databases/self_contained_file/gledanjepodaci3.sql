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

--
-- Table structure for table `korisnik`
--

DROP TABLE IF EXISTS `korisnik`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `korisnik` (
  `IDKor` int NOT NULL AUTO_INCREMENT,
  `Ime` varchar(45) NOT NULL,
  `Email` varchar(45) DEFAULT NULL,
  `Godiste` varchar(45) DEFAULT NULL,
  `Pol` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`IDKor`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `korisnik`
--

LOCK TABLES `korisnik` WRITE;
/*!40000 ALTER TABLE `korisnik` DISABLE KEYS */;
INSERT INTO `korisnik` VALUES (1,'Joakim','joakim@gmail.com','2002','M'),(2,'Charity','charity@gmail.com','1970','Z'),(3,'Isidora','isidora@gmail.com','1996','Z'),(4,'Svea','svea@gmail.com','1980','Z'),(5,'Morten','morten@gmail.com','1966','M'),(6,'Vojin','vojinetf@gmail.com','2002','M'),(7,'Dorotea','dorotea@gmail.com','2005','Z'),(8,'Andreas','andreas@gmail.com','1991','M'),(9,'Ilija','ilija@gmail.com','1981','Z'),(10,'Elias','elias@gmail.com','1999','M'),(11,'Petar','petar@gmail.com','2002','M'),(12,'Elena','elena5@gmail.com','2002','Z'),(13,'Alan Al','alan55@gmail.com','1995','M'),(14,'Alaz','alaz@yahoo.com','1988','M'),(15,'Jan','jan@gmail.com','1999','M'),(16,'Vaggelis','vaggelis@gmail.com','2004','M');
/*!40000 ALTER TABLE `korisnik` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ocena`
--

DROP TABLE IF EXISTS `ocena`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ocena` (
  `IDKor` int NOT NULL,
  `IDVid` int NOT NULL,
  `Ocena` int NOT NULL,
  `DatumVreme` datetime NOT NULL,
  PRIMARY KEY (`IDKor`,`IDVid`),
  KEY `FK_IDVid_Ocena_idx` (`IDVid`),
  CONSTRAINT `FK_IDKor_Ocena` FOREIGN KEY (`IDKor`) REFERENCES `korisnik` (`IDKor`) ON UPDATE CASCADE,
  CONSTRAINT `FK_IDVid_Ocena` FOREIGN KEY (`IDVid`) REFERENCES `video` (`IDVid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ocena`
--

LOCK TABLES `ocena` WRITE;
/*!40000 ALTER TABLE `ocena` DISABLE KEYS */;
INSERT INTO `ocena` VALUES (1,1,5,'2023-12-01 23:23:00'),(6,6,4,'2024-02-03 19:50:00'),(10,2,4,'2024-02-03 12:30:00');
/*!40000 ALTER TABLE `ocena` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paket`
--

DROP TABLE IF EXISTS `paket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `paket` (
  `IDPak` int NOT NULL AUTO_INCREMENT,
  `CenaMesec` int NOT NULL,
  PRIMARY KEY (`IDPak`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paket`
--

LOCK TABLES `paket` WRITE;
/*!40000 ALTER TABLE `paket` DISABLE KEYS */;
INSERT INTO `paket` VALUES (1,1000),(2,1500),(3,2000),(4,1150),(5,2150);
/*!40000 ALTER TABLE `paket` ENABLE KEYS */;
UNLOCK TABLES;

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

--
-- Table structure for table `video`
--

DROP TABLE IF EXISTS `video`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `video` (
  `IDVid` int NOT NULL AUTO_INCREMENT,
  `Naziv` varchar(45) NOT NULL,
  `Trajanje` int NOT NULL,
  `DatumVremePost` datetime NOT NULL,
  `IDKor` int NOT NULL,
  PRIMARY KEY (`IDVid`),
  KEY `FK_IDKor_Video_idx` (`IDKor`),
  CONSTRAINT `FK_IDKor_Video` FOREIGN KEY (`IDKor`) REFERENCES `korisnik` (`IDKor`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `video`
--

LOCK TABLES `video` WRITE;
/*!40000 ALTER TABLE `video` DISABLE KEYS */;
INSERT INTO `video` VALUES (1,'Gospodar Prstenova Povratak Kralja',183,'2022-10-25 10:34:15',1),(2,'Avatar',175,'2023-12-27 19:15:00',10),(3,'Parazit',155,'2023-05-12 20:00:00',4),(4,'Prozor u dvoriste',95,'1995-10-22 15:44:19',2),(5,'Kum',191,'1999-03-12 13:15:00',8),(6,'Matriks',115,'2015-01-15 03:03:03',6),(23,'Psiho',93,'2024-02-12 13:40:25',1);
/*!40000 ALTER TABLE `video` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-02-12 13:46:10
